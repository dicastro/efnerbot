package es.qopuir.efnerbot;

import java.util.Arrays;
import java.util.List;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

public class HelloCommand extends BotCommand {
	private static final String LOGTAG = "HELLOCOMMAND";

	private static final List<String> phrases = Arrays.asList(new String[]{ "Nos vamos a forrar {name}", "Este bot lo he hecho en 30 mins {name}", "Tenemos que currarnos uno {name}", "{name} te mola?", "{name}, pues no ha sido tan difícil hacerlo", "{name}, si no te has dado cuenta, estos mensajes se eligen aleatoriamente de una lista" });
	
	public HelloCommand() {
		super("hola", "Saluda al bot");
	}

	@Override
	public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
		String userName = chat.getUserName();
		
		if (userName == null || userName.isEmpty()) {
			userName = user.getFirstName() + " " + user.getLastName();
		}
		
		int selectedPhrase = EfnerBot.RANDOM.nextInt(phrases.size());
		
		SendMessage answer = new SendMessage();
		answer.setChatId(chat.getId().toString());
		answer.setText(phrases.get(selectedPhrase).replaceAll("\\{name\\}", userName));

		try {
			absSender.sendMessage(answer);
		} catch (TelegramApiException e) {
			BotLogger.error(LOGTAG, e);
		}
	}
}