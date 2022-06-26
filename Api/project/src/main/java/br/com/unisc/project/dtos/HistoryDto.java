package br.com.unisc.project.dtos;

import java.time.Instant;

import br.com.unisc.project.entities.HistoryEntity;

// DTO para inserir consultas no banco de dados
public class HistoryDto {
	// Atributos
	private Long clientId;
	private Long productId;
	private Instant date;
	
	public HistoryDto(HistoryEntity entity) {
		this.clientId = entity.getClientId().getId();
		this.productId = entity.getProductId().getId();
		this.date = entity.getInstant();
	}
	
	// Construtor
	public HistoryDto() {
		
	}
	
	// Construtor com todos os atributos
	public HistoryDto(Long clientId, Long productId, Instant date) {
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
