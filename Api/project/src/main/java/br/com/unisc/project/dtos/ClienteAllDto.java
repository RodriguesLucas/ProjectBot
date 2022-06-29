package br.com.unisc.project.dtos;

public class ClienteAllDto {
	private Long id;
	private String cpfCnpj;
	private String name;
	private String phoneNumber;
	private long numQueries;
	private double priceMean;

	public ClienteAllDto(Long id, String cpfCnpj, String name, String phoneNumber, long numQueries, double priceMean) {
		this.id = id;
		this.cpfCnpj = cpfCnpj;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.numQueries = numQueries;
		this.priceMean = priceMean;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
