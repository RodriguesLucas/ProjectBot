package br.com.unisc.project.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import br.com.unisc.project.dtos.CategoryDto;
import br.com.unisc.project.dtos.ProductDto;

public class ProductViewService {
	private static final String URlBaseCategory = "http://localhost:8080/category";
	private static final String URlBase = "http://localhost:8080/product";

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

	public CategoryDto[] findCategoryAdd() {
		String uri = URlBaseCategory.concat("/product");
		RestTemplate restTemplate = new RestTemplate();
		CategoryDto[] categoryDto = restTemplate.getForObject(uri, CategoryDto[].class);
		return categoryDto;
	}

	public void insertComboBox(JComboBox comboBoxProductCategoryAdd) {
		CategoryDto[] categoryDtos = findCategoryAdd();
		comboBoxProductCategoryAdd.removeAllItems();
		for (CategoryDto categoryDto : categoryDtos) {
			comboBoxProductCategoryAdd.addItem(categoryDto.getDescription());
		}
	}

	public CategoryDto findCategoryByName(String name) {
		String uri = URlBaseCategory.concat("/").concat(name);
		RestTemplate restTemplate = new RestTemplate();
		CategoryDto categoryDto = restTemplate.getForObject(uri, CategoryDto.class);
		return categoryDto;
	}

	public ProductDto addProduct(JComboBox comboBoxProductCategoryAdd, JTextField textFieldProductInfoAdd,
			JTextField textFieldPriceAdd, JTextField textFieldDescriptionAdd, byte[] bs) {

		if (textFieldDescriptionAdd.getText() == null
				|| textFieldDescriptionAdd.getText().trim().isEmpty() && textFieldPriceAdd != null
				|| textFieldPriceAdd.getText().trim().isEmpty() && textFieldProductInfoAdd != null
				|| textFieldProductInfoAdd.getText().trim().isEmpty()) {
			throw new RuntimeException("Texto vazio");
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		URI uri = null;
		try {
			uri = new URI(URlBase);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		CategoryDto categoryDto = findCategoryByName(comboBoxProductCategoryAdd.getSelectedItem().toString());
		ProductDto dto = new ProductDto();
		dto.setCategoryEntity(categoryDto.getId());
		dto.setDescription(textFieldDescriptionAdd.getText());
		dto.setInfoTec(textFieldProductInfoAdd.getText());
		dto.setPhoto(bs);
		dto.setPrice(BigDecimal.valueOf(Double.valueOf(textFieldPriceAdd.getText().toString())));

		HttpEntity<ProductDto> requestEntity = new HttpEntity<ProductDto>(dto, headers);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.postForEntity(uri, requestEntity, ProductDto.class);
		textFieldPriceAdd.setText("");
		textFieldProductInfoAdd.setText("");
		textFieldDescriptionAdd.setText("");
		bs = null;
		return dto;
	}

	public void insertComboBoxProductCategoryEdit(JComboBox comboBoxProductCategoryEdit) {
		CategoryDto[] categoryDtos = findCategoryEdit();
		comboBoxProductCategoryEdit.removeAllItems();
		for (CategoryDto categoryDto : categoryDtos) {
			comboBoxProductCategoryEdit.addItem(categoryDto.getDescription());
		}
	}

	private CategoryDto[] findCategoryEdit() {
		String uri = URlBaseCategory.concat("/produt/edit");
		System.out.println("test");
		RestTemplate restTemplate = new RestTemplate();
		CategoryDto[] categoryDto = restTemplate.getForObject(uri, CategoryDto[].class);
		return categoryDto;
	}

	public void setComboBoxNewProductCategoryEdit(JComboBox comboBoxProductCategoryEdit,
			JComboBox comboBoxNewProductCategoryEdit) {
		CategoryDto categoryDto = findCategoryByName(comboBoxProductCategoryEdit.getSelectedItem().toString());
		ProductDto[] productDtos = findProductByCategoryId(categoryDto.getId());
		comboBoxNewProductCategoryEdit.removeAllItems();
		for (ProductDto productDto : productDtos) {
			comboBoxNewProductCategoryEdit.addItem(productDto.getDescription());
		}
	}

	private ProductDto[] findProductByCategoryId(Long id) {
		String uri = URlBase.concat("/category/".concat(id.toString()));
		RestTemplate restTemplate = new RestTemplate();
		ProductDto[] productDtos = restTemplate.getForObject(uri, ProductDto[].class);
		return productDtos;
	}

	public void editProduct(byte[] bs, JComboBox comboBoxDescriptionEdit, JTextField textFieldNewDescriptionEdit,
			JComboBox comboBoxNewProductCategoryEdit, JTextField textFieldPriceEdit,
			JTextField textFieldProductInfoEdit) {
		ProductDto dto = findProductByName(comboBoxDescriptionEdit.getSelectedItem().toString());

		ProductDto productDto = new ProductDto();
		productDto.setCategoryEntity(
				findCategoryByName(comboBoxNewProductCategoryEdit.getSelectedItem().toString()).getCategoryParentId());
		productDto.setPhoto(bs);
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

	}

	private ProductDto findProductByName(String name) {
		String uri = URlBaseCategory.concat("/name/").concat(name);
		RestTemplate restTemplate = new RestTemplate();
		ProductDto categoryDto = restTemplate.getForObject(uri, ProductDto.class);
		return categoryDto;
	}

}