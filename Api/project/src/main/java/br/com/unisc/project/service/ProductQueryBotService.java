package br.com.unisc.project.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import br.com.unisc.project.entities.ProductEntity;
import br.com.unisc.project.repositories.ProductRepository;

@Component
public class ProductQueryBotService extends TelegramLongPollingBot {
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public void onUpdateReceived(Update update) {
		String message = update.getMessage().getText().toLowerCase();
		String chatId = update.getMessage().getChatId().toString();
		System.out.println(chatId);
		System.out.println("Mensagem bot: " + message);
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(
					"E:\\ProjetosJava\\workSpaceJava2022\\projectBot\\src\\main\\java\\projectBot\\service\\LucasImagem2.jpg"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		byte[] bytes = null;
		
		try {
			bytes = IOUtils.toByteArray(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ProductEntity entity = new ProductEntity();
		entity.setPhoto(bytes);
		productRepository.save(entity);

		List<ProductEntity> buscabancos = productRepository.findAll();
		InputStream targetStream = new ByteArrayInputStream(buscabancos.get(0).getPhoto());

		InputFile file32 = new InputFile(targetStream, "test");
		try {
			execute(SendPhoto.builder().chatId(chatId).photo(file32).build());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getBotUsername() {
		return "TrabalhoChatBot";
	}

	@Override
	public String getBotToken() {
		return "5393627617:AAEC3k1JBaUEe_oL01XpcsQQD-WTTsy4olg";
	}

}
