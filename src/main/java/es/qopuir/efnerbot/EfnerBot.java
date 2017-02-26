package es.qopuir.efnerbot;

import java.io.IOException;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;
import org.telegram.telegrambots.logging.BotsFileHandler;

@SpringBootApplication
public class EfnerBot {
	private static final String LOGTAG = "MAIN";

	public static final Random RANDOM = new Random(123456789L);

	public static void main(String[] args) {
		SpringApplication.run(EfnerBot.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			BotLogger.setLevel(Level.ALL);
			BotLogger.registerLogger(new ConsoleHandler());

			try {
				BotLogger.registerLogger(new BotsFileHandler());
			} catch (IOException e) {
				BotLogger.severe(LOGTAG, e);
			}

			try {
				ApiContextInitializer.init();
				TelegramBotsApi telegramBotsApi = createLongPollingTelegramBotsApi();

				try {
					telegramBotsApi.registerBot(new EfnerHandler());
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				BotLogger.error(LOGTAG, e);
			}
		};
	}

	private static TelegramBotsApi createLongPollingTelegramBotsApi() {
		return new TelegramBotsApi();
	}
}