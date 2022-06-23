package br.com.unisc.project.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "client")
public class ClientEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String cpf_cnpj;

	private String name;

	private String phone;
}
