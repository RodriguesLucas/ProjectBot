/*
 * Classe de testes pela aplicação Spring Boot
 * Autores @nicolasfischer @brunobolzan @lucasrodrigues
 */

package br.com.unisc.project.controller;

// importação das bibliotecas necessárias para inicializar o bot pelo Telegram
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import br.com.unisc.project.service.ProductQueryBotService;


@Component
public class BotStarterController implements CommandLineRunner {

	private final ProductQueryBotService botService;
	
	/*
	 * BotStarterController
	 * Objetivo: Construtor da classe que modifica o Service do bot.
	 * Retorno: void
	 */
	public BotStarterController(ProductQueryBotService botService) {
		this.botService = botService; 		// modificando atributo
	}

	/*
	 * run
	 * Objetivo: Realiza a chamada recebendo args (String) com tratamento das exceções.
	 * Retorno: void
	 */
	@Override
	public void run(String... args) throws Exception {
		try { // caso não funcione o try, executará o cath
			TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class); // instanciando objeto do tipo recebido da biblioteca da API
			telegramBotsApi.registerBot(this.botService);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}