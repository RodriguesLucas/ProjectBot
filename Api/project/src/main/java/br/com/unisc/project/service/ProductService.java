package br.com.unisc.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unisc.project.dtos.ProductDto;
import br.com.unisc.project.entities.ProductEntity;
import br.com.unisc.project.repositories.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	
	@Transactional
	public List<ProductDto> findProductsByCategoryId(Long id) {
		List<ProductEntity> products = productRepository.findAllByCategoryId(id);
		List<ProductDto> productsDto = new ArrayList<>();
		for(ProductEntity p : products) {
			ProductDto dto = new ProductDto(
					p.getId(), p.getDescription(),
					p.getInfoTec(), p.getPhoto(),
					p.getPrice(), p.getCategoryId().getId());
			productsDto.add(dto);
		}
		return productsDto;
	}

}
