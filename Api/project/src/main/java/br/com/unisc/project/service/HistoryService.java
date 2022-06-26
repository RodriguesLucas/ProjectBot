package br.com.unisc.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.unisc.project.dtos.HistoryDto;
import br.com.unisc.project.entities.ClientEntity;
import br.com.unisc.project.entities.HistoryEntity;
import br.com.unisc.project.entities.ProductEntity;
import br.com.unisc.project.repositories.HistoryRepository;

@Service
public class HistoryService {
	@Autowired
	private HistoryRepository historyRepository;

	public HistoryDto add(HistoryDto history) {
		HistoryEntity entity = new HistoryEntity();
		ClientEntity client = new ClientEntity();
		client.setId(history.getClientId());
		entity.setClientId(client);
		ProductEntity product = new ProductEntity();
		product.setId(history.getClientId());
		entity.setProductId(product);
		entity.setInstant(history.getDate());
		return new HistoryDto(historyRepository.save(entity));
	}

}
