package br.com.unisc.project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.unisc.project.dtos.ClientDto;
import br.com.unisc.project.entities.ClientEntity;
import br.com.unisc.project.repositories.ClienteRepository;

@Service
public class ClientService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public List<ClientDto> findAll() {
		List<ClientEntity> entities = clienteRepository.findAll();
		return entities.stream().map(ClientDto::new).collect(Collectors.toList());
	}

}
