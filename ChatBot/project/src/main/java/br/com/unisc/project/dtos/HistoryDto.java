/* 
 *  Classe que histório - regra de negócio
 * Autores @nicolasfischer @brunobolzan @lucasrodrigues 
 */
package br.com.unisc.project.dtos;

import java.time.Instant;


public class HistoryDto {
	// Atributos
	private Long clientId;
	private Long productId;
	private Instant date;
	
	//construtor sem especificação de parâmetros para atribuição
	public HistoryDto() {
		
	}
	
	/*
	 * sobrecarga de método construtor - HistoryDto
	 * Objetivo: Método que instaciará um objeto oriundo da classe a partir do recebimento de argumentos padronizados
	 * Retorno: void
	 * Parâmetros: Long clientId, Long productId, Instant date
	 */
	public HistoryDto(Long clientId, Long productId, Instant date) {
		this.clientId = clientId;
		this.productId = productId;
		this.date = date;
	}

	/*
	 * getClientId
	 * Objetivo: retornar o clientId do objeto relacionado
	 * Retorno: long
	 * Parâmetros: nenhum
	 */
	public Long getClientId() {
		return clientId;
	}

	/*
	 * setClientId
	 * Objetivo: setar o clientId do objeto relacionado
	 * Retorno: void
	 * Parâmetros: Long id
	 */
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	/*
	 * getProductId
	 * Objetivo: retornar o productId do objeto relacionado
	 * Retorno: long
	 * Parâmetros: nenhum
	 */
	public Long getProductId() {
		return productId;
	}

	/*
	 * setProductId
	 * Objetivo: setar o productId do objeto relacionado
	 * Retorno: void
	 * Parâmetros: Long productId
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/*
	 * getProductId
	 * Objetivo: retornar o date do objeto relacionado
	 * Retorno: Instant
	 * Parâmetros: nenhum
	 */
	public Instant getDate() {
		return date;
	}

	/*
	 * setDate
	 * Objetivo: setar o date do objeto relacionado
	 * Retorno: void
	 * Parâmetros: Instant date
	 */
	public void setDate(Instant date) {
		this.date = date;
	}
	
	
}
