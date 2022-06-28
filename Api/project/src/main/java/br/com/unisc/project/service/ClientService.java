package br.com.unisc.project.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unisc.project.dtos.ClientDto;
import br.com.unisc.project.dtos.ClienteAllDto;
import br.com.unisc.project.entities.ClientEntity;
import br.com.unisc.project.repositories.ClienteRepository;

@Service
public class ClientService {

	@Autowired
	private ClienteRepository clientRepository;
	
	public List<ClientDto> findAll() {
		List<ClientEntity> entities = clientRepository.findAll();
		return entities.stream().map(ClientDto::new).collect(Collectors.toList());
	}
	
	@Transactional
	public ClientDto findClientById(Long chatId) {
		Optional<ClientEntity> clientOptional = clientRepository.findById(chatId);
		if(clientOptional.isPresent()) {
			return new ClientDto(clientOptional.get());
			
		}
		else
			return null;
	}
	@Transactional
	public ClientDto add(ClientDto clientDto, Long chatId) {
		Optional<ClientEntity> clientOptional = clientRepository
				.findById(chatId);
		if(clientOptional.isEmpty()) {
			ClientEntity clientEntity = new ClientEntity();
			clientEntity.setId(chatId);
			clientEntity.setCpf_cnpj(clientDto.getCpfCnpj());
			clientEntity.setName(clientDto.getName());
			clientEntity.setPhone(clientDto.getPhoneNumber());
			return new ClientDto(clientRepository.save(clientEntity));
		}
		throw new RuntimeException("Cliente já existe!");
	}

	public List<ClienteAllDto> findClientAllDto() {
		
		
		List<ClienteAllDto> allDtos = clientRepository.findClientAllDto();
		return allDtos;
	}
}
