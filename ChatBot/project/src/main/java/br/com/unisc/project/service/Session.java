/* 
 *  Classe que trata a sessão instanciada pelo usuário ao começar o processo de conversação
 * Autores @nicolasfischer @brunobolzan @lucasrodrigues 
 */
package br.com.unisc.project.service;

import java.io.ByteArrayInputStream;
import java.time.Instant;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import br.com.unisc.project.dtos.CategoryDto;
import br.com.unisc.project.dtos.ClientDto;
import br.com.unisc.project.dtos.HistoryDto;
import br.com.unisc.project.dtos.ProductDto;

// classe que implementa interface e reescreve étodos dela
public class Session implements Runnable {
	// Atributos
	private long chatId;
	private Thread thread;
	private Callback cb;
	private ProductQueryBotService bot;
	private final int maxSeconds = 180;
	private final String greeting = "Olá";
	private int interactionStage;
	private static final String URlBase = "http://localhost:8080/";	// definindo url base para conexão
	// inicializando atributos base como nulos
	private CategoryDto[] categories = null;
	private CategoryDto[] children = null;
	private ProductDto[] products = null;
	private ProductDto chosenProduct = null;
	private CategoryDto chosenCategory = null;
	private final RestTemplate restTemplate = new RestTemplate();
	private Message m;
	private ClientDto client = new ClientDto();
	private long choice;
	private boolean exit;

	/*
	 * construtor - Session
	 * Objetivo: Método que instaciará um objeto oriundo da classe a partir do recebimento de argumentos padronizados
	 * Retorno: void
	 * Parâmetros: long chatId, Callback cb, ProductQueryBotService bot
	 */
	public Session(long chatId, Callback cb, ProductQueryBotService bot) {
		this.chatId = chatId;
		this.cb = cb;
		this.bot = bot;
		this.interactionStage = 1;
	}

	/*
	 * createThread
	 * Objetivo: criar thread
	 * Retorno: void
	 * Parâmetros: nenhum
	 */
	public void createThread() {
		thread = new Thread(this);
		thread.start();
	}

	/*
	 * reescrita do método run
	 * Objetivo: criar thread
	 * Retorno: void
	 * Parâmetros: nenhum
	 */
	@Override
	public void run() {
		Update update = bot.checkUpdates(chatId);

		Instant lastUpdate = null;

		exit = false;	// controlador

		// inicio do ciclo de rotina - do ... while - passa ao menos uma vez no bloco
		do {
			if (update == null) {	// caso atualização nula ocorrer - ou seja, não tiver atualização para aquele ID
				update = bot.checkUpdates(chatId);
				if (update == null && lastUpdate == null)
					lastUpdate = Instant.now();
				continue;
			}
			lastUpdate = Instant.ofEpochSecond(update.getMessage().getDate());
			m = update.getMessage(); // armazenando mensagem do usuário
			// tratamento de exções a partir da chamada de estágios
			try {
				switch (interactionStage) {
				case 1:
					stage1();
					break;
				case 2:
					stage2();
					break;
				case 3:
					stage3();
					break;
				case 4:
					stage4();
					break;
				case 5:
					stage5();
					break;
				case 6:
					stage6();
					break;
				case 7:
					stage7();
					break;
				case -1:
					exit = true;
					break;
				default:
					break;
				}
			} catch (TelegramApiException e) {
			}
			if (!exit)
				update = bot.checkUpdates(chatId);

		} while (Instant.now().minusSeconds(lastUpdate.getEpochSecond()).getEpochSecond() < maxSeconds && !exit);
		if (!exit) {
			// tratando exção do tempo limite para ciclo
			try {
				bot.sendMessage(String.valueOf(chatId),
						"Tempo de resposta esgotado. Recomece o diálogo do início se ainda deseja buscar produtos.");
			} catch (TelegramApiException e) {

			}
		}
		cb.callback(this);
	}

	/*
	 * getChosenProduct
	 * Objetivo: verificar produtos
	 * Retorno: long
	 * Parâmetros: String text, ProductDto[] products
	 */
	private long getChosenProduct(String text, ProductDto[] products) {
		// tratando exceção
		try {
			return Long.valueOf(text);
		} catch (NumberFormatException e) {
			for (ProductDto p : products) { // foreach para verificar produto
				if (p.getDescription() != null && p.getDescription().equalsIgnoreCase(text)) // caso seja diferente de null e produto encontrado
					return p.getId();	// retorna seu id
			}
		}
		return -1; //controlador de retorno negativo para caso não seja encontrada
	}

	/*
	 * getChosenProduct
	 * Objetivo: verificar categoria de produtos
	 * Retorno: long
	 * Parâmetros: String text, CategoryDto[] categories
	 */
	private long getChosenCategory(String text, CategoryDto[] categories) {
		// tratando exceção
		try {
			return Long.valueOf(text);
		} catch (NumberFormatException e) {
			for (CategoryDto c : categories) { // foreach para verificar categoria
				if (c.getDescription() != null && c.getDescription().equalsIgnoreCase(text)) // caso seja diferente de null e categoria encontrada
					return c.getId(); // retorna id
			}
		}
		return -1; //controlador de retorno negativo para caso não seja encontrada
	}

	/*
	 * isPhoneNumberValid
	 * Objetivo: verificar telefone
	 * Retorno: boolean
	 * Parâmetros: String text
	 */
	private boolean isPhoneNumberValid(String text) {
		if (!text.isBlank()) // caso não esteja em branco
			return true; // retorno positivo dizendo que está tudo ok
		return false; // caso não seja digitado nada
	}

	/*
	 * isCpfCnpjValid
	 * Objetivo: verificar telefone
	 * Retorno: boolean
	 * Parâmetros: String text
	 */
	private boolean isCpfCnpjValid(String text) {
		if (!text.isBlank() && text.length() >= 11 && text.length() <= 14) // caso não esteja em brancon e a contagem de caracteres esteja ok
			return true; // retorno positivo dizendo que está tudo ok
		return false; // caso não seja digitado nada
	}

	
	/*
	 * reescrita do método equals
	 * Objetivo: Comparar uma sessão com a sessão atual
	 * Retorno: boolean
	 * Parâmetros: Object o
	 */
	@Override
	public boolean equals(Object o) {
		Session s = (Session) o;
		if (s.getChatId() == chatId) // caso ids de chat iguais
			return true; // retorno positivo falando que são iguais
		return false; // retorno negativo falando que são diferentes
	}

	
	
	
	
	
	
	
	/*
	 * stage1
	 * Objetivo: realizar tratamento do cliente na sessão
	 * Retorno: nenhum
	 * Parâmetros: nenhum
	 */
	private void stage1() throws TelegramApiException {
		String id = String.valueOf(chatId);
		ClientDto currentClient = restTemplate.getForObject(URlBase.concat("/client/id/").concat(id), ClientDto.class); // tratando id da sessão a partirn da url 
		if (currentClient != null) { // caso diferente de null
			bot.sendMessage(id, greeting + ", " + currentClient.getName() + "!"); // mostrando para usuário que já está reconhecido
			interactionStage = 3;
			stage3(); // pulando para próxima etapa
		} else {
			bot.sendMessage(id, greeting + ", cliente!");
			bot.sendMessage(id, "Por favor, informe seu nome: ");
			client.setChatId(chatId);
			interactionStage = 2;
		}
	}
	
	/*
	 * stage2
	 * Objetivo: realizar cadastro de usuário
	 * Retorno: nenhum
	 * Parâmetros: nenhum
	 */
	private void stage2() throws TelegramApiException {
		String id = String.valueOf(chatId);
		if (client.getName() == null) {	// se nome retornado do objeto é nulo (não existe registrado)
			if (!m.getText().isBlank()) { // se não estiver em branco
				client.setName(m.getText());
				bot.sendMessage(id, "Entendi. Seu nome é " + client.getName());	// informando que já conhece usuário
				bot.sendMessage(id, "Por favor, informe seu cpf/cnpj:"); // pedindo cpf ou cnpj
			} else
				bot.sendMessage(id, "Seu nome não pode estar em branco. Tente novamente."); // caso nome esteja em branco
			return;
		} else if (client.getCpfCnpj() == null) { // tratamento cnpj/cpf caso não esteja registrado
			if (isCpfCnpjValid(m.getText())) { // validando se está digitado conforme regras do negócio e se sim, segue no bloco
				client.setCpfCnpj(m.getText());  // registrando cpf, cnpj no objeto
				bot.sendMessage(id, "Entendi. Seu cpf/cnpj é " + client.getCpfCnpj());
				bot.sendMessage(id, "Por favor, informe seu número de telefone:"); // coletando telefone
			} else // caso cpf/cnpj não passe na verificação
				bot.sendMessage(id, "O cpf/cnpj informado não é válido. Tente novamente.");
			return;
		} else { // caso o nome e o cpj esteja registrado
			if (isPhoneNumberValid(m.getText())) { // validando telefone
				client.setPhoneNumber(m.getText());
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<ClientDto> requestEntity = new HttpEntity<ClientDto>(client, headers);
				restTemplate.postForObject(URlBase.concat("client/").concat(id), requestEntity, ClientDto.class);
				bot.sendMessage(id, "Entendi. Seu número de telefone é " + client.getPhoneNumber()); // dando ok que com o recebimento de um telefone válido 
				bot.sendMessage(id, "Parabéns. Você foi cadastrado no sistema.");
				interactionStage = 3;
				stage3(); // pula para etapa 3
			} else // telefone informado não é válido
				bot.sendMessage(id, "O número de telefone informado não é válido. Tente novamente.");
			return;
		}
	}

	/*
	 * stage3
	 * Objetivo: tratar categorias
	 * Retorno: nenhum
	 * Parâmetros: nenhum
	 */
	private void stage3() throws TelegramApiException {
		String id = String.valueOf(chatId); // obtendo id
		categories = restTemplate.getForObject(URlBase.concat("category"), CategoryDto[].class);
		if (categories == null || (categories != null && categories.length == 0)) { // quando não há categoria registrada
			bot.sendMessage(id, "Não há categorias no banco. Volte mais tarde.");
			interactionStage = -1;
			exit = true;
			return; // sai do método
		}
		// quando há categorias registradas
		bot.sendMessage(id, "Escolha um código de categoria:");
		String categoryList = new String();
		for (int i = 0; i < categories.length; i++) { // percorre cetegorias e armazena em vetor
			CategoryDto c = categories[i];
			categoryList += (c.getId()) + ": " + c.getDescription() + "\n";
		}
		bot.sendMessage(id, categoryList); // mostrando categorias para usuario
		interactionStage = 4;
	}

	
	
	
	
	/*
	 * stage3
	 * Objetivo: tratar categorias
	 * Retorno: nenhum
	 * Parâmetros: nenhum
	 */
	private void stage4() throws TelegramApiException {
		categories = restTemplate.getForObject(URlBase.concat("category"), CategoryDto[].class);

		String id = String.valueOf(chatId);
		choice = getChosenCategory(m.getText(), categories);
		if (choice == -1) {
			bot.sendMessage(id, "Não entendi. Por favor, responda novamente.");
			return;
		}
		chosenCategory = restTemplate.getForObject(URlBase.concat("/category/id/").concat(String.valueOf(choice)),
				CategoryDto.class);
		products = restTemplate.getForObject(URlBase.concat("/product/category/".concat(String.valueOf(choice))),
				ProductDto[].class);
		if (products.length == 0) {

			children = restTemplate.getForObject(URlBase.concat("category/children/").concat(String.valueOf(choice)),
					CategoryDto[].class);
			if (children.length > 0) {
				bot.sendMessage(id, "Escolha um código de categoria filha:");
				String categoryList = new String();
				for (int i = 0; i < children.length; i++) {
					CategoryDto c = children[i];
					categoryList += (c.getId()) + ": " + c.getDescription() + "\n";
				}
				bot.sendMessage(id, categoryList);
			} else {
				bot.sendMessage(id, "A categoria escolhida está vazia.");
				interactionStage = 3;
				stage3();
			}
		} else {
			products = restTemplate.getForObject(URlBase.concat("/product/category/".concat(String.valueOf(choice))),
					ProductDto[].class);
			bot.sendMessage(id, "Escolha um código de produto:");
			String productList = new String();
			for (int i = 0; i < products.length; i++) {
				ProductDto c = products[i];
				productList += (c.getId()) + ": " + c.getDescription() + " - R$ " + c.getPrice() + "\n";
			}
			bot.sendMessage(id, productList);
			interactionStage = 5;
		}
	}

	public void stage5() throws TelegramApiException {
		String id = String.valueOf(chatId);
		if ((choice = getChosenProduct(m.getText(), products)) != -1) {
			products = restTemplate.getForObject(
					URlBase.concat("/product/category/").concat(String.valueOf(chosenCategory.getId())),
					ProductDto[].class);
			for (ProductDto p : products) {
				if (p.getId() == choice) {
					chosenProduct = p;
					interactionStage = 6;
					stage6();
					break;
				}
			}
		} else {
			bot.sendMessage(id, "Código inválido. Escolha um código de produto válido:");
			String productList = new String();
			for (int i = 0; i < products.length; i++) {
				ProductDto p = products[i];
				productList += (p.getId()) + ": " + p.getDescription() + " - R$ " + p.getPrice() + "\n";
			}
			bot.sendMessage(id, productList);

		}
	}

	public void stage6() throws TelegramApiException {
		String id = String.valueOf(chatId);
		ByteArrayInputStream stream;
		if (chosenProduct.getPhoto() != null) {
			stream = new ByteArrayInputStream(chosenProduct.getPhoto());
			bot.sendPhoto(id, new InputFile(stream, id));
		}
		bot.sendMessage(id, "Informações técnicas: " + chosenProduct.getInfoTec());
		bot.sendMessage(id, "Você pode pagar R$ 50,00 para obter garantia de 1 mês.");
		bot.sendMessage(id, "Para comprar o produto, " + "visite https://www.magazineluiza.com.br/busca/"
				+ chosenProduct.getDescription());
		bot.sendMessage(id, "Deseja continuar buscando produtos? (Sim/Não)");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<HistoryDto> requestEntity = new HttpEntity<HistoryDto>(
				new HistoryDto(chatId, chosenProduct.getId(), Instant.now()), headers);
		restTemplate.postForObject(URlBase.concat("/history"), requestEntity, HistoryDto.class);
		interactionStage = 7;
	}

	public void stage7() throws TelegramApiException {
		String id = String.valueOf(chatId);
		String answer = m.getText().toLowerCase();
		if (answer.equals("sim") || answer.equals("s") || answer.equals("ss")) {
			bot.sendMessage(id, "Você escolheu continuar consultando.");
			interactionStage = 3;
			stage3();
		} else if (answer.equals("não") || answer.equals("n") || answer.equals("nao") || answer.equals("nn")) {
			bot.sendMessage(id, "Você escolheu parar de consultar. Volte sempre!");
			exit = true;
		} else {
			bot.sendMessage(id, "Não entendi. Por favor, responda com \"sim\" ou \"não\"");
		}
	}

	/*
	 * getChatId
	 * Objetivo: retornar o thread do objeto relacionado
	 * Retorno: long
	 * Parâmetros: nenhum
	 */
	public long getChatId() {
		return chatId;
	}

	/*
	 * setChatId
	 * Objetivo: setar o chatId do objeto relacionado
	 * Retorno: void
	 * Parâmetros: long chatId
	 */
	public void setChatId(long chatId) {
		this.chatId = chatId;
	}

	/*
	 * getThread
	 * Objetivo: retornar a thread do objeto relacionado
	 * Retorno: thread
	 * Parâmetros: nenhum
	 */
	public Thread getThread() {
		return thread;
	}

	/*
	 * setThread
	 * Objetivo: setar a thread do objeto relacionado
	 * Retorno: void
	 * Parâmetros: Thread thread
	 */
	public void setThread(Thread thread) {
		this.thread = thread;
	}
}
