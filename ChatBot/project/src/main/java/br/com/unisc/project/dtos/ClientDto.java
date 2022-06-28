/* 
 *  Classe que trata cliente - regra de negócio
 * Autores @nicolasfischer @brunobolzan @lucasrodrigues 
 */

package br.com.unisc.project.dtos;

public class ClientDto {
	// atributos base da categoria declarados
	private Long chatId;
	private String cpfCnpj;
	private String name;
	private String phoneNumber;
	
	//construtor com inicialização nula de objeto
	public ClientDto() {
		this.chatId = null;
		this.cpfCnpj = null;
		this.name = null;
		this.phoneNumber = null;
	}
	
	/*
	 * sobrecarga de método construtor - ClientDto
	 * Objetivo: Método que instaciará um objeto oriundo da classe a partir do recebimento de argumentos padronizados
	 * Retorno: void
	 * Parâmetros: Long chatId, String cpfCnpj, String name, String phoneNumber
	 */
	public ClientDto(Long chatId, String cpfCnpj, String name, String phoneNumber) {
		this.chatId = chatId;
		this.cpfCnpj = cpfCnpj;
		this.name = name;
		this.phoneNumber = phoneNumber;
	}
	
	/*
	 * getChatId
	 * Objetivo: retornar o chatId do objeto relacionado
	 * Retorno: long
	 * Parâmetros: nenhum
	 */
	public Long getChatId() {
		return chatId;
	}
	
	/*
	 * setChatId
	 * Objetivo: setar o chatId do objeto relacionado
	 * Retorno: void
	 * Parâmetros: Long chatId
	 */
	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}
	
	/*
	 * getCpfCnpj
	 * Objetivo: retornar o cpfCnpj do objeto relacionado
	 * Retorno: String
	 * Parâmetros: nenhum
	 */
	public String getCpfCnpj() {
		return cpfCnpj;
	}
	
	/*
	 * setCpfCnpj
	 * Objetivo: setar o cpfCnpj do objeto relacionado
	 * Retorno: void
	 * Parâmetros: String cpfCnpj
	 */
	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}
	
	/*
	 * getName
	 * Objetivo: retornar o name do objeto relacionado
	 * Retorno: String
	 * Parâmetros: nenhum
	 */
	public String getName() {
		return name;
	}
	
	/*
	 * setCpfCnpj
	 * Objetivo: setar o name do objeto relacionado
	 * Retorno: String
	 * Parâmetros: String name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	/*
	 * getPhoneNumber
	 * Objetivo: retornar o phoneNumber do objeto relacionado
	 * Retorno: String
	 * Parâmetros: nenhum
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	/*
	 * setCpfCnpj
	 * Objetivo: setar o name do objeto relacionado
	 * Retorno: String
	 * Parâmetros: String name
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
}
