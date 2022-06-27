package br.com.unisc.project.service;

import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import br.com.unisc.project.dtos.CategoryDto;

public class CategoryViewService {
	private static final String URlBase = "http://localhost:8080/category";

	public void setComboBoxCategoryParentAdd(JComboBox comboBoxCategoryParentAdd) {
		String uri = URlBase.concat("/addAndEdit");
		RestTemplate restTemplate = new RestTemplate();
		CategoryDto[] categoryDtos = restTemplate.getForObject(uri, CategoryDto[].class);
		comboBoxCategoryParentAdd.removeAllItems();
		comboBoxCategoryParentAdd.addItem("Sem categoria");
		for (CategoryDto categoryDto : categoryDtos) {
			comboBoxCategoryParentAdd.addItem(categoryDto.getDescription());
		}
	}

	public void createdNewCategory(JTextField textFieldDescriptionAdd, JComboBox comboBoxCategoryParentAdd) {
		if (textFieldDescriptionAdd.getText() == null || textFieldDescriptionAdd.getText().trim().isEmpty()) {
			throw new RuntimeException("Texto vazio");
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		URI uri = null;
		try {
			uri = new URI(URlBase.concat("/"));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		CategoryDto dto = new CategoryDto();
		if (comboBoxCategoryParentAdd.getSelectedItem() != null
				&& !comboBoxCategoryParentAdd.getSelectedItem().equals("Sem categoria")) {
			CategoryDto categoryDto = findCategoryByName(comboBoxCategoryParentAdd.getSelectedItem().toString());
			dto.setDescription(textFieldDescriptionAdd.getText());
			dto.setCategoryParentId(categoryDto.getId());
		} else {
			dto.setDescription(textFieldDescriptionAdd.getText());
			dto.setCategoryParentId(null);
		}
		HttpEntity<CategoryDto> requestEntity = new HttpEntity<CategoryDto>(dto, headers);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.postForEntity(uri, requestEntity, CategoryDto.class);
		textFieldDescriptionAdd.setText("");
		comboBoxCategoryParentAdd.removeAllItems();
		setComboBoxCategoryParentAdd(comboBoxCategoryParentAdd);
	}

	public CategoryDto findCategoryByName(String name) {
		String uri = URlBase.concat("/").concat(name);
		RestTemplate restTemplate = new RestTemplate();
		CategoryDto categoryDto = restTemplate.getForObject(uri, CategoryDto.class);
		return categoryDto;
	}

	public CategoryDto[] findCategoryParent() {
		String uri = URlBase.concat("/addAndEdit");
		RestTemplate restTemplate = new RestTemplate();
		CategoryDto[] categoryDto = restTemplate.getForObject(uri, CategoryDto[].class);
		return categoryDto;
	}

	public CategoryDto[] findCategoryParentDel() {
		String uri = URlBase.concat("/");
		RestTemplate restTemplate = new RestTemplate();
		CategoryDto[] categoryDto = restTemplate.getForObject(uri, CategoryDto[].class);
		return categoryDto;
	}

	public CategoryDto[] findAllCategory() {
		String uri = URlBase;
		RestTemplate restTemplate = new RestTemplate();
		CategoryDto[] categoryDto = restTemplate.getForObject(uri, CategoryDto[].class);
		return categoryDto;
	}

	public void setComboBoxPut(JComboBox comboBoxCategoryEdit) {
		CategoryDto[] categoryDtos = findCategoryParent();
		comboBoxCategoryEdit.removeAllItems();
		for (CategoryDto categoryDto : categoryDtos) {
			comboBoxCategoryEdit.addItem(categoryDto.getDescription());
		}
	}

	public void setComboBoxCategoryParentEdit(JComboBox comboBoxCategoryParentEdit) {
		CategoryDto[] categoryDtos = findCategoryParent();
		comboBoxCategoryParentEdit.removeAllItems();
		comboBoxCategoryParentEdit.addItem("Sem categoria");
		for (CategoryDto categoryDto : categoryDtos) {
			comboBoxCategoryParentEdit.addItem(categoryDto.getDescription());
		}
	}

	public void putCategory(JComboBox comboBoxCategoryParentEdit, JComboBox comboBoxCategoryEdit,
			JTextField textFieldNewDescriptionEdit) {
		if (comboBoxCategoryParentEdit.getSelectedItem().toString()
				.equals(comboBoxCategoryEdit.getSelectedItem().toString())) {
			CategoryDto categoryDto = new CategoryDto();
			if (!comboBoxCategoryParentEdit.getSelectedItem().toString().equals("Sem categoria")) {
				CategoryDto categoryParentDto = findCategoryByName(
						comboBoxCategoryParentEdit.getSelectedItem().toString());
				categoryDto.setCategoryParentId(categoryParentDto.getId());
			} else {
				categoryDto.setCategoryParentId(null);
			}
			categoryDto.setDescription(textFieldNewDescriptionEdit.getText());

			CategoryDto dto = findCategoryByName(comboBoxCategoryEdit.getSelectedItem().toString());
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			URI uri = null;
			try {
				uri = new URI(URlBase.concat("/") + dto.getId());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.put(uri, categoryDto);
		}
		throw new RuntimeException("Categoria pai não pode ser o mesmo da categoria!");
	}

	public void setComboBoxCategoryDelete(JComboBox comboBoxCategoryDelete) {
		CategoryDto[] categoryDtos = findCategoryParentDel();
		comboBoxCategoryDelete.removeAllItems();
		for (CategoryDto categoryDto : categoryDtos) {
			comboBoxCategoryDelete.addItem(categoryDto.getDescription());
		}
	}

	public void delete(JComboBox comboBoxCategoryDelete) {
		CategoryDto dto = findCategoryByName(comboBoxCategoryDelete.getSelectedItem().toString());
		String uri = URlBase.concat("/" + dto.getId());
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(uri);
	}
}
