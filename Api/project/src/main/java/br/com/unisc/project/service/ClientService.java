package br.com.unisc.project.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unisc.project.dtos.ClientAllInfoDto;
import br.com.unisc.project.dtos.ClientDto;
import br.com.unisc.project.dtos.HistoryDto;
import br.com.unisc.project.dtos.ProductDto;
import br.com.unisc.project.entities.ClientEntity;
import br.com.unisc.project.entities.HistoryEntity;
import br.com.unisc.project.entities.ProductEntity;
import br.com.unisc.project.repositories.ClienteRepository;
import br.com.unisc.project.repositories.HistoryRepository;
import br.com.unisc.project.repositories.ProductRepository;

@Service
public class ClientService {

	@Autowired
	private ClienteRepository clientRepository;

	@Autowired
	private HistoryRepository historyRepository;

	@Autowired
	private ProductRepository productRepository;

	public List<ClientDto> findAll() {
		List<ClientEntity> entities = clientRepository.findAll();
		return entities.stream().map(ClientDto::new).collect(Collectors.toList());
	}

	@Transactional
	public ClientDto findClientById(Long chatId) {
		Optional<ClientEntity> clientOptional = clientRepository.findById(chatId);
		if (clientOptional.isPresent()) {
			return new ClientDto(clientOptional.get());

		} else
			return null;
	}

	@Transactional
	public ClientDto add(ClientDto clientDto, Long chatId) {

		Optional<ClientEntity> clientOptional = clientRepository.findById(chatId);
		if (clientOptional.isEmpty()) {
			ClientEntity clientEntity = new ClientEntity();
			clientEntity.setId(chatId);
			clientEntity.setCpf_cnpj(clientDto.getCpfCnpj());
			clientEntity.setName(clientDto.getName());
			clientEntity.setPhone(clientDto.getPhoneNumber());
			return new ClientDto(clientRepository.saveAndFlush(clientEntity));
		}
		throw new RuntimeException("Cliente já existe!");
	}

	public List<ClientAllInfoDto> findAllClientsAllInfo() {
		List<ClientDto> clientDtos = findAll();
		List<ProductEntity> productEntities = productRepository.findAll();
		List<ProductDto> productDtos = productEntities.stream().map(ProductDto::new).collect(Collectors.toList());
		List<ClientAllInfoDto> dtos = new ArrayList<>();
		for (ClientDto c : clientDtos) {
			ClientAllInfoDto client = new ClientAllInfoDto(
					c.getChatId(), c.getCpfCnpj(), c.getName(), c.getPhoneNumber(), 0, 0);
			List<HistoryEntity> historyEntities = historyRepository.findByClientId(c.getChatId());
			List<HistoryDto> historyDtos = historyEntities.stream().map(HistoryDto::new).collect(Collectors.toList());
			long count = 0;
			BigDecimal mean = new BigDecimal(0);
			for (HistoryDto h : historyDtos) {
				if (h.getClientId().longValue() == c.getChatId().longValue()) {
					count++;
					mean.add(findPriceById(h.getProductId(), productDtos));
				}				
			}
			if(count == 0)
				continue;
			client.setNumQueries(count);
			client.setPriceMean((mean.divide(new BigDecimal(count))).doubleValue());
			dtos.add(client);
		}
		return dtos;
	}
	
	private BigDecimal findPriceById(long id, List<ProductDto> pList) {
		for(ProductDto p : pList) {
			if(p.getId() == id)
				return p.getPrice();
		}
		return null;
	}
}
