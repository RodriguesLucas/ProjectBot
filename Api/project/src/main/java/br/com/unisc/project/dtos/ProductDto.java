package br.com.unisc.project.dtos;

import java.math.BigDecimal;

import br.com.unisc.project.entities.ProductEntity;

public class ProductDto {
	private Long id;

	private String description;

	private BigDecimal price;

	private String infoTec;

	private byte[] photo;
	private Long categoryId;

	public ProductDto() {
	}

	public ProductDto(Long id, String description, String infoTec, byte[] photo, BigDecimal price, Long categoryId) {
		this.id = id;
		this.description = description;
		this.price = price;
		this.infoTec = infoTec;
		this.photo = photo;
		this.categoryId = categoryId;
	}

	public ProductDto(ProductEntity entity) {
		this.setId(entity.getId());
		this.setDescription(entity.getDescription());
		this.setPrice(entity.getPrice());
		this.setInfoTec(entity.getInfoTec());
		this.setPhoto(entity.getPhoto());
		this.setCategoryEntity(entity.getCategoryId().getId());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCategoryEntity() {
		return categoryId;
	}

	public void setCategoryEntity(Long categoryEntity) {
		this.categoryId = categoryEntity;
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
