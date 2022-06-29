package br.com.unisc.project.controller;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.unisc.project.dtos.CategoryDto;
import br.com.unisc.project.service.CategoryViewService;
import br.com.unisc.project.view.CategoryView;

public class CategoryViewController {
	private CategoryViewService categoryViewService = new CategoryViewService();

	public void createdNewCategory(JTextField textFieldDescriptionAdd, JComboBox<CategoryDto> comboBoxCategoryParentAdd,
			CategoryView categoryView) {
		ResponseEntity<CategoryDto> entity = categoryViewService.createdNewCategory(textFieldDescriptionAdd,
				(CategoryDto) comboBoxCategoryParentAdd.getSelectedItem());
		if (entity.getStatusCode() == HttpStatus.NO_CONTENT) {
			JOptionPane.showMessageDialog(categoryView, "Algum campo de texto está vazio!", "Atenção!",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(categoryView, "Categoria criada!", "Sucesso!",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void putCategory(JComboBox<CategoryDto> comboBoxCategoryParentEdit,
			JComboBox<CategoryDto> comboBoxCategoryEdit, JTextField textFieldNewDescriptionEdit,
			CategoryView categoryView) {
		if (comboBoxCategoryEdit.getSelectedItem() != null) {
			ResponseEntity<CategoryDto> entity = categoryViewService.putCategory(
					(CategoryDto) comboBoxCategoryParentEdit.getSelectedItem(),
					(CategoryDto) comboBoxCategoryEdit.getSelectedItem(), textFieldNewDescriptionEdit.getText());
			if (entity.getStatusCode() == HttpStatus.NO_CONTENT) {
				JOptionPane.showMessageDialog(categoryView, "Algum campo de texto está vazio!", "Atenção!",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(categoryView, "Categoria Editada!", "Sucesso!",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(categoryView, "Não há categorias no banco!", "Atenção!",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void delete(JComboBox<CategoryDto> comboBoxCategoryDelete, CategoryView categoryView) {
		if (comboBoxCategoryDelete.getSelectedItem() != null) {
			ResponseEntity<CategoryDto> responseEntity = categoryViewService
					.delete((CategoryDto) comboBoxCategoryDelete.getSelectedItem());
			if (responseEntity.getStatusCode() == HttpStatus.OK) {
				JOptionPane.showMessageDialog(categoryView, "Categoria Excluída!", "Sucesso!",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(categoryView, "Não há categorias no banco!", "Atenção!",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void setData(JComboBox<CategoryDto> comboBoxCategoryParentAdd, JComboBox<CategoryDto> comboBoxCategoryEdit,
			JComboBox<CategoryDto> comboBoxCategoryDelete, JComboBox<CategoryDto> comboBoxCategoryParentEdit) {
		categoryViewService.setComboBoxCategoryParentAdd(comboBoxCategoryParentAdd);
		categoryViewService.setComboBoxPut(comboBoxCategoryEdit);
		categoryViewService.setComboBoxCategoryParentEdit(comboBoxCategoryParentEdit);
		categoryViewService.setComboBoxCategoryDelete(comboBoxCategoryDelete);
	}
}
