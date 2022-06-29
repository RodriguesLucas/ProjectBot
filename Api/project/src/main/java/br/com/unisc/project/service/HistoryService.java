package br.com.unisc.project.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.unisc.project.dtos.HistoryDto;
import br.com.unisc.project.entities.ClientEntity;
import br.com.unisc.project.entities.HistoryEntity;
import br.com.unisc.project.entities.ProductEntity;
import br.com.unisc.project.repositories.ClienteRepository;
import br.com.unisc.project.repositories.HistoryRepository;
import br.com.unisc.project.repositories.ProductRepository;

@Service
public class HistoryService {
	@Autowired
	private HistoryRepository historyRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ProductRepository productRepository;

	public HistoryDto add(HistoryDto history) {
		HistoryEntity entity = new HistoryEntity();
		Optional<ClientEntity> client = clienteRepository.findById(history.getClientId());
		entity.setClientId(client.get());
		Optional<ProductEntity> product = productRepository.findById(history.getProductId());
		if(product.isEmpty())
			throw new RuntimeException("Produto removido.");
		entity.setProductId(product.get());
		entity.setInstant(history.getDate());
		return new HistoryDto(historyRepository.save(entity));
	}

	public List<HistoryDto> findByClientId(Long id) {
		List<HistoryEntity> entity = historyRepository.findByClientId(id);
		return entity.stream().map(HistoryDto::new).collect(Collectors.toList());
	}

}
