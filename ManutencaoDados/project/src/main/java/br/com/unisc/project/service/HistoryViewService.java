package br.com.unisc.project.service;

import org.springframework.web.client.RestTemplate;

import br.com.unisc.project.dtos.ClientDto;
import br.com.unisc.project.dtos.HistoryDto;
import br.com.unisc.project.dtos.ProductDto;

public class HistoryViewService {
	private static final String URlBase = "http://localhost:8080";

	public ClientDto[] findAllClients() {
		String uri = URlBase.concat("/client/allInfo");
		RestTemplate restTemplate = new RestTemplate();
		ClientDto[] clientDto = restTemplate.getForObject(uri, ClientDto[].class);
		return clientDto;
	}

	public HistoryDto[] findAllQueriesByClientId(long clientId) {
		String uri = URlBase.concat("/history/clientId/").concat(String.valueOf(clientId));
		RestTemplate restTemplate = new RestTemplate();
		HistoryDto[] historyDto = restTemplate.getForObject(uri, HistoryDto[].class);
		return historyDto;
	}

	public ProductDto findProductById(long id) {
		String uri = URlBase.concat("/product/").concat(String.valueOf(id));
		RestTemplate restTemplate = new RestTemplate();
		ProductDto productDto = restTemplate.getForObject(uri, ProductDto.class);
		return productDto;
	}
}
