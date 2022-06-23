package br.com.unisc.project.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class CategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;

	@JoinColumn(name = "category_parent_id")
	@OneToOne
	private CategoryEntity categoryParent;

	public CategoryEntity() {

	}

	public CategoryEntity(Long id, String description, CategoryEntity categoryParent) {
		this.id = id;
		this.description = description;
		this.categoryParent = categoryParent;
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

	public CategoryEntity getCategoryParent() {
		return categoryParent;
	}

	public void setCategoryParent(CategoryEntity categoryParent) {
		this.categoryParent = categoryParent;
	}

}
