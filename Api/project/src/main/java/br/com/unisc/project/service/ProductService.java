package br.com.unisc.project.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unisc.project.dtos.ProductDto;
import br.com.unisc.project.entities.CategoryEntity;
import br.com.unisc.project.entities.ProductEntity;
import br.com.unisc.project.repositories.CategoryRepository;
import br.com.unisc.project.repositories.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional
	public List<ProductDto> findProductsByCategoryId(Long id) {
		List<ProductEntity> products = productRepository.findAllByCategoryId(id);
		return products.stream().map(ProductDto::new).collect(Collectors.toList());
	}
	
	@Transactional
	public ProductDto insertProduct(ProductDto productDto) {
		ProductEntity productEntity = new ProductEntity();
		Optional<CategoryEntity> findById = categoryRepository.findById(productDto.getCategoryEntity());
		productEntity.setCategoryId(findById.get());
		productEntity.setDescription(productDto.getDescription());
		productEntity.setInfoTec(productDto.getInfoTec());
		productEntity.setPhoto(productDto.getPhoto());
		productEntity.setPrice(productDto.getPrice());
		return new ProductDto(productRepository.save(productEntity));
	}

	@Transactional
	public ProductDto editProdut(ProductDto productDto, Long id) {
		Optional<ProductEntity> entityOptional = productRepository.findById(id);
		Optional<CategoryEntity> categoryOptional = categoryRepository.findById(productDto.getCategoryEntity());
		ProductEntity entity = new ProductEntity();
		entity.setCategoryId(categoryOptional.get());
		entity.setDescription(productDto.getDescription());
		entity.setId(entityOptional.get().getId());
		entity.setInfoTec(productDto.getInfoTec());
		entity.setPhoto(productDto.getPhoto());
		entity.setPrice(productDto.getPrice());
		return new ProductDto(productRepository.save(entity));
	}
	
	public ProductDto delete(Long id) {
		Optional<ProductEntity> entityOptional = productRepository.findById(id);
		if (entityOptional.isPresent()) {
			productRepository.deleteById(id);
			return new ProductDto(entityOptional.get());
		}
		return new ProductDto();
		
	}

}
