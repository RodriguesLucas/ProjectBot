/* 
 *  Classe que trata cliente - regra de negócio
 * Autores @nicolasfischer @brunobolzan @lucasrodrigues 
 */

package br.com.unisc.project.dtos;

import java.math.BigDecimal;

public class ProductDto {
	// atributos base da categoria declarados
	private Long id;
	private Long categoryId;
	private String description;
	private BigDecimal price;
	private String infoTec;
	private byte[] photo;

	// método construtor
	public ProductDto() {
	}

	/*
	 * getId
	 * Objetivo: retornar o id do objeto relacionado
	 * Retorno: long
	 * Parâmetros: nenhum
	 */
	public Long getId() {
		return id;
	}

	/*
	 * setId
	 * Objetivo: setar o id do objeto relacionado
	 * Retorno: void
	 * Parâmetros: Long id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * getCategoryId
	 * Objetivo: retornar o categoryId do objeto relacionado
	 * Retorno: long
	 * Parâmetros: nenhum
	 */
	public Long getCategoryId() {
		return categoryId;
	}

	/*
	 * setCategoryId
	 * Objetivo: setar o categoryId do objeto relacionado
	 * Retorno: void
	 * Parâmetros: Long categoryId
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	/*
	 * getDescription
	 * Objetivo: retornar o description do objeto relacionado
	 * Retorno: String
	 * Parâmetros: nenhum
	 */
	public String getDescription() {
		return description;
	}

	/*
	 * setDescription
	 * Objetivo: setar o description do objeto relacionado
	 * Retorno: void
	 * Parâmetros: String description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * getPrice
	 * Objetivo: retornar o price do objeto relacionado
	 * Retorno: BigDecimal
	 * Parâmetros: nenhum
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/*
	 * setPrice
	 * Objetivo: setar o price do objeto relacionado
	 * Retorno: void
	 * Parâmetros: BigDecimal price
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/*
	 * getInfoTec
	 * Objetivo: retornar o infoTec do objeto relacionado
	 * Retorno: String
	 * Parâmetros: nenhum
	 */
	public String getInfoTec() {
		return infoTec;
	}

	/*
	 * setInfoTec
	 * Objetivo: setar o infoTec do objeto relacionado
	 * Retorno: void
	 * Parâmetros: String infoTec
	 */
	public void setInfoTec(String infoTec) {
		this.infoTec = infoTec;
	}

	/*
	 * getId
	 * Objetivo: retornar o id do objeto relacionado
	 * Retorno: long
	 * Parâmetros: nenhum
	 */
	public byte[] getPhoto() {
		return photo;
	}

	/*
	 * setPhoto
	 * Objetivo: setar o photo do objeto relacionado
	 * Retorno: void
	 * Parâmetros: byte[] photo
	 */
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

}
