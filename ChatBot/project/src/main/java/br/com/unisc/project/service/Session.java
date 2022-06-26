package br.com.unisc.project.service;

import java.io.ByteArrayInputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
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
	private final String greeting = "Olá, cliente!";
	private int interactionStage;
	private static final String URlBase = "http://localhost:8090/";
	private CategoryDto[] categories = null;
	private CategoryDto[] children = null;
	private ProductDto[] products = null;
	private ProductDto chosenProduct = null;
	private final RestTemplate restTemplate = new RestTemplate();
	private Message m;
	private ClientDto client = new ClientDto();
	private long choice;

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

		boolean exit = false;

		while (Instant.now().minusSeconds(lastUpdate.getEpochSecond()).getEpochSecond() < maxSeconds && !exit) {
			if (update == null) {
				update = updateQueue.poll();
				continue;
			}
			m = update.getMessage();
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
			for (ProductDto p : products) {
				if (p.getId() == Long.valueOf(text))
					return Long.valueOf(text);
				if (p.getDescription().equalsIgnoreCase(text))
					return p.getId();
			}

		} catch (NumberFormatException e) {

		}
		return -1;
	}

	// Verifica a categoria escolhida
	private long getChosenCategory(String text, CategoryDto[] categories) {
		try {
			for (CategoryDto c : categories) {
				if (c.getId() == Long.valueOf(text))
					return Long.valueOf(text);
				if (c.getDescription() != null && c.getDescription().equalsIgnoreCase(text))
					return c.getId();
			}

		} catch (NumberFormatException e) {

		}
		return -1;
	}

	/* ARRUMAR */
	// Valida o número de telefone
	private boolean isPhoneNumberValid(String text) {
		if (!text.isBlank())
			return true;
		return false;
	}

	/* ARRUMAR */
	// Valida o CPF ou CNPJ
	private boolean isCpfCnpjValid(String text) {
		if (!text.isBlank())
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

	private void stage1() {
		String id = String.valueOf(chatId);
		try {
			bot.sendMessage(id, greeting);
		} catch (TelegramApiException e) {
		}
		ClientDto clients = restTemplate.getForObject(URlBase.concat("/client/id/").concat(id), ClientDto.class);
		if (clients != null) {
			interactionStage = 3;
			stage3();
		} else {
			try {
				bot.sendMessage(id, "Por favor, informe seu nome: ");
			} catch (TelegramApiException e) {

			}
			client.setChatId(chatId);
			interactionStage = 2;
		}
	}

	private void stage2() {
		String id = String.valueOf(chatId);
		if (client.getName() == null) {
			if (!m.getText().isBlank()) {
				client.setName(m.getText());
				try {
					bot.sendMessage(id, "Entendi. Seu nome é " + client.getName());
					bot.sendMessage(id, "Por favor, informe seu cpf/cnpj:");
				} catch (TelegramApiException e) {

				}
			} else
				try {
					bot.sendMessage(id, "Seu nome não pode estar em branco.");
				} catch (TelegramApiException e) {

				}
			return;
		} else if (client.getCpfCnpj() == null) {
			if (isCpfCnpjValid(m.getText())) {
				client.setCpfCnpj(m.getText());
				try {
					bot.sendMessage(id, "Entendi. Seu cpf/cnpj é " + client.getCpfCnpj());
					bot.sendMessage(id, "Por favor, informe seu número de telefone:");
				} catch (TelegramApiException e) {

				}
			} else
				try {
					bot.sendMessage(id, "O cpf/cnpj informado não é válido.");
				} catch (TelegramApiException e) {

				}
			return;
		} else {
			if (isPhoneNumberValid(m.getText())) {
				client.setPhoneNumber(m.getText());
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<ClientDto> requestEntity = new HttpEntity<ClientDto>(client, headers);
				restTemplate.postForObject(URlBase.concat("client/").concat(id), requestEntity, ClientDto.class);
				try {
					bot.sendMessage(id, "Entendi. Seu número de telefone é " + client.getPhoneNumber());
					bot.sendMessage(id, "Parabéns. Você foi cadastrado no sistema.");
				} catch (TelegramApiException e) {

				}
				interactionStage = 3;
			} else
				try {
					bot.sendMessage(id, "O número de telefone informado não é válido.");
				} catch (TelegramApiException e) {

				}
			return;
		}
	}

	private void stage3() {
		String id = String.valueOf(chatId);
		categories = restTemplate.getForObject(URlBase.concat("category"), CategoryDto[].class);
		if (categories == null) {
			try {
				bot.sendMessage(id, "Não há categorias no banco. Volte mais tarde.");
			} catch (TelegramApiException e) {

			}
			interactionStage = -1;
		}
		try {
			bot.sendMessage(id, "Escolha um código de categoria:");
		} catch (TelegramApiException e) {

		}
		String categoryList = new String();
		for (int i = 0; i < categories.length; i++) {
			CategoryDto c = categories[i];
			categoryList += (c.getId()) + ": " + c.getDescription() + "\n";
		}
		try {
			bot.sendMessage(id, categoryList);
		} catch (TelegramApiException e) {

		}
		interactionStage = 4;
	}

	private void stage4() {
		categories = restTemplate.getForObject(URlBase.concat("category"), CategoryDto[].class);

		String id = String.valueOf(chatId);
		choice = getChosenCategory(m.getText(), categories);
		products = restTemplate.getForObject(URlBase.concat("/product/category/".concat(String.valueOf(choice))),
				ProductDto[].class);
		if (products.length == 0) {
			if (choice == -1) {
				try {
					bot.sendMessage(id, "Não entendi. Por favor, responda novamente.");
				} catch (TelegramApiException e) {

				}
				return;
			}
			children = restTemplate.getForObject(URlBase.concat("category/children/").concat(String.valueOf(choice)),
					CategoryDto[].class);
			if (children.length > 0) {
				try {
					bot.sendMessage(id, "Escolha um código de categoria filha:");
				} catch (TelegramApiException e) {

				}
				String categoryList = new String();
				for (int i = 0; i < children.length; i++) {
					CategoryDto c = children[i];
					categoryList += (c.getId()) + ": " + c.getDescription() + "\n";
				}
				try {
					bot.sendMessage(id, categoryList);
				} catch (TelegramApiException e) {

				}
			} else {
				try {
					bot.sendMessage(id, "A categoria escolhida está vazia.");
				} catch (TelegramApiException e) {

				}
				interactionStage = 3;
				stage3();
			}
		} else {
			System.out.println("chegou aqui");
			products = restTemplate.getForObject(URlBase.concat("/product/category/".concat(String.valueOf(choice))),
					ProductDto[].class);

			try {
				bot.sendMessage(id, "Escolha um código de produto:");
			} catch (TelegramApiException e) {

			}
			String productList = new String();
			for (int i = 0; i < products.length; i++) {
				ProductDto c = products[i];
				productList += (c.getId()) + ": " + c.getDescription() + " - R$ " + c.getPrice() + "\n";
			}
			try {
				bot.sendMessage(id, productList);
			} catch (TelegramApiException e) {

			}
			interactionStage = 5;

		}
	}

	public void stage5() {
		String id = String.valueOf(chatId);
		if ((choice = getChosenProduct(m.getText(), products)) != -1) {
			products = restTemplate.getForObject(URlBase.concat("/product/category/").concat(id), ProductDto[].class);
			for (ProductDto p : products) {
				if (p.getId() == choice) {
					chosenProduct = p;
					interactionStage = 6;
					break;
				}
			}
		} else {
			try {
				bot.sendMessage(id, "Código inválido. Escolha um código de produto válido:");
			} catch (TelegramApiException e) {

			}
			String productList = new String();
			for (int i = 0; i < products.length; i++) {
				ProductDto p = products[i];
				productList += (p.getId()) + ": " + p.getDescription() + " - R$ " + p.getPrice() + "\n";
			}
			try {
				bot.sendMessage(id, productList);
			} catch (TelegramApiException e) {

			}
		}
	}

	public void stage6() {
		String id = String.valueOf(chatId);
		ByteArrayInputStream stream = new ByteArrayInputStream(chosenProduct.getPhoto());
		try {
			bot.sendPhoto(id, new InputFile(stream, ""));
			bot.sendMessage(id, "Informações técnicas: " + chosenProduct.getInfoTec());
			bot.sendMessage(id, "Você pode pagar R$ 50,00 para obter garantia de 1 mês.");
			bot.sendMessage(id, "Para comprar o produto, " + "visite https://www.magazineluiza.com.br/busca/"
					+ chosenProduct.getDescription());
			bot.sendMessage(id, "Deseja continuar buscando produtos? (Sim/Não)");
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<HistoryDto> requestEntity = new HttpEntity<HistoryDto>(
					new HistoryDto(client.getChatId(), chosenProduct.getId(), Instant.now()), headers);
			restTemplate.postForObject(URlBase.concat("/history/"), requestEntity, HistoryDto.class);
			interactionStage = 7;
		} catch (TelegramApiException e) {

		}
	}

	public void stage7() {
		String id = String.valueOf(chatId);
		String answer = m.getText().toLowerCase();
		if (answer.equals("sim") || answer.equals("s") || answer.equals("ss")) {
			try {
				bot.sendMessage(id, "Você escolheu continuar consultando.");
			} catch (TelegramApiException e) {

			}
			interactionStage = 3;
		} else if (answer.equals("não") || answer.equals("n") || answer.equals("nao") || answer.equals("nn")) {
			try {
				bot.sendMessage(id, "Você escolheu parar de consultar. Volte sempre!");
			} catch (TelegramApiException e) {

			}
			interactionStage = -1;
		} else {
			try {
				bot.sendMessage(id, "Não entendi. Por favor, responda com \"sim\" ou \"não\"");
			} catch (TelegramApiException e) {

			}
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
