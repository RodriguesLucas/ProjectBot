package br.com.unisc.project.controller.viewController;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import br.com.unisc.project.service.viewService.CategoryViewService;

public class CategoryViewController {
	private CategoryViewService categoryViewService = new CategoryViewService();

	public void setComboBoxCategoryParentAdd(JComboBox comboBoxCategoryParentAdd) {
		categoryViewService.setComboBoxCategoryParentAdd(comboBoxCategoryParentAdd);
	}

	public void setComboBoxPut(JComboBox comboBoxCategoryEdit) {
		categoryViewService.setComboBoxPut(comboBoxCategoryEdit);
	}

	public void createdNewCategory(JTextField textFieldDescriptionAdd, JComboBox comboBoxCategoryParentAdd) {
		categoryViewService.createdNewCategory(textFieldDescriptionAdd, comboBoxCategoryParentAdd);

	}

	public void setComboBoxCategoryParentEdit(JComboBox comboBoxCategoryParentEdit) {
		categoryViewService.setComboBoxCategoryParentEdit(comboBoxCategoryParentEdit);
	}

	public void putCategory(JComboBox comboBoxCategoryParentEdit, JComboBox comboBoxCategoryEdit,
			JTextField textFieldNewDescriptionEdit) {
		categoryViewService.putCategory(comboBoxCategoryParentEdit, comboBoxCategoryEdit, textFieldNewDescriptionEdit);
		
	}

	public void setComboBoxCategoryDelete(JComboBox comboBoxCategoryDelete) {
		categoryViewService.setComboBoxCategoryDelete(comboBoxCategoryDelete);
		
	}

}
