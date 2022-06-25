package br.com.unisc.project.dtos;

public class ClientDto {
	private Long chatId;
	private String cpfCnpj;
	private String name;
	private String phoneNumber;
	public ClientDto() {
		this.chatId = null;
		this.cpfCnpj = null;
		this.name = null;
		this.phoneNumber = null;
	}
	public ClientDto(Long chatId, String cpfCnpj, String name, String phoneNumber) {
		this.chatId = chatId;
		this.cpfCnpj = cpfCnpj;
		this.name = name;
		this.phoneNumber = phoneNumber;
	}
	public Long getChatId() {
		return chatId;
	}
	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}
	public String getCpfCnpj() {
		return cpfCnpj;
	}
	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
}
