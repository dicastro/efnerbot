package es.qopuir.efnerbot;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

public class EfnerHandler extends TelegramLongPollingCommandBot {
	private static final String LOGTAG = "DIRECTIONSHANDLERS";

	public EfnerHandler() {
		register(new HelloCommand());
		register(new StartCommand());
		register(new StopCommand());
		
		HelpCommand helpCommand = new HelpCommand(this);
		register(helpCommand);

		registerDefaultAction((absSender, message) -> {
			SendMessage commandUnknownMessage = new SendMessage();
			commandUnknownMessage.setChatId(message.getChatId());
			commandUnknownMessage.setText("El comando '" + message.getText() + "' es desconocido para mi. Una ayuda " + Emoji.AMBULANCE);
			
			try {
				absSender.sendMessage(commandUnknownMessage);
			} catch (TelegramApiException e) {
				BotLogger.error(LOGTAG, e);
			}
			
			helpCommand.execute(absSender, message.getFrom(), message.getChat(), new String[] {});
		});
	}

	@Override
	public String getBotToken() {
		return System.getenv(BotConfig.EFNER_TOKEN);
	}

	@Override
	public String getBotUsername() {
		return BotConfig.EFNER_USER;
	}

	@Override
	public void processNonCommandUpdate(Update update) {
		if (update.hasMessage()) {
			Message message = update.getMessage();

			if (message.hasText()) {
				SendMessage echoMessage = new SendMessage();
				echoMessage.setChatId(message.getChatId());
				echoMessage.setText("No has introducido ningún comando, por favor introduce uno de los comandos permitidos");

				try {
					sendMessage(echoMessage);
				} catch (TelegramApiException e) {
					BotLogger.error(LOGTAG, e);
				}
			}
		}
	}
}
