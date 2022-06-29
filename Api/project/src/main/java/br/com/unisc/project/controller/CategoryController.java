package br.com.unisc.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import br.com.unisc.project.dtos.CategoryDto;
import br.com.unisc.project.entities.CategoryEntity;
import br.com.unisc.project.service.CategoryService;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/categoryDel")
	public List<CategoryDto> findAllCategoryDel(){
		return categoryService.findAllCategoryDel();
	}
	
	@GetMapping("/product")
	public List<CategoryDto> findAllCategoriesForProductAdd(){
		return categoryService.findAllCategoriesForProductAdd();
	}
	
	@GetMapping("/produt/edit")
	public List<CategoryDto> findAllCategoriesForProductEdit(){
		return categoryService.findAllCategoriesForProductEdit();
	}

	@GetMapping(value = "/{name}")
	public CategoryDto findByName(@PathVariable String name) {
		return categoryService.findByNameCategory(name);
	}
	
	@GetMapping(value = "/id/{id}")
	public CategoryDto findById(@PathVariable Long id) {
		return categoryService.findCategoryById(id);
	}
	
	@GetMapping
	public List<CategoryDto> findAll() {
		return categoryService.findAll();
	}
	
	@GetMapping("/")
	public List<CategoryDto> findCategoryParent() {
		return categoryService.findCategoryParent();
	}

	@GetMapping(value = "/children/{id}")
	public List<CategoryDto> findChildrenById(@PathVariable Long id){
		return categoryService.findChildrenById(id);
	}
	
	@GetMapping("/addAndEdit")
	public List<CategoryDto> findCategoryParentAddAndEdit() {
		return categoryService.findCategoryParentAddAndEdit();
	}

	@PostMapping
	public ResponseEntity<CategoryDto> add(@RequestBody CategoryDto categoryDto) {
		return categoryService.add(categoryDto);
	}

	@PutMapping(value = "/{id}")
	public CategoryDto put(@RequestBody CategoryDto categoryDto, @PathVariable Long id) {
		return categoryService.put(categoryDto, id);
	}

	@DeleteMapping(value = "{id}")
	public CategoryDto delete(@PathVariable Long id) {
		return categoryService.delete(id);
	}
}

