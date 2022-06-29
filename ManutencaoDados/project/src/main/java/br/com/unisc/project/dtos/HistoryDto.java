/* 
 *  Classe que trata informações do objeto histórico. DTO para inserir consultas no banco de dados
 * Autores @nicolasfischer @brunobolzan @lucasrodrigues 
 */

package br.com.unisc.project.dtos;

import java.time.Instant;


public class HistoryDto {
	// Atributos
	private Long clientId;
	private Long productId;
	private Instant date;
	
	/*
	 * construtor da classe
	 * Objetivo: instanciar um objeto
	 * Parâmetros: nenhum
	 */
	public HistoryDto() {
		
	}
	
	/*
	 * sobrecarga do construtor
	 * Parâmetros: Long chatId, String cpfCnpj, String name, String phoneNumber, long numQueries, double priceMean
	 */
	public HistoryDto(Long clientId, Long productId, Instant date) {
		this.clientId = clientId;
		this.productId = productId;
		this.date = date;
	}

	/*
	 * getClientId
	 * Objetivo: retornar clientId do objeto relacionado
	 * Retorno: long
	 * Parâmetros: nenhum
	 */
	public Long getClientId() {
		return clientId;
	}

	/*
	 * setClientId
	 * Objetivo: setar clientId do objeto relacionado
	 * Retorno: nenhum
	 * Parâmetros: Long clientId
	 */
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	/*
	 * getProductId
	 * Objetivo: retornar productId do objeto relacionado
	 * Retorno: Long
	 * Parâmetros: nenhum
	 */
	public Long getProductId() {
		return productId;
	}

	/*
	 * setProductId
	 * Objetivo: setar productId do objeto relacionado
	 * Retorno: nenhum
	 * Parâmetros: Long productId
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/*
	 * getDate
	 * Objetivo: retornar date do objeto relacionado
	 * Retorno: Instant
	 * Parâmetros: nenhum
	 */
	public Instant getDate() {
		return date;
	}

	/*
	 * setDate
	 * Objetivo: setar date do objeto relacionado
	 * Retorno: nenhum
	 * Parâmetros: Instant date
	 */
	public void setDate(Instant date) {
		this.date = date;
	}
	
	
}
