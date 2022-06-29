package br.com.unisc.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unisc.project.dtos.ProductDto;
import br.com.unisc.project.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;

	@GetMapping(value = "/category/{id}")
	public List<ProductDto> findProductsByCategoryId(@PathVariable Long id) {
		return productService.findProductsByCategoryId(id);
	}
	
	@GetMapping("/name{name}")
	public ProductDto findByProductById(@PathVariable String name) {
		return productService.findByProductByName(name);
	}
	
	@GetMapping("/{id}")
	public ProductDto findByProductById(@PathVariable Long id) {
		return productService.findByProductById(id);
	}

	@PostMapping
	public ProductDto insertProduct(@RequestBody ProductDto productDto) {
		return productService.insertProduct(productDto);
	}

	@PutMapping(value = "/{id}")
	public ProductDto editProduct(@RequestBody ProductDto productDto, @PathVariable Long id) {
		return productService.editProdut(productDto, id);
	}

	@DeleteMapping(value = "/{id}")
	public ProductDto deleteProduct(@PathVariable Long id) {
		return productService.delete(id);
	}
}
