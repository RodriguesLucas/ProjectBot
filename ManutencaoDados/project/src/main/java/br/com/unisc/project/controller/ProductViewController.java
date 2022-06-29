package br.com.unisc.project.controller;

import java.io.File;
import java.math.BigDecimal;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.unisc.project.dtos.CategoryDto;
import br.com.unisc.project.dtos.ProductDto;
import br.com.unisc.project.service.ProductViewService;
import br.com.unisc.project.view.ProductView;

public class ProductViewController {
	private ProductViewService productViewService = new ProductViewService();

	public void setData(JComboBox<CategoryDto> comboBoxProductCategoryAdd,
			JComboBox<CategoryDto> comboBoxNewProductCategoryEdit, JComboBox<CategoryDto> comboBoxProductCategoryEdit,
			JComboBox<CategoryDto> comboBoxProductCategoryDelete, JComboBox<ProductDto> comboBoxDescriptionEdit,
			JComboBox<ProductDto> comboBoxProductDelete) {
		productViewService.insertComboBoxProductAddAndEdit(comboBoxProductCategoryAdd);
		productViewService.insertComboBoxProductAddAndEdit(comboBoxNewProductCategoryEdit);
		productViewService.insertComboBoxProductCategoryEdit(comboBoxProductCategoryEdit);
		productViewService.insertComboBoxProductCategoryDel(comboBoxProductCategoryDelete);
	}

	public void updateComboBoxProductCategoryEdit(JComboBox<CategoryDto> comboBoxProductCategoryEdit) {
		productViewService.insertComboBoxProductCategoryEdit(comboBoxProductCategoryEdit);
	}

	public void setComboBoxDelete(JComboBox<CategoryDto> comboBoxProductCategoryDelete,
			JComboBox<ProductDto> comboBoxProductDelete) {
		productViewService.setComboBoxDeleteUpdate(comboBoxProductCategoryDelete, comboBoxProductDelete);
	}

	public byte[] getPhotoByte(ProductView productView) {
		JFileChooser chooser = new JFileChooser();
		chooser.setName("Imagem");
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		chooser.showDialog(productView, "Concluído");
		File file = chooser.getSelectedFile();
		return productViewService.getPhotoByte(file);
	}

	public void addProduct(JComboBox<CategoryDto> comboBoxProductCategoryAdd, JTextField textFieldProductInfoAdd,
			JTextField textFieldPriceAdd, JTextField textFieldDescriptionAdd, byte[] bs, ProductView productView) {
		if (comboBoxProductCategoryAdd.getSelectedItem() != null) {
			ResponseEntity<ProductDto> responseEntity = null;
			try {
				BigDecimal.valueOf(Double.valueOf(textFieldPriceAdd.getText()));
				responseEntity = productViewService.addProduct(
						(CategoryDto) comboBoxProductCategoryAdd.getSelectedItem(), textFieldProductInfoAdd,
						textFieldPriceAdd, textFieldDescriptionAdd, bs);
			} catch (Exception e) {
				textFieldPriceAdd.setText("");
				JOptionPane.showMessageDialog(productView, "Insira um valor válido no preço!", "Atenção!",
						JOptionPane.INFORMATION_MESSAGE);
			}

			if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
				JOptionPane.showMessageDialog(productView, "Algum campo de texto está vazio!", "Atenção!",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(productView, "Produto Criado!", "Sucesso!",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(productView, "Não há categorias no banco!", "Atenção!",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void setComboBoxNewProductCategoryEdit(JComboBox<CategoryDto> comboBoxProductCategoryEdit,
			JComboBox<ProductDto> comboBoxNewProductCategoryEdit) {
		productViewService.setComboBoxNewProductCategory(comboBoxProductCategoryEdit, comboBoxNewProductCategoryEdit);
	}

	public void editProduct(byte[] bs, JComboBox<ProductDto> comboBoxDescriptionEdit,
			JTextField textFieldNewDescriptionEdit, JComboBox<CategoryDto> comboBoxNewProductCategoryEdit,
			JTextField textFieldPriceEdit, JTextField textFieldProductInfoEdit,
			JComboBox<CategoryDto> comboBoxProductCategoryEdit, ProductView productView) {
		if (comboBoxProductCategoryEdit.getSelectedItem() != null) {

			ResponseEntity<ProductDto> responseEntity = null;
			try {
				BigDecimal.valueOf(Double.valueOf(textFieldPriceEdit.getText()));
				responseEntity = productViewService.editProduct(bs,
						(ProductDto) comboBoxDescriptionEdit.getSelectedItem(), textFieldNewDescriptionEdit,
						(CategoryDto) comboBoxNewProductCategoryEdit.getSelectedItem(), textFieldPriceEdit,
						textFieldProductInfoEdit);
			} catch (Exception e) {
				textFieldPriceEdit.setText("");
				JOptionPane.showMessageDialog(productView, "Insira um valor válido no preço!", "Atenção!",
						JOptionPane.INFORMATION_MESSAGE);
			}
			if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
				JOptionPane.showMessageDialog(productView, "Algum campo de texto está vazio!", "Atenção!",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(productView, "Produto Editado!", "Sucesso!",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(productView, "Não há categorias no banco!", "Atenção!",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void delete(JComboBox<ProductDto> comboBoxProductDelete) {
		productViewService.delete((ProductDto) comboBoxProductDelete.getSelectedItem());
	}

}