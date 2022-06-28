package br.com.unisc.project.dtos;

import br.com.unisc.project.entities.ClientAllInfoEntity;

public class ClientAllInfoDto {
	private Long chatId;
	private String cpfCnpj;
	private String name;
	private String phoneNumber;
	private long numQueries;
	private double priceMean;

	public ClientAllInfoDto(ClientAllInfoEntity entity) {
		this.chatId = entity.getId();
		this.cpfCnpj = entity.getCpf_cnpj();
		this.name = entity.getName();
		this.phoneNumber = entity.getPhone();
		this.numQueries = entity.getNumQueries();
		this.priceMean = entity.getPriceMean();
	}
	
	public ClientAllInfoDto() {
	}

	public ClientAllInfoDto(Long chatId, String cpfCnpj, String name, String phoneNumber, long numQueries,
			double priceMean) {
		this.chatId = chatId;
		this.cpfCnpj = cpfCnpj;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.priceMean = priceMean;
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

	public long getNumQueries() {
		return numQueries;
	}

	public void setNumQueries(long numQueries) {
		this.numQueries = numQueries;
	}

	public double getPriceMean() {
		return priceMean;
	}

	public void setPriceMean(double priceMean) {
		this.priceMean = priceMean;
	}
}
