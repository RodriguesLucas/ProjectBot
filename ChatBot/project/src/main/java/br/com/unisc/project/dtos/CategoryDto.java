/* 
 *  Classe que trata a categoria - regra de negócio
 * Autores @nicolasfischer @brunobolzan @lucasrodrigues 
 */

package br.com.unisc.project.dtos;

public class CategoryDto {

	// atributos base da categoria declarados
	private Long id;
	private Long categoryParentId;
	private String description;

	//construtor sem especificação de parâmetros para atribuição
	public CategoryDto() {

	}

	/*
	 * sobrecarga de método construtor - CategoryDto
	 * Objetivo: Método que instaciará um objeto oriundo da classe a partir do recebimento de argumentos padronizados
	 * Retorno: void
	 * Parâmetros: String description, Long categoryParentId, Long id
	 */
	public CategoryDto(String description, Long categoryParentId, Long id) {
		this.id = id;
		this.description = description;
		this.categoryParentId = categoryParentId;
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
	 * getDescriptio
	 * Objetivo: retornar o description do objeto relacionado
	 * Retorno: long
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
	 * getCategoryParentId
	 * Objetivo: retornar o categoryParentId do objeto relacionado
	 * Retorno: long
	 * Parâmetros: nenhum
	 */
	public Long getCategoryParentId() {
		return categoryParentId;
	}

	/*
	 * setCategoryParentId
	 * Objetivo: setar o categoryParentId do objeto relacionado
	 * Retorno: void
	 * Parâmetros: Long categoryParentId
	 */
	public void setCategoryParentId(Long categoryParentId) {
		this.categoryParentId = categoryParentId;
	}

}
