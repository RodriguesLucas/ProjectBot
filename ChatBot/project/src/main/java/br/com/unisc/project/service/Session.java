package br.com.unisc.project.service;

import java.io.ByteArrayInputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Queue;

import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import br.com.unisc.project.dtos.CategoryDto;
import br.com.unisc.project.dtos.ClientDto;
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

	// Construtor
	public Session(long chatId, Callback cb, ProductQueryBotService bot) {
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
		String id = String.valueOf(chatId);
		ClientDto client = new ClientDto();
		boolean exit = false;
		while (Instant.now().minusSeconds(lastUpdate.getEpochSecond()).getEpochSecond() < maxSeconds && !exit) {
			if(update == null) {
				update = updateQueue.poll();
				continue;
			}
			Message m = update.getMessage();
			ArrayList<CategoryDto> categories = null;
			ArrayList<CategoryDto> children = null;
			ArrayList<ProductDto> products = null;
			ProductDto chosenProduct = null;
			switch (interactionStage) {
			case 1:
				try {
					bot.sendMessage(id, greeting);
				} catch (TelegramApiException e) {
				}
				if (true /* (client = banco.getClientById(chatId)) != null */)
					interactionStage = 3;
				else
					interactionStage = 2;
				break;
			case 2:
				if (client.getChatId() == null) {
					try {
						bot.sendMessage(id, "Por favor, informe seu nome: ");
					} catch (TelegramApiException e) {

					}
					client.setChatId(chatId);
				} else if (client.getName() == null) {
					if (!m.getText().isBlank())
						client.setName(m.getText());
					else
						try {
							bot.sendMessage(id, "Seu nome não pode estar em branco.");
						} catch (TelegramApiException e) {

						}
					break;
				} else if (client.getCpfCnpj() == null) {
					if (isCpfCnpjValid(m.getText()))
						client.setCpfCnpj(m.getText());
					else
						try {
							bot.sendMessage(id, "O cpf/cnpj informado não é válido.");
						} catch (TelegramApiException e) {

						}
					break;
				} else {
					if (isPhoneNumberValid(m.getText())) {
						client.setPhoneNumber(m.getText());
						// banco.insertClient(client);
						interactionStage = 3;
					} else
						try {
							bot.sendMessage(id, "O número de telefone informado não é válido.");
						} catch (TelegramApiException e) {

						}
					break;
				}
				break;
			case 3:
				categories = null;// banco.getCategories();
				if (categories.isEmpty()) {
					try {
						bot.sendMessage(id, "Não há categorias no banco. Volte mais tarde.");
					} catch (TelegramApiException e) {

					}
					cb.callback(this);
					interactionStage = -1;
				}
				try {
					bot.sendMessage(id, "Escolha um código de categoria:");
				} catch (TelegramApiException e) {

				}
				String categoryList = new String();
				for (int i = 0; i < categories.size(); i++) {
					CategoryDto c = categories.get(i);
					categoryList += (c.getId()) + ": " + c.getDescription();
				}
				try {
					bot.sendMessage(id, categoryList);
				} catch (TelegramApiException e) {
					
				}
				interactionStage = 4;
				break;
			case 4:
				categories = null;// banco.getCategories();
				int choice;
				if ((choice = getChosenCategory(m.getText(), categories)) != -1) {
					children = null; // banco.getChildrenOfCategoryById(choice);
					if (!children.isEmpty()) {
						try {
							bot.sendMessage(id, "Escolha um código de categoria filha:");
						} catch (TelegramApiException e) {

						}
						categoryList = new String();
						for (int i = 0; i < categories.size(); i++) {
							CategoryDto c = categories.get(i);
							categoryList += (c.getId()) + ": " + c.getDescription();
						}
						try {
							bot.sendMessage(id, categoryList);
						} catch (TelegramApiException e) {
							
						}
					} else {
						products = null; //banco.getProductsByCategoryId(choice)
						if(products.isEmpty()) {
							try {
								bot.sendMessage(id, "A categoria escolhida está vazia.");
							} catch (TelegramApiException e) {
								
							}
							interactionStage = 3;
						} else {
							try {
								bot.sendMessage(id, "Escolha um código de produto:");
							} catch (TelegramApiException e) {

							}
							String productList = new String();
							for (int i = 0; i < products.size(); i++) {
								ProductDto c = products.get(i);
								productList += (c.getId()) + ": " + c.getDescription() + c.getPrice();
							}
							try {
								bot.sendMessage(id, productList);
							} catch (TelegramApiException e) {
								
							}
							interactionStage = 5;
						}
					}
				}
				break;
			case 5:
				if ((choice = getChosenProduct(m.getText(), products)) != -1) {
					for(ProductDto p : products) {
						if(p.getId().intValue() == choice) {
							chosenProduct = p;
							interactionStage = 6;
							break;
						}
					}
				}else {
					try {
						bot.sendMessage(id, "Código inválido. Escolha um código de produto válido:");
					} catch (TelegramApiException e) {

					}
					String productList = new String();
					for (int i = 0; i < products.size(); i++) {
						ProductDto p = products.get(i);
						productList += (p.getId()) + ": " + p.getDescription() + " - R$ " + p.getPrice();
					}
					try {
						bot.sendMessage(id, productList);
					} catch (TelegramApiException e) {
						
					}
				}
				break;
			case 6:
				ByteArrayInputStream stream = new ByteArrayInputStream(chosenProduct.getPhoto());
				try {
					bot.sendPhoto(id, new InputFile(stream, ""));
					bot.sendMessage(id, "Informações técnicas: " + chosenProduct.getInfoTec());
					bot.sendMessage(id, "Você pode pagar R$ 50,00 para obter garantia de 1 mês.");
					bot.sendMessage(id, "Para comprar o produto, " +
							"visite https://www.magazineluiza.com.br/busca/" +
							chosenProduct.getDescription());
					bot.sendMessage(id, "Deseja continuar buscando produtos? (Sim/Não)");
					//banco.insertQuery(new QueryDto(client.getId() chosenProduct.getId()));
					interactionStage = 7;
				} catch (TelegramApiException e) {
					
				}
				break;
			case 7:
				String answer = m.getText().toLowerCase();
				if(answer.equals("sim") || answer.equals("s") || answer.equals("ss")) {
					try {
						bot.sendMessage(id, "Você escolheu continuar consultando.");
					} catch (TelegramApiException e) {
						
					}
					interactionStage = 3;
				} else if(answer.equals("não") || answer.equals("n") ||
						  answer.equals("nao") || answer.equals("nn")) {
					try {
						bot.sendMessage(id, "Você escolheu parar de consultar. Volte sempre!");
					} catch (TelegramApiException e) {
						
					}
					cb.callback(this);
					interactionStage = -1;
				} else {
					try {
						bot.sendMessage(id, "Não entendi. Por favor, responda com \"sim\" ou \"não\"");
					} catch (TelegramApiException e) {
						
					}
				}
				
				break;
			case -1:
				exit = true;
				break;
			default:
				break;
			}
			update = updateQueue.poll();
		}
		if(!exit) {
			try {
				bot.sendMessage(String.valueOf(chatId), "Tempo de resposta esgotado.");
			} catch (TelegramApiException e) {
	
			}
		}
		cb.callback(this);
	}

	// Verifica o produto escolhido
	private int getChosenProduct(String text, ArrayList<ProductDto> products) {
		try {
			return Integer.valueOf(text);
		} catch (NumberFormatException e) {
			for (ProductDto p : products) {
				if (p.getDescription().equalsIgnoreCase(text))
					return p.getId().intValue();
			}
		}
		return -1;
	}
	
	// Verifica a categoria escolhida
	private int getChosenCategory(String text, ArrayList<CategoryDto> categories) {
		try {
			return Integer.valueOf(text);
		} catch (NumberFormatException e) {
			for (CategoryDto c : categories) {
				if (c.getDescription().equalsIgnoreCase(text))
					return c.getId().intValue();
			}
		}
		return -1;
	}
	
	/*ARRUMAR*/
	// Valida o número de telefone
	private boolean isPhoneNumberValid(String text) {
		if (!text.isBlank())
			return true;
		return false;
	}
	
	/*ARRUMAR*/
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
