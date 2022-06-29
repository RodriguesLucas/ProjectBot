/* 
 *  Classe que trata informações do objeto cliente
 * Autores @nicolasfischer @brunobolzan @lucasrodrigues 
 */

package br.com.unisc.project.dtos;

public class ClientDto {
	// atributos
	private Long chatId;
	private String cpfCnpj;
	private String name;
	private String phoneNumber;
	private long numQueries;
	private double priceMean;
	
	
	/*
	 * construtor da classe
	 * Objetivo: instanciar um objeto
	 * Parâmetros: nenhum
	 */
	public ClientDto() {
		this.chatId = null;
		this.cpfCnpj = null;
		this.name = null;
		this.phoneNumber = null;
		this.numQueries = 0;
		this.priceMean = 0.0;
	}
	
	/*
	 * sobrecarga do construtor
	 * Parâmetros: Long chatId, String cpfCnpj, String name, String phoneNumber, long numQueries, double priceMean
	 */
	public ClientDto(Long chatId, String cpfCnpj, String name,
			String phoneNumber, long numQueries, double priceMean) {
		this.chatId = chatId;
		this.cpfCnpj = cpfCnpj;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.priceMean = priceMean;
	}
	
	/*
	 * getChatId
	 * Objetivo: retornar id do objeto relacionado
	 * Retorno: long
	 * Parâmetros: nenhum
	 */
	public Long getChatId() {
		return chatId;
	}
	
	/*
	 * setChatId
	 * Objetivo: setar chatId do objeto relacionado
	 * Retorno: nenhum
	 * Parâmetros: Long chatId
	 */
	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}
	
	/*
	 * getCpfCnpj
	 * Objetivo: retornar cpfCnpj do objeto relacionado
	 * Retorno: String
	 * Parâmetros: nenhum
	 */
	public String getCpfCnpj() {
		return cpfCnpj;
	}
	
	/*
	 * setCpfCnpj
	 * Objetivo: setar o cpfCnpj do objeto relacionado
	 * Retorno: nenhum
	 * Parâmetros: String cpfCnpj
	 */
	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}
	
	/*
	 * getName
	 * Objetivo: retornar name do objeto relacionado
	 * Retorno: String
	 * Parâmetros: nenhum
	 */
	public String getName() {
		return name;
	}
	
	/*
	 * setName
	 * Objetivo: setar id do objeto relacionado
	 * Retorno: nenhum
	 * Parâmetros: String name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/*
	 * getPhoneNumber
	 * Objetivo: retornar id do objeto relacionado
	 * Retorno: String
	 * Parâmetros: nenhum
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	/*
	 * setPhoneNumber
	 * Objetivo: setar phoneNumber do objeto relacionado
	 * Retorno: nenhum
	 * Parâmetros: String phoneNumber
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	/*
	 * getNumQueries
	 * Objetivo: retornar numQueries do objeto relacionado
	 * Retorno: long
	 * Parâmetros: nenhum
	 */
	public long getNumQueries() {
		return numQueries;
	}
	
	/*
	 * setNumQueries
	 * Objetivo: setar numQueries do objeto relacionado
	 * Retorno: nenhum
	 * Parâmetros: long numQueries
	 */
	public void setNumQueries(long numQueries) {
		this.numQueries = numQueries;
	}
	
	/*
	 * getPriceMean
	 * Objetivo: retornar priceMean do objeto relacionado
	 * Retorno: double
	 * Parâmetros: nenhum
	 */
	public double getPriceMean() {
		return priceMean;
	}
	
	/*
	 * setPriceMean
	 * Objetivo: setar priceMean do objeto relacionado
	 * Retorno: nenhum
	 * Parâmetros: double priceMean
	 */
	public void setPriceMean(double priceMean) {
		this.priceMean = priceMean;
	}
	
	
}
