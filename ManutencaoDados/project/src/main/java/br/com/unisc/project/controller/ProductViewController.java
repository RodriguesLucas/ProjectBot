package br.com.unisc.project.controller;

import java.io.File;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import br.com.unisc.project.dtos.ProductDto;
import br.com.unisc.project.service.ProductViewService;
import br.com.unisc.project.view.ProductView;

public class ProductViewController {
	private ProductViewService productViewService = new ProductViewService();

	public void setData(JComboBox comboBoxProductCategoryAdd, JComboBox comboBoxNewProductCategoryEdit,
			JComboBox comboBoxProductCategoryEdit, JComboBox comboBoxProductCategoryDelete) {
		productViewService.insertComboBox(comboBoxProductCategoryAdd);
	}

	public byte[] getPhotoByte(ProductView productView) {
		JFileChooser chooser = new JFileChooser();
		chooser.setName("Teste");
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		chooser.showDialog(productView, "teste");
		File file = chooser.getSelectedFile();
		return productViewService.getPhotoByte(file);
	}

	public ProductDto addProduct(JComboBox comboBoxProductCategoryAdd, JTextField textFieldProductInfoAdd,
			JTextField textFieldPriceAdd, JTextField textFieldDescriptionAdd, byte[] bs) {
		return productViewService.addProduct(comboBoxProductCategoryAdd, textFieldProductInfoAdd, textFieldPriceAdd,
				textFieldDescriptionAdd, bs);

	}

}