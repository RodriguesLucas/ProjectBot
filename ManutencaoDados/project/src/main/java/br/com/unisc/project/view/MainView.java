package br.com.unisc.project.view;

//Importações
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;


import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//Classe correspondente à tela inicial do programa
public class MainView extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JLabel labelInstructions; // Label para texto instrutivo
	private JPanel panelMain; // Panel principal da janela
	private JButton buttonCategories; // Botão para acessar as categorias
	private JButton buttonProducts; // Botão para acessar os produtos
	private JButton buttonQueries; // Botão para acessar as consultas

	// Texto para label de instruções
	private final String stringInstructions = "<html><center>Bem-vindo ao Sistema de Produtos."
			+ "<br>Abaixo estão suas opções.</center></html>";

	// Ajuda do botão de categorias
	private final String stringToolTipCategories = "Exibe uma janela para adicionar, editar e excluir categorias.";

	// Ajuda do botão de produtos
	private final String stringToolTipProducts = "Exibe uma janela para adicionar, editar e excluir produtos.";

	// Ajuda do botão de consultas
	private final String stringToolTipQueries = "Exibe uma janela para verificar as"
			+ " consultas feitas pelos clientes.";

	// Construtor
	public MainView(String title) {
		super(title); // Chama construtor da classe pai
		initComponents(); // Inicia os componentes
	}

	// Função que inicializa os componentes
	private void initComponents() {
		// Termina aplicação ao fechar
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Criação dos componentes
		panelMain = new JPanel();
		labelInstructions = new JLabel(stringInstructions, JLabel.CENTER);
		buttonCategories = new JButton("Administrar categorias");
		buttonCategories.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CategoryView("Categorias");
			}
		});
		buttonProducts = new JButton("Administrar produtos");
		buttonProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ProductView("Produtos");
			}
		});
		buttonQueries = new JButton("Verificar consultas");

		setContentPane(panelMain); // Define o painel da janela

		// Atribui tamanhos mínimos aos botões e à janela
		buttonCategories.setMinimumSize(new Dimension(400, 20));
		buttonProducts.setMinimumSize(new Dimension(400, 20));
		buttonQueries.setMinimumSize(new Dimension(400, 20));
		this.setMinimumSize(new Dimension(500, 250));

		// Atribui os textos de ajuda aos botões
		buttonCategories.setToolTipText(stringToolTipCategories);
		buttonProducts.setToolTipText(stringToolTipProducts);
		buttonQueries.setToolTipText(stringToolTipQueries);

		// Atribui mnemônicos aos botões
		buttonCategories.setMnemonic(KeyEvent.VK_C);
		buttonProducts.setMnemonic(KeyEvent.VK_P);
		buttonQueries.setMnemonic(KeyEvent.VK_V);

		// Atribui ícones aos botões

		// Atribui listeners aos botões

		// Definição e configuração de layout
		GroupLayout layout = new GroupLayout(panelMain);
		panelMain.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap(100, Short.MAX_VALUE)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(labelInstructions, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
										.addComponent(buttonCategories, GroupLayout.Alignment.LEADING,
												GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(buttonProducts, GroupLayout.Alignment.LEADING,
												GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(buttonQueries, GroupLayout.Alignment.LEADING,
												GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addContainerGap(100, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(labelInstructions).addGap(20, 20, 20)
				.addComponent(buttonCategories, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(buttonProducts, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(buttonQueries, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addContainerGap(40, Short.MAX_VALUE)));

		// Torna os componentes e a janela visíveis
		labelInstructions.setVisible(true);
		buttonCategories.setVisible(true);
		buttonProducts.setVisible(true);
		buttonQueries.setVisible(true);
		this.setVisible(true);

		// Posiciona a janela no centro
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle rScreen = ge.getMaximumWindowBounds();
		Rectangle rFrame = this.getBounds();
		this.setBounds(rScreen.width / 2 - rFrame.width / 2, rScreen.height / 2 - rFrame.height / 2, 500, 250);
	}
}
