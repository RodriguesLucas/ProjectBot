package br.com.unisc.project.dtos;

import br.com.unisc.project.entities.ClientEntity;

public class ClientDto {

	private String cpf_cnpj;

	private String name;

	private String phone;

	public ClientDto() {

	}

	public ClientDto(ClientEntity clientEntity) {
		this.cpf_cnpj = clientEntity.getCpf_cnpj();
		this.name = clientEntity.getName();
		this.phone = clientEntity.getPhone();
	}

	public ClientDto(String cpf_cnpj, String name, String phone) {
		this.cpf_cnpj = cpf_cnpj;
		this.name = name;
		this.phone = phone;
	}

	public String getCpf_cnpj() {
		return cpf_cnpj;
	}

	public void setCpf_cnpj(String cpf_cnpj) {
		this.cpf_cnpj = cpf_cnpj;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
