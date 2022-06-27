package br.com.unisc.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.unisc.project.dtos.ClientDto;
import br.com.unisc.project.service.ClientService;

@RestController
@RequestMapping(value = "/client")
public class ClientController {
	@Autowired
	private ClientService clientService;

	@GetMapping()
	public List<ClientDto> findAll(){
		return clientService.findAll();
	}
	
	@GetMapping(value = "/id/{id}")
	public ClientDto findClientById(@PathVariable Long id) {
		return clientService.findClientById(id);
	}
	
	@PostMapping(value = "/{id}")
	public ClientDto add(@RequestBody ClientDto clientDto, @PathVariable(value = "id") Long chatId){
		return clientService.add(clientDto, chatId);
	}
}
