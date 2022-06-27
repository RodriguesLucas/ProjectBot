package br.com.unisc.project.service;

import java.io.ByteArrayInputStream;
import java.time.Instant;
import java.util.LinkedList;
import java.util.Queue;

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

// Sessão de um cliente
public class Session implements Runnable {
	// Atributos
	private long chatId;
	private Thread thread;
	private Queue<Update> updateQueue;
	private Callback cb;
	private ProductQueryBotService bot;
	private final int maxSeconds = 180;
	private final String greeting = "Olá";
	private int interactionStage;
	private static final String URlBase = "http://localhost:8080/";
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

	// Construtor
	public Session(long chatId, Callback cb, ProductQueryBotService bot) {
		this.updateQueue = new LinkedList<>();
		this.chatId = chatId;
		this.cb = cb;
		this.bot = bot;
		this.interactionStage = 1;
	}

	// Função que recebe as mensagens da sessão atual
	public void sendUpdate(Update update) {
		updateQueue.add(update);
	}

	// Cria thread para a sessão
	public void createThread() {
		thread = new Thread(this);
		thread.start();
	}

	// Controla o fluxo da sessão
	@Override
	public void run() {
		Update update = updateQueue.poll();
		Instant lastUpdate = Instant.ofEpochSecond(update.getMessage().getDate());

		exit = false;

		while (Instant.now().minusSeconds(lastUpdate.getEpochSecond()).getEpochSecond() < maxSeconds && !exit) {
			if (update == null) {
				update = updateQueue.poll();
				continue;
			}
			m = update.getMessage();
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
				update = updateQueue.poll();
		}
		if (!exit) {
			try {
				bot.sendMessage(String.valueOf(chatId), "Tempo de resposta esgotado. Recomecemos do início.");
			} catch (TelegramApiException e) {

			}
		}
		cb.callback(this);
	}

	// Verifica o produto escolhido
	private long getChosenProduct(String text, ProductDto[] products) {
		try {
			return Long.valueOf(text);
		} catch (NumberFormatException e) {
			for (ProductDto p : products) {
				if (p.getDescription() != null && p.getDescription().equalsIgnoreCase(text))
					return p.getId();
			}
		}
		return -1;
	}

	// Verifica a categoria escolhida
	private long getChosenCategory(String text, CategoryDto[] categories) {
		try {
			return Long.valueOf(text);
		} catch (NumberFormatException e) {
			for (CategoryDto c : categories) {
				if (c.getDescription() != null && c.getDescription().equalsIgnoreCase(text))
					return c.getId();
			}
		}
		return -1;
	}

	// Valida o número de telefone
	private boolean isPhoneNumberValid(String text) {
		if (!text.isBlank())
			return true;
		return false;
	}

	// Valida o CPF ou CNPJ
	private boolean isCpfCnpjValid(String text) {
		if (!text.isBlank() && text.length() >= 11 && text.length() <= 14)
			return true;
		return false;
	}

	// Compara uma sessão com a sessão atual
	@Override
	public boolean equals(Object o) {
		Session s = (Session) o;
		if (s.getChatId() == chatId)
			return true;
		return false;
	}

	private void stage1() throws TelegramApiException {
		String id = String.valueOf(chatId);
		ClientDto currentClient = restTemplate.getForObject(URlBase.concat("/client/id/").concat(id), ClientDto.class);
		if (currentClient != null) {
			bot.sendMessage(id, greeting + ", " + currentClient.getName() + "!");
			interactionStage = 3;
			stage3();
		} else {
			bot.sendMessage(id, greeting + ", cliente!");
			bot.sendMessage(id, "Por favor, informe seu nome: ");
			client.setChatId(chatId);
			interactionStage = 2;
		}
	}

	private void stage2() throws TelegramApiException {
		String id = String.valueOf(chatId);
		if (client.getName() == null) {
			if (!m.getText().isBlank()) {
				client.setName(m.getText());
				bot.sendMessage(id, "Entendi. Seu nome é " + client.getName());
				bot.sendMessage(id, "Por favor, informe seu cpf/cnpj:");
			} else
				bot.sendMessage(id, "Seu nome não pode estar em branco. Tente novamente.");
			return;
		} else if (client.getCpfCnpj() == null) {
			if (isCpfCnpjValid(m.getText())) {
				client.setCpfCnpj(m.getText());
				bot.sendMessage(id, "Entendi. Seu cpf/cnpj é " + client.getCpfCnpj());
				bot.sendMessage(id, "Por favor, informe seu número de telefone:");
			} else
				bot.sendMessage(id, "O cpf/cnpj informado não é válido. Tente novamente.");
			return;
		} else {
			if (isPhoneNumberValid(m.getText())) {
				client.setPhoneNumber(m.getText());
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<ClientDto> requestEntity = new HttpEntity<ClientDto>(client, headers);
				restTemplate.postForObject(URlBase.concat("client/").concat(id), requestEntity, ClientDto.class);
				bot.sendMessage(id, "Entendi. Seu número de telefone é " + client.getPhoneNumber());
				bot.sendMessage(id, "Parabéns. Você foi cadastrado no sistema.");
				interactionStage = 3;
				stage3();
			} else
				bot.sendMessage(id, "O número de telefone informado não é válido. Tente novamente.");
			return;
		}
	}

	private void stage3() throws TelegramApiException {
		String id = String.valueOf(chatId);
		categories = restTemplate.getForObject(URlBase.concat("category"), CategoryDto[].class);
		if (categories == null) {
			bot.sendMessage(id, "Não há categorias no banco. Volte mais tarde.");
			interactionStage = -1;
		}
		bot.sendMessage(id, "Escolha um código de categoria:");
		String categoryList = new String();
		for (int i = 0; i < categories.length; i++) {
			CategoryDto c = categories[i];
			categoryList += (c.getId()) + ": " + c.getDescription() + "\n";
		}
		bot.sendMessage(id, categoryList);
		interactionStage = 4;
	}

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

	// Getters e setters
	public long getChatId() {
		return chatId;
	}

	public void setChatId(long chatId) {
		this.chatId = chatId;
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}
}
