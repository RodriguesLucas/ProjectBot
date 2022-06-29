/* 
 *  Classe que trata informações do objeto produto
 * Autores @nicolasfischer @brunobolzan @lucasrodrigues 
 */


package br.com.unisc.project.dtos;

import java.math.BigDecimal;

public class ProductDto {
	// atributos
	private Long id;
	private String description;
	private BigDecimal price;
	private String infoTec;
	private byte[] photo;
	private Long categoryId;
	
	/*
	 * construtor da classe
	 * Objetivo: instanciar um objeto
	 * Parâmetros: nenhum
	 */
	public ProductDto() {
	}

	/*
	 * sobrecarga do construtor
	 * Parâmetros: Long id, String description, String infoTec, byte[] photo, BigDecimal price, Long categoryId
	 */
	public ProductDto(Long id, String description, String infoTec, byte[] photo, BigDecimal price, Long categoryId) {
		this.id = id;
		this.description = description;
		this.price = price;
		this.infoTec = infoTec;
		this.photo = photo;
		this.categoryId = categoryId;
	}

	/*
	 * getId
	 * Objetivo: retornar id do objeto relacionado
	 * Retorno: long
	 * Parâmetros: nenhum
	 */
	public Long getId() {
		return id;
	}

	/*
	 * setId
	 * Objetivo: setar id do objeto relacionado
	 * Retorno: nenhum
	 * Parâmetros: Long chatId
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * getCategoryEntity
	 * Objetivo: retornar categoryId do objeto relacionado
	 * Retorno: long
	 * Parâmetros: nenhum
	 */
	public Long getCategoryEntity() {
		return categoryId;
	}

	/*
	 * setCategoryEntity
	 * Objetivo: setar categoryEntity do objeto relacionado
	 * Retorno: nenhum
	 * Parâmetros: Long categoryEntity
	 */
	public void setCategoryEntity(Long categoryEntity) {
		this.categoryId = categoryEntity;
	}

	/*
	 * getDescription
	 * Objetivo: retornar description do objeto relacionado
	 * Retorno: String
	 * Parâmetros: nenhum
	 */
	public String getDescription() {
		return description;
	}

	/*
	 * setDescription
	 * Objetivo: setar description do objeto relacionado
	 * Retorno: nenhum
	 * Parâmetros: String description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * getPrice
	 * Objetivo: retornar price do objeto relacionado
	 * Retorno: BigDecimal
	 * Parâmetros: nenhum
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/*
	 * setPrice
	 * Objetivo: setar price do objeto relacionado
	 * Retorno: nenhum
	 * Parâmetros: BigDecimal price
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/*
	 * getInfoTec
	 * Objetivo: retornar infoTec do objeto relacionado
	 * Retorno: String
	 * Parâmetros: nenhum
	 */
	public String getInfoTec() {
		return infoTec;
	}

	/*
	 * setInfoTec
	 * Objetivo: setar infoTec do objeto relacionado
	 * Retorno: nenhum
	 * Parâmetros: String infoTec
	 */
	public void setInfoTec(String infoTec) {
		this.infoTec = infoTec;
	}

	/*
	 * getPhoto
	 * Objetivo: retornar photo do objeto relacionado
	 * Retorno: byte[]
	 * Parâmetros: nenhum
	 */
	public byte[] getPhoto() {
		return photo;
	}

	/*
	 * setPhoto
	 * Objetivo: setar photo do objeto relacionado
	 * Retorno: nenhum
	 * Parâmetros: byte[] photo
	 */
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	/*
	 * reescrita do método toString
	 * Objetivo: retornar description do objeto relacionado
	 * Retorno: String
	 * Parâmetros: nenhum
	 */
	@Override
	public String toString() {
		return description;
	}

}
