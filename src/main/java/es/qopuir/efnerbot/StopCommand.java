package es.qopuir.efnerbot;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

public class StopCommand extends BotCommand {

	public static final String LOGTAG = "STOPCOMMAND";

	/**
	 * Construct
	 */
	public StopCommand() {
		super("stop", "Con este comando se para el Bot");
	}

	@Override
	public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
		String userName = user.getFirstName() + " " + user.getLastName();

		SendMessage answer = new SendMessage();
		answer.setChatId(chat.getId().toString());
		answer.setText("Adios " + userName + "!\n" + "Espero verte de nuevo pronto!");

		try {
			absSender.sendMessage(answer);
		} catch (TelegramApiException e) {
			BotLogger.error(LOGTAG, e);
		}
	}
}