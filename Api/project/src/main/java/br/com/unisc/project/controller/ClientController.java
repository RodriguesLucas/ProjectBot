package br.com.unisc.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unisc.project.dtos.ClientDto;
import br.com.unisc.project.service.ClientService;

@RestController
@RequestMapping(value = "/client")
public class ClientController {
	@Autowired
	private ClientService clientService;

	@GetMapping
	public List<ClientDto> findAll(){
		return clientService.findAll();
	}
	
	
}
