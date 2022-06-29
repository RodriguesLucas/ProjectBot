package br.com.unisc.project.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.unisc.project.dtos.CategoryDto;

public class CategoryViewService {
	private static final String URlBase = "http://localhost:8080/category";

	// Cria uma Cateria sem pai
	private void createNACategory(JComboBox<CategoryDto> comboBoxCategoryParentAdd) {
		CategoryDto dto = new CategoryDto();
		dto.setId(0l);
		dto.setDescription("N/A");
		comboBoxCategoryParentAdd.removeAllItems();
		comboBoxCategoryParentAdd.addItem(dto);
	}

	// Bucas na api da categoria
	public CategoryDto[] findAllCategoryParentAdd() {
		String uri = URlBase.concat("/addAndEdit");
		RestTemplate restTemplate = new RestTemplate();
		CategoryDto[] categoryDtos = restTemplate.getForObject(uri, CategoryDto[].class);
		return categoryDtos;
	}
	
	public CategoryDto[] findCategoryParentDel() {
		String uri = URlBase.concat("/");
		RestTemplate restTemplate = new RestTemplate();
		CategoryDto[] categoryDto = restTemplate.getForObject(uri, CategoryDto[].class);
		return categoryDto;
	}

	public CategoryDto[] findCategoryParent() {
		String uri = URlBase.concat("/addAndEdit");
		RestTemplate restTemplate = new RestTemplate();
		CategoryDto[] categoryDto = restTemplate.getForObject(uri, CategoryDto[].class);
		return categoryDto;
	}

	public CategoryDto findCategoryById(Long id) {
		String uri = URlBase.concat("/id/").concat(id.toString());
		RestTemplate restTemplate = new RestTemplate();
		CategoryDto categoryDto = restTemplate.getForObject(uri, CategoryDto.class);
		return categoryDto;
	}

	// Seta os valores nas comboBox
	public void setComboBoxCategoryParentAdd(JComboBox<CategoryDto> comboBoxCategoryParentAdd) {
		createNACategory(comboBoxCategoryParentAdd);
		CategoryDto[] categoryDtos = findAllCategoryParentAdd();
		for (CategoryDto categoryDto : categoryDtos) {
			comboBoxCategoryParentAdd.addItem(categoryDto);
		}
	}

	public void setComboBoxPut(JComboBox<CategoryDto> comboBoxCategoryEdit) {
		CategoryDto[] categoryDtos = findCategoryParent();
		comboBoxCategoryEdit.removeAllItems();
		for (CategoryDto categoryDto : categoryDtos) {
			comboBoxCategoryEdit.addItem(categoryDto);
		}
	}

	public void setComboBoxCategoryParentEdit(JComboBox<CategoryDto> comboBoxCategoryParentEdit) {
		CategoryDto[] categoryDtos = findCategoryParent();
		createNACategory(comboBoxCategoryParentEdit);
		for (CategoryDto categoryDto : categoryDtos) {
			comboBoxCategoryParentEdit.addItem(categoryDto);
		}
	}

	public void setComboBoxCategoryDelete(JComboBox<CategoryDto> comboBoxCategoryDelete) {
		CategoryDto[] categoryDtos = findCategoryParentDel();
		comboBoxCategoryDelete.removeAllItems();
		for (CategoryDto categoryDto : categoryDtos) {
			comboBoxCategoryDelete.addItem(categoryDto);
		}
	}

	// Adiciona categoria no banco
	public ResponseEntity<CategoryDto> createdNewCategory(JTextField textFieldDescriptionAdd, CategoryDto categoryDto) {
		if (textFieldDescriptionAdd.getText() == null || textFieldDescriptionAdd.getText().trim().isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new CategoryDto());
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

		if (categoryDto.getId() != 0l) {
			CategoryDto findById = findCategoryById(categoryDto.getId());
			dto.setDescription(textFieldDescriptionAdd.getText());
			dto.setCategoryParentId(findById.getId());
		} else {
			dto.setDescription(textFieldDescriptionAdd.getText());
			dto.setCategoryParentId(null);
		}
		HttpEntity<CategoryDto> requestEntity = new HttpEntity<CategoryDto>(dto, headers);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.postForEntity(uri, requestEntity, CategoryDto.class);
		textFieldDescriptionAdd.setText("");
		return ResponseEntity.ok().body(dto);
	} 

	// Edita categoria do banco
	public ResponseEntity<CategoryDto> putCategory(CategoryDto comboBoxCategoryParentEdit,
			CategoryDto comboBoxCategoryEdit, JTextField textFieldNewDescriptionEdit) {
		CategoryDto categoryDto = new CategoryDto();
		if (!textFieldNewDescriptionEdit.getText().trim().isEmpty()) {
			if (comboBoxCategoryParentEdit.getId() != 0l) {
				CategoryDto categoryParentDto = findCategoryById(comboBoxCategoryParentEdit.getId());
				categoryDto.setCategoryParentId(categoryParentDto.getId());
			} else {
				categoryDto.setCategoryParentId(null);
			}
			categoryDto.setDescription(textFieldNewDescriptionEdit.getText());

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			URI uri = null;
			try {
				uri = new URI(URlBase.concat("/") + comboBoxCategoryEdit.getId());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.put(uri, categoryDto);
			textFieldNewDescriptionEdit.setText("");
			return ResponseEntity.ok().body(categoryDto);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new CategoryDto());
	}

	//Deleta categoria do banco
	public ResponseEntity<CategoryDto> delete(CategoryDto categoryDto) {
		CategoryDto dto = findCategoryById(categoryDto.getId());
		String uri = URlBase.concat("/" + dto.getId());
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(uri);
		return ResponseEntity.ok().body(new CategoryDto());
	}
}
