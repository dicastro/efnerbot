package es.qopuir.efnerbot;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.bots.commands.ICommandRegistry;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

public class HelpCommand extends BotCommand {

	private static final String LOGTAG = "HELPCOMMAND";

	private final ICommandRegistry commandRegistry;

	public HelpCommand(ICommandRegistry commandRegistry) {
		super("ayuda", "Obtén una ayuda de todos los comandos disponibles");
		this.commandRegistry = commandRegistry;
	}

	@Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        StringBuilder helpMessageBuilder = new StringBuilder("<b>Ayuda</b>\n");
        helpMessageBuilder.append("Estos son los comandos registrados para este Bot:\n\n");

        for (BotCommand botCommand : commandRegistry.getRegisteredCommands()) {
            helpMessageBuilder.append(botCommand.toString()).append("\n\n");
        }

        SendMessage helpMessage = new SendMessage();
        helpMessage.setChatId(chat.getId().toString());
        helpMessage.enableHtml(true);
        helpMessage.setText(helpMessageBuilder.toString());

        try {
            absSender.sendMessage(helpMessage);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
	}
}