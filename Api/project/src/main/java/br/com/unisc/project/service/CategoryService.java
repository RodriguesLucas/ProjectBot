package br.com.unisc.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unisc.project.dtos.CategoryDto;
import br.com.unisc.project.entities.CategoryEntity;
import br.com.unisc.project.entities.ProductEntity;
import br.com.unisc.project.repositories.CategoryRepository;
import br.com.unisc.project.repositories.ProductRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductRepository productRepository;

	// Nome não pode ser igual e verificar se a categoria não é a mesma que querem
	// inserir
	@Transactional
	public List<CategoryDto> findCategory() {
		return null;
	}

	public List<CategoryDto> findChildrenById(Long id){
		return categoryRepository.findAllByParentId(id);
	}
	
	public CategoryDto findByNameCategory(String name) {
		Optional<CategoryEntity> findByDescription = categoryRepository.findByDescription(name);
		return new CategoryDto(findByDescription.get());
	}
	
	public CategoryDto findCategoryById(Long id) {
		Optional<CategoryEntity> findById = categoryRepository.findById(id);
		return new CategoryDto(findById.get());
	}

	// Se tiver produto cadastrado com esse id não pode
	// SE ela for pai também não pode
	@Transactional
	public List<CategoryDto> findCategoryParent() {
		List<CategoryEntity> categorEntityOptional = categoryRepository.findAll();
		List<ProductEntity> productOptional = productRepository.findAll();
		List<CategoryDto> list = new ArrayList<CategoryDto>();
		for (CategoryEntity categoryEntity : categorEntityOptional) {
			for (ProductEntity productEntity : productOptional) {
				if (categoryEntity.getId() != categoryEntity.getCategoryParent().getId()) {
					if (categoryEntity.getId() != productEntity.getCategoryId().getId()) {
						CategoryDto categoryDto = new CategoryDto();
						categoryDto.setId(categoryEntity.getId());
						categoryDto.setCategoryParentId(categoryEntity.getCategoryParent().getId());
						categoryDto.setDescription(categoryEntity.getDescription());
					}
				}
			}
		}

		return list;
	}

	@Transactional
	public List<CategoryDto> findAll() {
		List<CategoryEntity> categoryEntities = categoryRepository.findAll();
		return categoryEntities.stream().map(CategoryDto::new).collect(Collectors.toList());
	}

	@Transactional
	public ResponseEntity<CategoryDto> add(CategoryDto categoryDto) {
		CategoryEntity entity = new CategoryEntity();
		if (categoryDto.getCategoryParentId() == null) {
			Optional<CategoryEntity> findDescriptionOptional = categoryRepository
					.findByDescription(categoryDto.getDescription());
			if (!findDescriptionOptional.isPresent()) {
				entity.setDescription(categoryDto.getDescription());
				entity.setCategoryParent(null);
				return ResponseEntity.ok().body(new CategoryDto(categoryRepository.save(entity)));
			}
			throw new RuntimeException("Descrição já contém no banco de dados!");
		} else {
			Optional<CategoryEntity> optionalCategoryOptional = categoryRepository
					.findById(categoryDto.getCategoryParentId());
			if (optionalCategoryOptional.isPresent()) {
				Optional<CategoryEntity> findDescriptionOptional = categoryRepository
						.findByDescription(categoryDto.getDescription());
				if (!findDescriptionOptional.isPresent()) {
					Optional<ProductEntity> productOptional = productRepository
							.findByCategoryEntityId(categoryDto.getCategoryParentId());
					if (!productOptional.isPresent()) {
						entity.setDescription(categoryDto.getDescription());
						entity.setCategoryParent(optionalCategoryOptional.get());
						return ResponseEntity.ok().body(new CategoryDto(categoryRepository.save(entity)));
					}
					throw new RuntimeException("Produto ligado a essa categoria!");
				}
				throw new RuntimeException("Descrição já contém no banco de dados!");
			}
			throw new RuntimeException("Categoria pai não existe!");
		}
	}

	@Transactional
	public CategoryDto put(CategoryDto categoryDto, Long id) {
		Optional<CategoryEntity> categoryParentOptional = categoryRepository
				.findById(categoryDto.getCategoryParentId());
		if (categoryParentOptional.isPresent()) {
			Optional<ProductEntity> productCategoryOptional = productRepository.findByCategoryEntityId(id);
			if (!productCategoryOptional.isPresent()) {
				Optional<CategoryEntity> findDescriptionOptional = categoryRepository
						.findByDescription(categoryDto.getDescription());
				if (!findDescriptionOptional.isPresent()) {
					CategoryEntity categoryEntity = new CategoryEntity();
					categoryEntity.setId(id);
					categoryEntity.setDescription(categoryDto.getDescription());
					categoryEntity.setCategoryParent(categoryParentOptional.get());
					return new CategoryDto(categoryRepository.save(categoryEntity));
				}
				throw new RuntimeException("Nome já existe no banco!");
			}
			throw new RuntimeException("Categoria ligado a um produto!");
		}
		throw new RuntimeException("Essa categoria está vinculada a filhos!");
	}

	public CategoryDto delete(Long id) {
		Optional<ProductEntity> productCategoryOptional = productRepository.findByCategoryEntityId(id);
		if (!productCategoryOptional.isPresent()) {
			Optional<CategoryEntity> category = categoryRepository.findById(id);
			if (!category.isPresent()) {
				categoryRepository.deleteById(id);
				return new CategoryDto(category.get());
			}
			throw new RuntimeException("Essa categoria está vinculada a filhos!");
		}
		throw new RuntimeException("Categoria ligado a um produto!");
	}

}
