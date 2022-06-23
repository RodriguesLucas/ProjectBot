package br.com.unisc.project.dtos;


public class CategoryDto {

	private Long id;
	private Long categoryParentId;
	private String description;

	public CategoryDto() {

	}
	public CategoryDto(String description, Long categoryParentId, Long id) {
		this.id = id;
		this.description = description;
		this.categoryParentId = categoryParentId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCategoryParentId() {
		return categoryParentId;
	}
	
}
