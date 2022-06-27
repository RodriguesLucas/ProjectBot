package br.com.unisc.project.controller;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import br.com.unisc.project.service.CategoryViewService;


public class CategoryViewController {
	private CategoryViewService categoryViewService = new CategoryViewService();


	public void createdNewCategory(JTextField textFieldDescriptionAdd, JComboBox comboBoxCategoryParentAdd) {
		categoryViewService.createdNewCategory(textFieldDescriptionAdd, comboBoxCategoryParentAdd);

	}

	public void putCategory(JComboBox comboBoxCategoryParentEdit, JComboBox comboBoxCategoryEdit,
			JTextField textFieldNewDescriptionEdit) {
		categoryViewService.putCategory(comboBoxCategoryParentEdit, comboBoxCategoryEdit, textFieldNewDescriptionEdit);
	}


	public void delete(JComboBox comboBoxCategoryDelete) {
		categoryViewService.delete(comboBoxCategoryDelete);
	}

	public void setData(JComboBox comboBoxCategoryParentAdd, JComboBox comboBoxCategoryEdit,
			JComboBox comboBoxCategoryDelete, JComboBox comboBoxCategoryParentEdit) {
		categoryViewService.setComboBoxCategoryParentAdd(comboBoxCategoryParentAdd);
		categoryViewService.setComboBoxPut(comboBoxCategoryEdit);
		categoryViewService.setComboBoxCategoryParentEdit(comboBoxCategoryParentEdit);
		categoryViewService.setComboBoxCategoryDelete(comboBoxCategoryDelete);
	}
}
