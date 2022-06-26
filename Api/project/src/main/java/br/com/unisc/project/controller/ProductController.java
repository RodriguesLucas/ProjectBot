package br.com.unisc.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unisc.project.dtos.ProductDto;
import br.com.unisc.project.entities.ProductEntity;
import br.com.unisc.project.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@GetMapping(value = "/category/{id}")
	public List<ProductDto> findProductsByCategoryId(@PathVariable Long id){
		return productService.findProductsByCategoryId(id);
	}

}
