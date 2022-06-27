package br.com.unisc.project.dtos;

import br.com.unisc.project.entities.ClientEntity;

public class ClientDto {

	private Long chatId;
	
	private String cpfCnpj;

	private String name;

	private String phoneNumber;

	public ClientDto() {

	}

	public ClientDto(ClientEntity clientEntity) {
		this.chatId = clientEntity.getId();
		this.cpfCnpj = clientEntity.getCpf_cnpj();
		this.name = clientEntity.getName();
		this.phoneNumber = clientEntity.getPhone();
	}

	public ClientDto(Long chatId, String cpf_cnpj, String name, String phone) {
		this.chatId = chatId;
		this.cpfCnpj = cpf_cnpj;
		this.name = name;
		this.phoneNumber = phone;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpf_cnpj) {
		this.cpfCnpj = cpf_cnpj;
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

	public void setPhoneNumber(String phone) {
		this.phoneNumber = phone;
	}

	public Long getChatId() {
		return chatId;
	}

	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}
}
