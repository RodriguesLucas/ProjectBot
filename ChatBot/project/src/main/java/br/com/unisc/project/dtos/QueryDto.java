package br.com.unisc.project.dtos;

import java.time.Instant;

// DTO para inserir consultas no banco de dados
public class QueryDto {
	// Atributos
	private Long clientId;
	private Long productId;
	private Instant date;
	
	// Construtor
	public QueryDto() {
		
	}
	
	// Construtor com todos os atributos
	public QueryDto(Long clientId, Long productId, Instant date) {
		this.clientId = clientId;
		this.productId = productId;
		this.date = date;
	}



	// Getters e setters
	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}
	
	
}
