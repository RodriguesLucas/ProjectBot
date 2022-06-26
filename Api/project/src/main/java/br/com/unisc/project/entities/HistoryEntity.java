package br.com.unisc.project.entities;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "history")
public class HistoryEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JoinColumn(name = "cliente_id")
	@ManyToOne
	private ClientEntity clientId;
	
	@JoinColumn(name = "product_id")
	@ManyToOne
	private ProductEntity productId;
	
	private Instant instant;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ClientEntity getClientId() {
		return clientId;
	}

	public void setClientId(ClientEntity clientId) {
		this.clientId = clientId;
	}

	public ProductEntity getProductId() {
		return productId;
	}

	public void setProductId(ProductEntity productId) {
		this.productId = productId;
	}

	public Instant getInstant() {
		return instant;
	}

	public void setInstant(Instant instant) {
		this.instant = instant;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
