package br.com.unisc.project.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "client")
public class ClientAllInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String cpf_cnpj;

	private String name;

	private String phone;

	private long numQueries;

	private double priceMean;
	
	public ClientAllInfoEntity() {

	}

	public ClientAllInfoEntity(String cpf_cnpj, String name, String phone, long numQueries, double priceMean) {
		this.cpf_cnpj = cpf_cnpj;
		this.name = name;
		this.phone = phone;
		this.numQueries = numQueries;
		this.priceMean = priceMean;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
