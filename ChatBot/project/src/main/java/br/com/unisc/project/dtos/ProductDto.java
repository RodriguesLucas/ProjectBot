package br.com.unisc.project.dtos;

import java.math.BigDecimal;

public class ProductDto {
	private Long id;
	private Long categoryId;
	private String description;
	private BigDecimal price;
	private String infoTec;
	private byte[] photo;

	public ProductDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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
