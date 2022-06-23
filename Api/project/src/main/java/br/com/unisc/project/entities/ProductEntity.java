package br.com.unisc.project.entities;

import java.io.Serializable;
import java.math.BigDecimal;

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
@Table(name = "product")
public class ProductEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JoinColumn(name = "category_id")
	@ManyToOne
	private CategoryEntity categoryEntity;
	
	private String description;
	
	private BigDecimal price;
	
	private String infoTec;
	
	@Column(length = 10485760, columnDefinition = "MediumBlob")
	private byte [] photo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CategoryEntity getCategoryId() {
		return categoryEntity;
	}

	public void setCategoryId(CategoryEntity categoryEntity) {
		this.categoryEntity = categoryEntity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getInfoTec() {
		return infoTec;
	}

	public void setInfoTec(String infoTec) {
		this.infoTec = infoTec;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	
	
}
