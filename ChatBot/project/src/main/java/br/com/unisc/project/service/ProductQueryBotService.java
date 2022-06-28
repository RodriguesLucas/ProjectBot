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

// Classe principal do bot
@Component
public class ProductQueryBotService extends TelegramLongPollingBot implements Callback {

	private ArrayList<Session> activeSessions = new ArrayList<>(); // Sessões de clientes ativas
	private ArrayList<Update> updateList = new ArrayList<>(); // Lista de atualiações recebidas

	// Método chamado quando o bot recebe mensagens
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

	// Retorna o nome do bot
	@Override
	public String getBotUsername() {
		return "TrabalhoChatBot";
	}

	// Retorna o token do bot
	@Override
	public String getBotToken() {
		return "5393627617:AAEC3k1JBaUEe_oL01XpcsQQD-WTTsy4olg";
	}

	// Método que remove a sessão quando esta termina
	@Override
	public void callback(Object o) {
		Session s = (Session) o;
		activeSessions.remove(s);
	}

	// Método para enviar mensagens
	public Message sendMessage(String chatId, String text) throws TelegramApiException {
		return execute(SendMessage.builder().chatId(chatId).text(text).build());
	}

	// Método para enviar imagens
	public Message sendPhoto(String chatId, InputFile photo) throws TelegramApiException {
		return execute(SendPhoto.builder().chatId(chatId).photo(photo).build());
	}

	// Método para checar se há atualizações para uma dada sessão
	public synchronized Update checkUpdates(long chatId) {
		for (Update u : updateList)
			if (u.getMessage().getChatId() == chatId) {
				updateList.remove(u);
				return u;
			}
		return null;
	}
}
