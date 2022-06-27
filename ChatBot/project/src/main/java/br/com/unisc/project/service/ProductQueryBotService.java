package br.com.unisc.project.service;

import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
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
	
	// Método chamado quando o bot recebe mensagens
	@Override
	public void onUpdateReceived(Update update) {
		System.out.println("gfghfhfhgfghf");
		long chatId = update.getMessage().getChatId();
		boolean hasActiveSession = false;
		// Checa se já existe uma sessão com o remetente da mensagem
		for (Session s : activeSessions) {
			if (s.getChatId() == chatId) {
				hasActiveSession = true;
				s.sendUpdate(update);
				break;
			}
		}
		// Se não houver sessão do cliente aberta, é criada uma nova
		if (!hasActiveSession) {
			Session s = new Session(chatId, this, this);
			activeSessions.add(s);
			s.sendUpdate(update);
			s.createThread();
		}
		
		 
		 
		  /*System.out.println(Date.from(Instant.ofEpochSecond(update.getMessage().
		 * getDate())).toString()); String message =
		 *update.getMessage().getText().toLowerCase(); //String chatId =
		 * update.getMessage().getChatId().toString(); System.out.println(chatId);
		 * System.out.println("Mensagem bot: " + message); InputStream inputStream =
		 * null; try { inputStream = new FileInputStream(new File(
		 *"E:\\ProjetosJava\\workSpaceJava2022\\projectBot\\src\\main\\java\\projectBot\\service\\LucasImagem2.jpg"
		 * )); } catch (FileNotFoundException e) { e.printStackTrace(); } byte[] bytes =
		 * null;
		 * */
		 //* try { bytes = IOUtils.toByteArray(inputStream); } catch (IOException e) {
		 //* e.printStackTrace(); } // simulação de como salva imagens no banco e pega
		 //* imagens pro bot
		 //* 
		 //* //ProductEntity entity = new ProductEntity(); //entity.setPhoto(bytes);
		  //productRepository.save(entity);
		  
		  //List<ProductEntity> buscabancos = productRepository.findAll();
		  //InputStream targetStream = new
		 // ByteArrayInputStream(buscabancos.get(0).getPhoto());
		  
		  // file32 = new InputFile(targetStream, "test"); //try {
		//execute(SendPhoto.builder().chatId(chatId).photo(file32).build());
		 //execute(SendMessage.builder().chatId(chatId).text("OLAAAAAAAAAAAA").build()
		  //} catch (TelegramApiException e) { //e.printStackTrace(); //}
		 
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

}
