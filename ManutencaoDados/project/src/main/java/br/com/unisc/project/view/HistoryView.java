/* 
 *  Classe que trata da view e faz inicializações das configurações necessárias para o histórico
 * Autores @nicolasfischer @brunobolzan @lucasrodrigues 
 */
package br.com.unisc.project.view;

//Importações
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import br.com.unisc.project.controller.HistoryViewController;

//Classe da janela de gestão de consultas
public class HistoryView extends JFrame {

	private static final long serialVersionUID = 1L;
	// Componentes necessários
	private JPanel panelMain;
	private JPanel panelClients;
	private JPanel panelProducts;
	private JTable tableClients;
	private JTable tableProducts;
	private JButton buttonRefresh;
	private HistoryViewController historyViewController;

	/*
	 * construtor da classe
	 * Objetivo: instanciar um objeto
	 * Parâmetros: nenhum
	 */
	public HistoryView(String title) {
		super(title);
		historyViewController = new HistoryViewController(this);
		initComponents();
	}


	/*
	 * initComponents
	 * Objetivo: inicializar os componentes
	 * Parâmetros: nenhum
	 */
	private void initComponents() {
		// Destrói janela ao fechar
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Criação dos componentes
		panelMain = new JPanel();
		panelClients = new JPanel();
		panelProducts = new JPanel();
		tableClients = new JTable();
		tableProducts = new JTable();
		buttonRefresh = new JButton("Atualizar tela");

		// Atribuição de borda aos painéis
		panelClients.setBorder(new TitledBorder("Clientes que consultaram produtos"));
		panelProducts.setBorder(new TitledBorder("Produtos consultados pelo cliente selecionado"));

		setContentPane(panelMain); // Define o painel da janela

		// Atribui painéis e componentes aos respectivos pais
		panelProducts.add(new JScrollPane(tableProducts));
		panelMain.add(panelClients);
		panelMain.add(panelProducts);
		panelMain.add(buttonRefresh);

		// Atribui modelos às tabelas
		tableClients.setModel(new DefaultTableModel(
				new Object[] { "Cód. Cliente", "Nome Cliente", "Nº Consultas", "Média de Interesse (R$)" }, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		tableProducts.setModel(new DefaultTableModel(
				new Object[] { "Cód. Produto", "Descrição", "Info. Técnica", "Preço (R$)", "Data/Hora" }, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		// Atribui teclas de atalho
		buttonRefresh.setMnemonic(KeyEvent.VK_A);

		// Atribui textos de ajuda aos componentes pertinentes
		tableClients.setToolTipText("Clientes que consultaram produtos.");
		tableProducts.setToolTipText("Produtos consultados pelo cliente selecionado");
		buttonRefresh.setToolTipText("Recarrega as informações na tela.");

		// Configura os layouts dos painéis
		GroupLayout layout = new GroupLayout(panelMain);
		panelMain.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(panelClients).addContainerGap())
				.addGroup(
						layout.createSequentialGroup().addContainerGap().addComponent(panelProducts).addContainerGap())
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(buttonRefresh, 0, 0, Short.MAX_VALUE).addContainerGap()));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(panelClients).addContainerGap())
				.addGroup(
						layout.createSequentialGroup().addContainerGap().addComponent(panelProducts).addContainerGap())
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(buttonRefresh)
						.addContainerGap()));

		GroupLayout layoutClients = new GroupLayout(panelClients);
		panelClients.setLayout(layoutClients);
		JScrollPane scrollClients = new JScrollPane(tableClients);
		layoutClients.setHorizontalGroup(layoutClients.createParallelGroup()
				.addGroup(layoutClients.createSequentialGroup().addComponent(scrollClients)));
		layoutClients.setVerticalGroup(layoutClients.createSequentialGroup()
				.addGroup(layoutClients.createSequentialGroup().addComponent(scrollClients)));

		GroupLayout layoutProducts = new GroupLayout(panelProducts);
		panelProducts.setLayout(layoutProducts);
		JScrollPane scrollProducts = new JScrollPane(tableProducts);
		layoutProducts.setHorizontalGroup(layoutProducts.createParallelGroup()
				.addGroup(layoutProducts.createSequentialGroup().addComponent(scrollProducts)));
		layoutProducts.setVerticalGroup(layoutProducts.createSequentialGroup()
				.addGroup(layoutProducts.createSequentialGroup().addComponent(scrollProducts)));

		// Define tamanho mínimo da janela
		this.setMinimumSize(new Dimension(650, 400));
		// Posiciona a janela no centro
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle rScreen = ge.getMaximumWindowBounds();
		Rectangle rFrame = this.getBounds();
		this.setBounds(rScreen.width / 2 - rFrame.width / 2, rScreen.height / 2 - rFrame.height / 2, 650, 400);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				HistoryView.this.historyViewController.onWindowOpened(e);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				HistoryView.this.historyViewController.onWindowClosed(e);
			}
		});

		tableClients.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				HistoryView.this.historyViewController.onTableActionPerformed(e);
			}
		});

		buttonRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				HistoryView.this.historyViewController.onButtonActionPerformed(e);
			}
		});

		// Torna a janela visível
		this.setVisible(true);
	}

	public JTable getTableClients() {
		return tableClients;
	}

	public JTable getTableProducts() {
		return tableProducts;
	}

}
