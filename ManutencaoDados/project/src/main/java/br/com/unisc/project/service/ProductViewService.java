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
		return dto;
	}

}
