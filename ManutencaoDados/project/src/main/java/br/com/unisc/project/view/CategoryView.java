/* 
 *  Classe que trata da view e faz inicializações das configurações necessárias para a categoria
 * Autores @nicolasfischer @brunobolzan @lucasrodrigues 
 */


package br.com.unisc.project.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;

import br.com.unisc.project.controller.CategoryViewController;
import br.com.unisc.project.dtos.CategoryDto;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CategoryView extends JFrame {
	private static final long serialVersionUID = 1L;

	// Componentes necessários
	private JPanel panelMain;
	private JTabbedPane tabbedPaneMain;
	private JPanel panelAddCategory;
	private JLabel labelDescriptionAdd;
	private JTextField textFieldDescriptionAdd;
	private JLabel labelCategoryParentAdd;
	private JComboBox<CategoryDto> comboBoxCategoryParentAdd;
	private JButton buttonConfirmAdd;
	private JButton buttonCancelAdd;
	private JPanel panelEditCategory;
	private JLabel labelCategoryEdit;
	private JComboBox<CategoryDto> comboBoxCategoryEdit;
	private JLabel labelNewDescriptionEdit;
	private JTextField textFieldNewDescriptionEdit;
	private JLabel labelCategoryParentEdit;
	private JComboBox<CategoryDto> comboBoxCategoryParentEdit;
	private JButton buttonConfirmEdit;
	private JButton buttonCancelEdit;
	private JPanel panelDeleteCategory;
	private JLabel labelCategoryDelete;
	private JButton buttonConfirmDelete;
	private JButton buttonCancelDelete;
	private JComboBox<CategoryDto> comboBoxCategoryDelete;
	private CategoryViewController categoryViewController;

	// Construtor
	public CategoryView(String title) {
		super(title);
		initComponents();
	}

	// Função que inicializa os componentes
	private void initComponents() {
		// Destrói janela ao fechar
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Criação dos componentes
		categoryViewController = new CategoryViewController();
		panelMain = new JPanel();
		tabbedPaneMain = new JTabbedPane(JTabbedPane.TOP);
		panelAddCategory = new JPanel();
		labelDescriptionAdd = new JLabel("Descrição:", JLabel.TRAILING);
		textFieldDescriptionAdd = new JTextField();
		labelCategoryParentAdd = new JLabel("Categoria Pai:", JLabel.TRAILING);
		comboBoxCategoryParentAdd = new JComboBox<CategoryDto>();
		buttonConfirmAdd = new JButton("Confirmar");
		buttonCancelAdd = new JButton("Cancelar");
		panelEditCategory = new JPanel();
		labelCategoryEdit = new JLabel("Categoria:", JLabel.TRAILING);
		comboBoxCategoryEdit = new JComboBox<CategoryDto>();
		labelNewDescriptionEdit = new JLabel("Descrição:", JLabel.TRAILING);
		textFieldNewDescriptionEdit = new JTextField();
		labelCategoryParentEdit = new JLabel("Categoria Pai:", JLabel.TRAILING);
		comboBoxCategoryParentEdit = new JComboBox<CategoryDto>();
		buttonConfirmEdit = new JButton("Confirmar");
		buttonCancelEdit = new JButton("Cancelar");
		panelDeleteCategory = new JPanel();
		labelCategoryDelete = new JLabel("Categoria:", JLabel.TRAILING);
		buttonConfirmDelete = new JButton("Confirmar");
		buttonCancelDelete = new JButton("Cancelar");
		comboBoxCategoryDelete = new JComboBox<CategoryDto>();

		setContentPane(panelMain); // Define o painel da janela

		// Coloca borda no tabbedPane
		tabbedPaneMain
				.setBorder(new TitledBorder(null, "Categorias", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		// Atribui os paineis ao tabbedPane
		tabbedPaneMain.addTab("Adicionar", null, panelAddCategory, null);
		tabbedPaneMain.addTab("Editar", null, panelEditCategory, null);
		tabbedPaneMain.addTab("Excluir", null, panelDeleteCategory, null);

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

		// Atribui textos de ajuda aos componentes pertinentes
		tabbedPaneMain.setToolTipTextAt(0, "Adicionar categorias");
		tabbedPaneMain.setToolTipTextAt(1, "Editar categorias");
		tabbedPaneMain.setToolTipTextAt(2, "Excluir categorias");
		textFieldDescriptionAdd.setToolTipText("Nome da nova categoria.");
		comboBoxCategoryParentAdd.setToolTipText("Pai da nova categoria.");
		comboBoxCategoryEdit.setToolTipText("Categoria a ser editada.");
		textFieldNewDescriptionEdit.setToolTipText("Novo nome da categoria.");
		comboBoxCategoryParentEdit.setToolTipText("Nova categoria pai da categoria.");
		comboBoxCategoryDelete.setToolTipText("Categoria a ser excluída.");
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
		GroupLayout layoutAdd = new GroupLayout(panelAddCategory);
		panelAddCategory.setLayout(layoutAdd);

		layoutAdd
				.setHorizontalGroup(layoutAdd.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addGroup(layoutAdd.createSequentialGroup().addContainerGap()
								.addComponent(labelDescriptionAdd, 0, 0, 100).addGap(10)
								.addComponent(textFieldDescriptionAdd).addContainerGap())
						.addGroup(layoutAdd.createSequentialGroup().addContainerGap()
								.addComponent(labelCategoryParentAdd, 0, 0, 100).addGap(10)
								.addComponent(comboBoxCategoryParentAdd).addContainerGap())
						.addGroup(layoutAdd.createSequentialGroup().addComponent(buttonConfirmAdd).addGap(10)
								.addComponent(buttonCancelAdd).addContainerGap()));
		layoutAdd.setVerticalGroup(layoutAdd.createSequentialGroup()
				.addGroup(layoutAdd.createParallelGroup()
						.addGroup(layoutAdd.createSequentialGroup().addContainerGap()
								.addComponent(labelDescriptionAdd, 0, 0, 32).addGap(20)
								.addComponent(labelCategoryParentAdd, 0, 0, 32))
						.addGroup(layoutAdd.createSequentialGroup().addContainerGap()
								.addComponent(textFieldDescriptionAdd, 0, 0, 32).addGap(20)
								.addComponent(comboBoxCategoryParentAdd, 0, 0, 32).addGap(10)))
				.addGroup(layoutAdd.createParallelGroup().addComponent(buttonConfirmAdd, 0, 0, 32)
						.addComponent(buttonCancelAdd, 0, 0, 32)));

		// Configura layout do painel editar
		GroupLayout layoutEdit = new GroupLayout(panelEditCategory);
		panelEditCategory.setLayout(layoutEdit);

		layoutEdit
				.setHorizontalGroup(layoutEdit.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addGroup(layoutEdit.createSequentialGroup().addContainerGap()
								.addComponent(labelCategoryEdit, 0, 0, 100).addGap(10)
								.addComponent(comboBoxCategoryEdit).addContainerGap())
						.addGroup(layoutEdit.createSequentialGroup().addContainerGap()
								.addComponent(labelNewDescriptionEdit, 0, 0, 100).addGap(10)
								.addComponent(textFieldNewDescriptionEdit).addContainerGap())
						.addGroup(layoutEdit.createSequentialGroup().addContainerGap()
								.addComponent(labelCategoryParentEdit, 0, 0, 100).addGap(10)
								.addComponent(comboBoxCategoryParentEdit).addContainerGap())
						.addGroup(layoutEdit.createSequentialGroup().addComponent(buttonConfirmEdit).addGap(10)
								.addComponent(buttonCancelEdit).addContainerGap()));
		layoutEdit.setVerticalGroup(layoutEdit.createSequentialGroup().addGroup(layoutEdit
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layoutEdit.createSequentialGroup().addContainerGap().addComponent(labelCategoryEdit, 0, 0, 32)
						.addGap(20).addComponent(labelNewDescriptionEdit, 0, 0, 32).addGap(20)
						.addComponent(labelCategoryParentEdit, 0, 0, 32))
				.addGroup(layoutEdit.createSequentialGroup().addContainerGap()
						.addComponent(comboBoxCategoryEdit, 0, 0, 32).addGap(20)
						.addComponent(textFieldNewDescriptionEdit, 0, 0, 32).addGap(20)
						.addComponent(comboBoxCategoryParentEdit, 0, 0, 32).addGap(10)))
				.addGroup(layoutEdit.createParallelGroup().addComponent(buttonConfirmEdit, 0, 0, 32)
						.addComponent(buttonCancelEdit, 0, 0, 32)));

		// Configura layout do painel excluir
		GroupLayout layoutDelete = new GroupLayout(panelDeleteCategory);
		panelDeleteCategory.setLayout(layoutDelete);

		layoutDelete.setHorizontalGroup(layoutDelete.createParallelGroup(GroupLayout.Alignment.TRAILING)
				.addGroup(layoutDelete.createSequentialGroup().addContainerGap()
						.addComponent(labelCategoryDelete, 0, 0, 80).addGap(10).addComponent(comboBoxCategoryDelete)
						.addContainerGap())
				.addGroup(layoutDelete.createSequentialGroup().addComponent(buttonConfirmDelete).addGap(10)
						.addComponent(buttonCancelDelete).addContainerGap()));
		layoutDelete.setVerticalGroup(layoutDelete.createSequentialGroup()
				.addGroup(layoutDelete.createParallelGroup()
						.addGroup(layoutDelete.createSequentialGroup().addContainerGap()
								.addComponent(labelCategoryDelete, 0, 0, 32))
						.addGroup(layoutDelete.createSequentialGroup().addContainerGap()
								.addComponent(comboBoxCategoryDelete, 0, 0, 32).addGap(10)))
				.addGroup(layoutDelete.createParallelGroup().addComponent(buttonConfirmDelete, 0, 0, 32)
						.addComponent(buttonCancelDelete, 0, 0, 32)));

		// Define tamanho mínimo da janela
		this.setMinimumSize(new Dimension(400, 280));

		// Posiciona a janela no centro
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle rScreen = ge.getMaximumWindowBounds();
		Rectangle rFrame = this.getBounds();
		this.setBounds(rScreen.width / 2 - rFrame.width / 2, rScreen.height / 2 - rFrame.height / 2, 400, 280);

		// Torna a janela visível
		this.setVisible(true);

		// preenche as combo Box
		categoryViewController.setData(comboBoxCategoryParentAdd, comboBoxCategoryEdit, comboBoxCategoryDelete,
				comboBoxCategoryParentEdit);

		// açoes dos botoes
		buttonConfirmAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				categoryViewController.createdNewCategory(textFieldDescriptionAdd, comboBoxCategoryParentAdd, CategoryView.this);
				categoryViewController.setData(comboBoxCategoryParentAdd, comboBoxCategoryEdit, comboBoxCategoryDelete,
						comboBoxCategoryParentEdit);
			}
		});

		buttonConfirmEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				categoryViewController.putCategory(comboBoxCategoryParentEdit, comboBoxCategoryEdit,
						textFieldNewDescriptionEdit, CategoryView.this);
				categoryViewController.setData(comboBoxCategoryParentAdd, comboBoxCategoryEdit, comboBoxCategoryDelete,
						comboBoxCategoryParentEdit);
			}
		});
		
		buttonConfirmDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				categoryViewController.delete(comboBoxCategoryDelete, CategoryView.this);
				categoryViewController.setData(comboBoxCategoryParentAdd, comboBoxCategoryEdit, comboBoxCategoryDelete,
						comboBoxCategoryParentEdit);
			}
		});

		buttonCancelAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CategoryView.this.dispose();
			}
		});

		buttonCancelEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CategoryView.this.dispose();
			}
		});
		
		buttonCancelDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CategoryView.this.dispose();
			}
		});
	}

}