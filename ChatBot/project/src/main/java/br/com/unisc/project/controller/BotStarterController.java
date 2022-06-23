package br.com.unisc.project.controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import br.com.unisc.project.service.ProductQueryBotService;


@Component
public class BotStarterController implements CommandLineRunner {

	private final ProductQueryBotService botService;

	public BotStarterController(ProductQueryBotService botService) {
		this.botService = botService;
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
			telegramBotsApi.registerBot(this.botService);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}