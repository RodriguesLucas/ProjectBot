package br.com.unisc.project.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.unisc.project.dtos.CategoryDto;
import br.com.unisc.project.dtos.ProductDto;

public class ProductViewService {
	private static final String URlBaseCategory = "http://localhost:8080/category";
	private static final String URlBase = "http://localhost:8080/product";

	// Código para pegar a foto do user
	public byte[] getPhotoByte(File file) {
		if (file != null) {
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				return IOUtils.toByteArray(fileInputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	// Buscar no banco de dados da Categorias
	public CategoryDto[] findCategoryAdd() {
		String uri = URlBaseCategory.concat("/product");
		RestTemplate restTemplate = new RestTemplate();
		CategoryDto[] categoryDto = restTemplate.getForObject(uri, CategoryDto[].class);
		return categoryDto;
	}
	

	private CategoryDto[] findCategoryDel() {
		String uri = URlBaseCategory.concat("/categoryDel");
		RestTemplate restTemplate = new RestTemplate();
		CategoryDto[] categoryDto = restTemplate.getForObject(uri, CategoryDto[].class);
		return categoryDto;
	}

	private CategoryDto[] findCategoryEdit() {
		String uri = URlBaseCategory.concat("/produt/edit");
		RestTemplate restTemplate = new RestTemplate();
		CategoryDto[] categoryDto = restTemplate.getForObject(uri, CategoryDto[].class);
		return categoryDto;
	}

	// Buscar no banco de dados da produtos
	private ProductDto[] findProductByCategoryId(Long id) {
		String uri = URlBase.concat("/category/".concat(id.toString()));
		RestTemplate restTemplate = new RestTemplate();
		ProductDto[] productDtos = restTemplate.getForObject(uri, ProductDto[].class);
		return productDtos;
	}
	
	private ProductDto[] findAllProductyForDel(Long id) {
		String uri = URlBase.concat("/categoryDel/".concat(id.toString()));
		RestTemplate restTemplate = new RestTemplate();
		ProductDto[] productDtos = restTemplate.getForObject(uri, ProductDto[].class);
		return productDtos;
	}

	// Insert nas combox das caterias de opções de produtos
	public void insertComboBoxProductAddAndEdit(JComboBox<CategoryDto> comboBoxProductCategoryAdd) {
		CategoryDto[] categoryDtos = findCategoryAdd();
		comboBoxProductCategoryAdd.removeAllItems();
		for (CategoryDto categoryDto : categoryDtos) {
			comboBoxProductCategoryAdd.addItem(categoryDto);
		}
	}

	public void insertComboBoxProductCategoryEdit(JComboBox<CategoryDto> comboBoxProductCategoryEdit) {
		CategoryDto[] categoryDtos = findCategoryEdit();
		comboBoxProductCategoryEdit.removeAllItems();
		for (CategoryDto categoryDto : categoryDtos) {
			comboBoxProductCategoryEdit.addItem(categoryDto);
		}
	}

	public void setComboBoxNewProductCategoryEdit(JComboBox<CategoryDto> comboBoxProductCategoryEdit,
			JComboBox<ProductDto> comboBoxNewProductCategoryEdit) {
		insertComboBoxProductCategoryEdit(comboBoxProductCategoryEdit);
		CategoryDto dto = (CategoryDto) comboBoxProductCategoryEdit.getSelectedItem();
		ProductDto[] productDtos = findProductByCategoryId(dto.getId());
		comboBoxNewProductCategoryEdit.removeAllItems();
		for (ProductDto productDto : productDtos) {
			comboBoxNewProductCategoryEdit.addItem(productDto);
		}
	}
	
	//Insert na tela de delete
	public void setComboBoxDelete(JComboBox<CategoryDto> comboBoxProductCategoryDelete,
			JComboBox<ProductDto> comboBoxProductDelete) {
		insertComboBoxProductCategoryDel(comboBoxProductCategoryDelete);
		CategoryDto dto = (CategoryDto) comboBoxProductCategoryDelete.getSelectedItem();
		ProductDto[] productDtos = findAllProductyForDel(dto.getId());
		comboBoxProductDelete.removeAllItems();
		for (ProductDto productDto : productDtos) {
			comboBoxProductDelete.addItem(productDto);
		}
	}

	private void insertComboBoxProductCategoryDel(JComboBox<CategoryDto> comboBoxProductCategoryDelete) {
		CategoryDto[] categoryDtos = findCategoryDel();
		comboBoxProductCategoryDelete.removeAllItems();
		for (CategoryDto categoryDto : categoryDtos) {
			comboBoxProductCategoryDelete.addItem(categoryDto);
		}
	}

	public ResponseEntity<ProductDto> addProduct(CategoryDto categoryDto, JTextField textFieldProductInfoAdd,
			JTextField textFieldPriceAdd, JTextField textFieldDescriptionAdd, byte[] bs) {
		if (textFieldProductInfoAdd.getText().trim().isEmpty() || textFieldPriceAdd.getText().trim().isEmpty()
				|| textFieldDescriptionAdd.getText().trim().isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ProductDto());
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		URI uri = null;
		try {
			uri = new URI(URlBase);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		ProductDto dto = new ProductDto();
		dto.setCategoryEntity(categoryDto.getId());
		dto.setDescription(textFieldDescriptionAdd.getText());
		dto.setInfoTec(textFieldProductInfoAdd.getText());
		if (bs == null) {
			dto.setPhoto(null);
		} else {
			dto.setPhoto(bs);
		}

		dto.setPrice(BigDecimal.valueOf(Double.valueOf(textFieldPriceAdd.getText())));

		HttpEntity<ProductDto> requestEntity = new HttpEntity<ProductDto>(dto, headers);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.postForEntity(uri, requestEntity, ProductDto.class);
		textFieldPriceAdd.setText("");
		textFieldProductInfoAdd.setText("");
		textFieldDescriptionAdd.setText("");
		bs = null;
		return ResponseEntity.ok().body(dto);
	}

	public ResponseEntity<ProductDto> editProduct(byte[] bs, ProductDto dto, JTextField textFieldNewDescriptionEdit,
			CategoryDto categoryDto, JTextField textFieldPriceEdit, JTextField textFieldProductInfoEdit) {
		if (textFieldNewDescriptionEdit.getText().trim().isEmpty() || textFieldPriceEdit.getText().trim().isEmpty()
				|| textFieldProductInfoEdit.getText().trim().isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ProductDto());
		}
		ProductDto productDto = new ProductDto();
		productDto.setCategoryEntity(categoryDto.getId());
		if (bs == null) {
			productDto.setPhoto(null);
		} else {
			productDto.setPhoto(bs);
		}
		productDto.setInfoTec(textFieldProductInfoEdit.getText());
		productDto.setPrice(BigDecimal.valueOf(Double.parseDouble(textFieldPriceEdit.getText())));
		productDto.setDescription(textFieldNewDescriptionEdit.getText());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		URI uri = null;
		try {
			uri = new URI(URlBase.concat("/") + dto.getId());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put(uri, productDto);
		textFieldNewDescriptionEdit.setText("");
		textFieldPriceEdit.setText("");
		textFieldProductInfoEdit.setText("");
		bs = null;
		return ResponseEntity.ok().body(productDto);
	}
}