package br.com.unisc.project.view;

//Importações
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;

import br.com.unisc.project.controller.ProductViewController;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.GroupLayout;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;

//Classe da janela de gestão de categorias
public class ProductView extends JFrame {
	private static final long serialVersionUID = 1L;

	// Componentes necessários
	private JPanel panelMain;
	private JTabbedPane tabbedPaneMain;
	private JPanel panelAddProduct;
	private JLabel labelDescriptionAdd;
	private JTextField textFieldDescriptionAdd;
	private JLabel labelProductCategoryAdd;
	private JComboBox comboBoxProductCategoryAdd;
	private JLabel labelProductPriceAdd;
	private JTextField textFieldPriceAdd;
	private JLabel labelProductInfoAdd;
	private JTextField textFieldProductInfoAdd;
	private JLabel labelProductImageAdd;
	private JButton buttonProductImageAdd;
	private JButton buttonConfirmAdd;
	private JButton buttonCancelAdd;
	private JPanel panelEditProduct;
	private JLabel labelDescriptionEdit;
	private JComboBox comboBoxDescriptionEdit;
	private JLabel labelProductCategoryEdit;
	private JComboBox comboBoxProductCategoryEdit;
	private JLabel labelNewDescriptionEdit;
	private JTextField textFieldNewDescriptionEdit;
	private JLabel labelNewProductCategoryEdit;
	private JComboBox comboBoxNewProductCategoryEdit;
	private JLabel labelProductPriceEdit;
	private JTextField textFieldPriceEdit;
	private JLabel labelProductInfoEdit;
	private JTextField textFieldProductInfoEdit;
	private JLabel labelProductImageEdit;
	private JButton buttonProductImageEdit;
	private JButton buttonConfirmEdit;
	private JButton buttonCancelEdit;
	private JPanel panelDeleteProduct;
	private JLabel labelProductCategoryDelete;
	private JButton buttonConfirmDelete;
	private JButton buttonCancelDelete;
	private JComboBox comboBoxProductCategoryDelete;
	private JLabel labelProductDelete;
	private JComboBox comboBoxProductDelete;
	private ProductViewController productViewController;
	private byte[] bs;

	// Construtor
	public ProductView(String name) {
		super(name);
		initComponents();
	}

	// Função que inicializa os componentes
	private void initComponents() {
		// Destrói janela ao fechar
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Criação dos componentes
		panelMain = new JPanel();
		tabbedPaneMain = new JTabbedPane(JTabbedPane.TOP);
		panelAddProduct = new JPanel();
		labelDescriptionAdd = new JLabel("Descrição:", JLabel.TRAILING);
		textFieldDescriptionAdd = new JTextField();
		labelProductCategoryAdd = new JLabel("Categoria:", JLabel.TRAILING);
		comboBoxProductCategoryAdd = new JComboBox();
		labelProductPriceAdd = new JLabel("Preço:", JLabel.TRAILING);
		textFieldPriceAdd = new JFormattedTextField();
		labelProductInfoAdd = new JLabel("<html><p style=\"text-align:right\">" + "Informação<br>Técnica:</p><html>",
				JLabel.TRAILING);
		textFieldProductInfoAdd = new JTextField();
		labelProductImageAdd = new JLabel("Foto:", JLabel.TRAILING);
		buttonProductImageAdd = new JButton("Selecionar foto");
		buttonConfirmAdd = new JButton("Confirmar");
		buttonCancelAdd = new JButton("Cancelar");
		panelEditProduct = new JPanel();
		labelDescriptionEdit = new JLabel("Produto:", JLabel.TRAILING);
		comboBoxDescriptionEdit = new JComboBox();
		labelProductCategoryEdit = new JLabel("Categoria:", JLabel.TRAILING);
		comboBoxProductCategoryEdit = new JComboBox();
		labelNewDescriptionEdit = new JLabel("Descrição:", JLabel.TRAILING);
		textFieldNewDescriptionEdit = new JTextField();
		labelNewProductCategoryEdit = new JLabel(
				"<html><p style=\"text-align:right\">" + "Nova<br>Categoria:</p><html>", JLabel.TRAILING);
		comboBoxNewProductCategoryEdit = new JComboBox();
		labelProductPriceEdit = new JLabel("Preço:", JLabel.TRAILING);
		textFieldPriceEdit = new JFormattedTextField();
		labelProductInfoEdit = new JLabel("<html><p style=\"text-align:right\">" + "Informação<br>Técnica:</p><html>",
				JLabel.TRAILING);
		textFieldProductInfoEdit = new JTextField();
		labelProductImageEdit = new JLabel("Foto:", JLabel.TRAILING);
		buttonProductImageEdit = new JButton("Selecionar foto");
		buttonConfirmEdit = new JButton("Confirmar");
		buttonCancelEdit = new JButton("Cancelar");
		panelDeleteProduct = new JPanel();
		labelProductCategoryDelete = new JLabel("Categoria:", JLabel.TRAILING);
		buttonConfirmDelete = new JButton("Confirmar");
		buttonCancelDelete = new JButton("Cancelar");
		comboBoxProductCategoryDelete = new JComboBox();
		labelProductDelete = new JLabel("Produto:", JLabel.TRAILING);
		comboBoxProductDelete = new JComboBox();
		productViewController = new ProductViewController();
		bs = null;
		setContentPane(panelMain); // Define o painel da janela

		// Coloca borda no tabbedPane
		tabbedPaneMain
				.setBorder(new TitledBorder(null, "Produtos", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		// Atribui os paineis ao tabbedPane
		tabbedPaneMain.addTab("Adicionar", null, panelAddProduct, null);
		tabbedPaneMain.addTab("Editar", null, panelEditProduct, null);
		tabbedPaneMain.addTab("Excluir", null, panelDeleteProduct, null);

		// Atribui teclas de atalho
		tabbedPaneMain.setMnemonicAt(0, KeyEvent.VK_A);
		tabbedPaneMain.setMnemonicAt(1, KeyEvent.VK_E);
		tabbedPaneMain.setMnemonicAt(2, KeyEvent.VK_X);
		buttonConfirmAdd.setMnemonic(KeyEvent.VK_O);
		buttonCancelAdd.setMnemonic(KeyEvent.VK_C);
		buttonConfirmEdit.setMnemonic(KeyEvent.VK_O);
		buttonCancelEdit.setMnemonic(KeyEvent.VK_C);
		buttonConfirmDelete.setMnemonic(KeyEvent.VK_O);
		buttonCancelDelete.setMnemonic(KeyEvent.VK_C);
		buttonProductImageAdd.setMnemonic(KeyEvent.VK_F);

		// Atribui textos de ajuda aos componentes pertinentes
		tabbedPaneMain.setToolTipTextAt(0, "Adicionar produtos");
		tabbedPaneMain.setToolTipTextAt(1, "Editar produtos");
		tabbedPaneMain.setToolTipTextAt(2, "Excluir produtos");
		textFieldDescriptionAdd.setToolTipText("Nome do novo produto.");
		comboBoxProductCategoryAdd.setToolTipText("Categoria do novo produto.");
		textFieldPriceAdd.setToolTipText("Preço do novo produto.");
		textFieldProductInfoAdd.setToolTipText("Informações técnicas do novo produto.");
		buttonProductImageAdd.setToolTipText("Buscar foto para o novo produto.");
		comboBoxDescriptionEdit.setToolTipText("Nome do produto a editar.");
		comboBoxProductCategoryEdit.setToolTipText("Categoria do produto a editar.");
		textFieldNewDescriptionEdit.setToolTipText("Nova descrição do produto.");
		comboBoxNewProductCategoryEdit.setToolTipText("Nova categoria do produto.");
		textFieldPriceEdit.setToolTipText("Novo preço do produto.");
		textFieldProductInfoEdit.setToolTipText("Nova informação técnica do produto.");
		buttonProductImageEdit.setToolTipText("Nova foto do produto.");
		comboBoxProductCategoryDelete.setToolTipText("Categoria do produto a ser excluído.");
		comboBoxProductDelete.setToolTipText("Nome do produto a ser excluído.");
		buttonConfirmAdd.setToolTipText("Confirmar criação de categoria.");
		buttonCancelAdd.setToolTipText("Cancelar criação de categoria.");
		buttonConfirmEdit.setToolTipText("Confirmar edição de categoria.");
		buttonCancelEdit.setToolTipText("Cancelar edição de categoria.");
		buttonConfirmDelete.setToolTipText("Confirmar exclusão de categoria.");
		buttonCancelDelete.setToolTipText("Cancelar exclusão de categoria.");

		// Configura layout do painel principal
		GroupLayout layout = new GroupLayout(panelMain);
		panelMain.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addComponent(tabbedPaneMain)));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addComponent(tabbedPaneMain)));

		// Configura layout do painel adicionar
		GroupLayout layoutAdd = new GroupLayout(panelAddProduct);
		panelAddProduct.setLayout(layoutAdd);

		layoutAdd
				.setHorizontalGroup(layoutAdd.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addGroup(layoutAdd.createSequentialGroup().addContainerGap()
								.addComponent(labelDescriptionAdd, 0, 0, 80).addGap(10)
								.addComponent(textFieldDescriptionAdd).addContainerGap())
						.addGroup(layoutAdd.createSequentialGroup().addContainerGap()
								.addComponent(labelProductCategoryAdd, 0, 0, 80).addGap(10)
								.addComponent(comboBoxProductCategoryAdd).addContainerGap())
						.addGroup(layoutAdd.createSequentialGroup().addContainerGap()
								.addComponent(labelProductPriceAdd, 0, 0, 80).addGap(10).addComponent(textFieldPriceAdd)
								.addContainerGap())
						.addGroup(layoutAdd.createSequentialGroup().addContainerGap()
								.addComponent(labelProductInfoAdd, 0, 0, 80).addGap(10)
								.addComponent(textFieldProductInfoAdd).addContainerGap())
						.addGroup(layoutAdd.createSequentialGroup().addContainerGap()
								.addComponent(labelProductImageAdd, 0, 0, 80).addGap(10)
								.addComponent(buttonProductImageAdd).addContainerGap())
						.addGroup(layoutAdd.createSequentialGroup().addContainerGap().addComponent(buttonConfirmAdd)
								.addGap(10).addComponent(buttonCancelAdd).addContainerGap()));

		layoutAdd.setVerticalGroup(layoutAdd.createSequentialGroup()
				.addGroup(layoutAdd.createParallelGroup()
						.addGroup(layoutAdd.createSequentialGroup().addContainerGap()
								.addComponent(labelDescriptionAdd, 0, 0, 32).addGap(20)
								.addComponent(labelProductCategoryAdd, 0, 0, 32).addGap(20)
								.addComponent(labelProductPriceAdd, 0, 0, 32).addGap(20)
								.addComponent(labelProductInfoAdd, 0, 0, 32).addGap(20)
								.addComponent(labelProductImageAdd, 0, 0, 32))
						.addGroup(layoutAdd.createSequentialGroup().addContainerGap()
								.addComponent(textFieldDescriptionAdd, 0, 0, 32).addGap(20)
								.addComponent(comboBoxProductCategoryAdd, 0, 0, 32).addGap(20)
								.addComponent(textFieldPriceAdd, 0, 0, 32).addGap(20)
								.addComponent(textFieldProductInfoAdd, 0, 0, 32).addGap(20)
								.addComponent(buttonProductImageAdd, 0, 0, 32).addGap(10)))
				.addGroup(layoutAdd.createParallelGroup().addComponent(buttonConfirmAdd, 0, 0, 32)
						.addComponent(buttonCancelAdd, 0, 0, 32)));

		// Configura layout do painel editar
		GroupLayout layoutEdit = new GroupLayout(panelEditProduct);
		panelEditProduct.setLayout(layoutEdit);

		layoutEdit.setHorizontalGroup(layoutEdit.createParallelGroup(GroupLayout.Alignment.TRAILING)
				.addGroup(layoutEdit.createSequentialGroup().addContainerGap()
						.addComponent(labelProductCategoryEdit, 0, 0, 80).addGap(10)
						.addComponent(comboBoxProductCategoryEdit).addContainerGap())
				.addGroup(layoutEdit.createSequentialGroup().addContainerGap()
						.addComponent(labelDescriptionEdit, 0, 0, 80).addGap(10).addComponent(comboBoxDescriptionEdit)
						.addContainerGap())
				.addGroup(layoutEdit.createSequentialGroup().addContainerGap()
						.addComponent(labelNewDescriptionEdit, 0, 0, 80).addGap(10)
						.addComponent(textFieldNewDescriptionEdit).addContainerGap())
				.addGroup(layoutEdit.createSequentialGroup().addContainerGap()
						.addComponent(labelNewProductCategoryEdit, 0, 0, 80).addGap(10)
						.addComponent(comboBoxNewProductCategoryEdit).addContainerGap())
				.addGroup(layoutEdit.createSequentialGroup().addContainerGap()
						.addComponent(labelProductPriceEdit, 0, 0, 80).addGap(10).addComponent(textFieldPriceEdit)
						.addContainerGap())
				.addGroup(layoutEdit.createSequentialGroup().addContainerGap()
						.addComponent(labelProductInfoEdit, 0, 0, 80).addGap(10).addComponent(textFieldProductInfoEdit)
						.addContainerGap())
				.addGroup(layoutEdit.createSequentialGroup().addContainerGap()
						.addComponent(labelProductImageEdit, 0, 0, 80).addGap(10).addComponent(buttonProductImageEdit)
						.addContainerGap())
				.addGroup(layoutEdit.createSequentialGroup().addContainerGap().addComponent(buttonConfirmEdit)
						.addGap(10).addComponent(buttonCancelEdit).addContainerGap()));
		layoutEdit.setVerticalGroup(layoutEdit.createSequentialGroup()
				.addGroup(layoutEdit.createParallelGroup()
						.addGroup(layoutEdit.createSequentialGroup().addContainerGap()
								.addComponent(labelProductCategoryEdit, 0, 0, 32).addGap(20)
								.addComponent(labelDescriptionEdit, 0, 0, 32).addGap(20)
								.addComponent(labelNewDescriptionEdit, 0, 0, 32).addGap(20)
								.addComponent(labelNewProductCategoryEdit, 0, 0, 32).addGap(20)
								.addComponent(labelProductPriceEdit, 0, 0, 32).addGap(20)
								.addComponent(labelProductInfoEdit, 0, 0, 32).addGap(20)
								.addComponent(labelProductImageEdit, 0, 0, 32))
						.addGroup(layoutEdit.createSequentialGroup().addContainerGap()
								.addComponent(comboBoxProductCategoryEdit, 0, 0, 32).addGap(20)
								.addComponent(comboBoxDescriptionEdit, 0, 0, 32).addGap(20)
								.addComponent(textFieldNewDescriptionEdit, 0, 0, 32).addGap(20)
								.addComponent(comboBoxNewProductCategoryEdit, 0, 0, 32).addGap(20)
								.addComponent(textFieldPriceEdit, 0, 0, 32).addGap(20)
								.addComponent(textFieldProductInfoEdit, 0, 0, 32).addGap(20)
								.addComponent(buttonProductImageEdit, 0, 0, 32).addGap(10)))
				.addGroup(layoutEdit.createParallelGroup().addComponent(buttonConfirmEdit, 0, 0, 32)
						.addComponent(buttonCancelEdit, 0, 0, 32)));

		// Configura layout do painel excluir
		GroupLayout layoutDelete = new GroupLayout(panelDeleteProduct);
		panelDeleteProduct.setLayout(layoutDelete);

		layoutDelete.setHorizontalGroup(layoutDelete.createParallelGroup(GroupLayout.Alignment.TRAILING)
				.addGroup(layoutDelete.createSequentialGroup().addContainerGap()
						.addComponent(labelProductCategoryDelete, 0, 0, 80).addGap(10)
						.addComponent(comboBoxProductCategoryDelete).addContainerGap())
				.addGroup(layoutDelete.createSequentialGroup().addContainerGap()
						.addComponent(labelProductDelete, 0, 0, 80).addGap(10).addComponent(comboBoxProductDelete)
						.addContainerGap())
				.addGroup(layoutDelete.createSequentialGroup().addComponent(buttonConfirmDelete).addGap(10)
						.addComponent(buttonCancelDelete).addContainerGap()));
		layoutDelete.setVerticalGroup(layoutDelete.createSequentialGroup()
				.addGroup(layoutDelete.createParallelGroup()
						.addGroup(layoutDelete.createSequentialGroup().addContainerGap()
								.addComponent(labelProductCategoryDelete, 0, 0, 32).addGap(20)
								.addComponent(labelProductDelete, 0, 0, 32))
						.addGroup(layoutDelete.createSequentialGroup().addContainerGap()
								.addComponent(comboBoxProductCategoryDelete, 0, 0, 32).addGap(20)
								.addComponent(comboBoxProductDelete, 0, 0, 32).addGap(10)))
				.addGroup(layoutDelete.createParallelGroup().addComponent(buttonConfirmDelete, 0, 0, 32)
						.addComponent(buttonCancelDelete, 0, 0, 32)));

		// Define tamanho mínimo da janela
		this.setMinimumSize(new Dimension(550, 500));

		// Posiciona a janela no centro
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle rScreen = ge.getMaximumWindowBounds();
		Rectangle rFrame = this.getBounds();
		this.setBounds(rScreen.width / 2 - rFrame.width / 2, rScreen.height / 2 - rFrame.height / 2, 400, 280);

		// Torna a janela visível
		this.setVisible(true);

		// seta valores nas comboBox
		productViewController.setData(comboBoxProductCategoryAdd, comboBoxNewProductCategoryEdit,
				comboBoxProductCategoryEdit, comboBoxProductCategoryDelete);
		// funções dos botões

		buttonConfirmEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (bs == null) {
					buttonProductImageEdit.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							bs = productViewController.getPhotoByte(ProductView.this);
						}
					});
				}
				productViewController.editProduct(bs, comboBoxDescriptionEdit, textFieldNewDescriptionEdit,
						comboBoxNewProductCategoryEdit, textFieldPriceEdit, textFieldProductInfoEdit);
			}
		});

		buttonProductImageAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bs = productViewController.getPhotoByte(ProductView.this);
			}
		});

		comboBoxProductCategoryEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				productViewController.setComboBoxNewProductCategoryEdit(comboBoxProductCategoryEdit,
						comboBoxDescriptionEdit);
			}
		});

		buttonConfirmAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (bs == null) {
					buttonProductImageAdd.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							bs = productViewController.getPhotoByte(ProductView.this);
						}
					});
				}
				productViewController.addProduct(comboBoxProductCategoryAdd, textFieldProductInfoAdd, textFieldPriceAdd,
						textFieldDescriptionAdd, bs);
				productViewController.setData(comboBoxProductCategoryAdd, comboBoxNewProductCategoryEdit,
						comboBoxProductCategoryEdit, comboBoxProductCategoryDelete);
			}
		});

		buttonCancelAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProductView.this.dispose();
			}
		});
	}
}