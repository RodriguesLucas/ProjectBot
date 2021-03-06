/* 
 *  Classe que trata a sessão instanciada pelo usuário ao começar o processo de categoria
 * Autores @nicolasfischer @brunobolzan @lucasrodrigues 
 */

package br.com.unisc.project.dtos;

public class CategoryDto {

	// atributos
	private Long id;
	private Long categoryParentId;
	private String description;

	
	/*
	 * construtor da classe
	 * Objetivo: instanciar um objeto
	 * Parâmetros: nenhum
	 */
	public CategoryDto() {

	}

	
	/*
	 * sobrecarga do construtor da classe
	 * Objetivo: instanciar um objeto
	 * Parâmetros: String description, Long categoryParentId, Long id
	 */
	public CategoryDto(String description, Long categoryParentId, Long id) {
		this.id = id;
		this.description = description;
		this.categoryParentId = categoryParentId;
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
	 * Objetivo: setar o id do objeto relacionado
	 * Retorno: void
	 * Parâmetros: Long id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * getCategoryParentId
	 * Objetivo: retornar categoryParentId do objeto relacionado
	 * Retorno: long
	 * Parâmetros: nenhum
	 */
	public String getDescription() {
		return description;
	}

	/*
	 * setDescription
	 * Objetivo: setar description do objeto relacionado
	 * Retorno: void
	 * Parâmetros: String description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * getCategoryParentId
	 * Objetivo: retornar categoryParentId do objeto relacionado
	 * Retorno: long
	 * Parâmetros: nenhum
	 */
	public Long getCategoryParentId() {
		return categoryParentId;
	}

	/*
	 * setCategoryParentId
	 * Objetivo: setar categoryParentId do objeto relacionado
	 * Retorno: void
	 * Parâmetros: Long categoryParentId
	 */
	public void setCategoryParentId(Long categoryParentId) {
		this.categoryParentId = categoryParentId;
	}

	/*
	 * reescrita do método toString
	 * Retorno: nenhum
	 * Parâmetros: nenhum
	 */
	@Override
	public String toString() {
		return description;
	}

	
}
