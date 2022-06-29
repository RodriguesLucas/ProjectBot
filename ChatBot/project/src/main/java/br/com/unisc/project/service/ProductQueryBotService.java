/* 
 *  Classe principal do bot, regras mais próximas da conexão
 * Autores @nicolasfischer @brunobolzan @lucasrodrigues 
 */
package br.com.unisc.project.service;

import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class ProductQueryBotService extends TelegramLongPollingBot implements Callback {

	private ArrayList<Session> activeSessions = new ArrayList<>(); // Sessões de clientes ativas
	private ArrayList<Update> updateList = new ArrayList<>(); // Lista de atualiações recebidas


	/*
	 * onUpdateReceived
	 * Objetivo: utilizar quando o bot recebe mensagens
	 * Retorno: nenhum
	 * Parâmetros: Update update
	 */
	@Override
	public void onUpdateReceived(Update update) {
		updateList.add(update);
		long chatId = update.getMessage().getChatId();
		boolean hasActiveSession = false;
		// Checa se já existe uma sessão com o remetente da mensagem
		for (Session s : activeSessions) {
			if (s.getChatId() == chatId) {
				hasActiveSession = true;
				break;
			}
		}
		// Se não houver sessão do cliente aberta, é criada uma nova
		if (!hasActiveSession) {
			Session s = new Session(chatId, this, this);
			activeSessions.add(s);
			s.createThread();
		}
	}

	/*
	 * reescrita do método getBotToken
	 * Objetivo: retornar o nome do bot
	 * Retorno: String
	 * Parâmetros: nenhum
	 */
	@Override
	public String getBotUsername() {
		return "TrabalhoChatBot";
	}

	/*
	 * reescrita do método getBotToken
	 * Objetivo: retornar o token do bot
	 * Retorno: String
	 * Parâmetros: nenhum
	 */
	@Override
	public String getBotToken() {
		return "5393627617:AAEC3k1JBaUEe_oL01XpcsQQD-WTTsy4olg";
	}


	/*
	 * callback
	 * Objetivo: remover a sessão quando ela terminar
	 * Retorno: nenhum
	 * Parâmetros: Object o
	 */
	@Override
	public void callback(Object o) {
		Session s = (Session) o;
		activeSessions.remove(s);
	}


	/*
	 * checkUpdates
	 * Objetivo: enviar mensagens de texto
	 * Retorno: Message
	 * Parâmetros: String chatId, String text
	 */
	public Message sendMessage(String chatId, String text) throws TelegramApiException {
		return execute(SendMessage.builder().chatId(chatId).text(text).build());
	}


	/*
	 * checkUpdates
	 * Objetivo: enviar imagens
	 * Retorno: Message
	 * Parâmetros: String chatId, InputFile photo
	 */
	public Message sendPhoto(String chatId, InputFile photo) throws TelegramApiException {
		return execute(SendPhoto.builder().chatId(chatId).photo(photo).build());
	}

	/*
	 * checkUpdates
	 * Objetivo: checar se há atualizações para uma dada sessão
	 * Parâmetros: long chatId
	 */
	public synchronized Update checkUpdates(long chatId) {
		for (Update u : updateList)
			if (u.getMessage().getChatId() == chatId) {
				updateList.remove(u);
				return u;
			}
		return null;
	}
}
