package br.com.unisc.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unisc.project.dtos.ClientDto;
import br.com.unisc.project.dtos.ClientAllInfoDto;
import br.com.unisc.project.entities.ClientAllInfoEntity;
import br.com.unisc.project.entities.ClientEntity;
import br.com.unisc.project.repositories.ClienteAllInfoRepository;
import br.com.unisc.project.repositories.ClienteRepository;

@Service
public class ClientService {

	@Autowired
	private ClienteRepository clientRepository;
	@Autowired
	private ClienteAllInfoRepository clientAllInfoRepository;
	
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

	public List<ClientAllInfoDto> findClientAllDto() {
		List<ClientAllInfoEntity> allEntities = clientAllInfoRepository.findClientAllDto();
		List<ClientAllInfoDto> allDtos = new ArrayList<ClientAllInfoDto>();
		for(ClientAllInfoEntity e : allEntities) {
			allDtos.add(new ClientAllInfoDto(e));
		}
		return allDtos;
	}
}
