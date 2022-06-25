package br.com.unisc.project.view;

//Importações
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import javax.swing.GroupLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

//Classe da janela de gestão de consultas
public class HistoryView extends JFrame {

 // Componentes necessários
 private JPanel panelMain;
 private JPanel panelClients;
 private JPanel panelProducts;
 private JTable tableClients;
 private JTable tableProducts;
 private JButton buttonRefresh;

 public static void main(String args[]) {
     new HistoryView("Pesquisas de preços");
 }

 // Construtor
 public HistoryView(String title) {
     super(title);
     initComponents();
 }

 // Função que inicializa os componentes
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
     panelClients.setBorder(
             new TitledBorder("Clientes que consultaram produtos"));
     panelProducts.setBorder(
             new TitledBorder("Produtos consultados pelo cliente selecionado"));

     setContentPane(panelMain); // Define o painel da janela

     // Atribui painéis e componentes aos respectivos pais
     panelProducts.add(new JScrollPane(tableProducts));
     panelMain.add(panelClients);
     panelMain.add(panelProducts);
     panelMain.add(buttonRefresh);

     // Atribui modelos às tabelas
     tableClients.setModel(new DefaultTableModel(new Object[]{
         "Cód. Cliente", "Nome Cliente", "Nº Consultas",
         "Média de Interesse (R$)"}, 0));
     tableProducts.setModel(new DefaultTableModel(new Object[]{
         "Cód. Produto", "Descrição", "Info. Técnica",
         "Preço (R$)", "Data/Hora"}, 0));

     // Atribui teclas de atalho
     buttonRefresh.setMnemonic(KeyEvent.VK_A);

     // Atribui textos de ajuda aos componentes pertinentes
     tableClients.setToolTipText("Clientes que consultaram produtos.");
     tableProducts.setToolTipText("Produtos consultados pelo cliente selecionado");
     buttonRefresh.setToolTipText("Recarrega as informações na tela.");

     // Configura os layouts dos painéis
     GroupLayout layout = new GroupLayout(panelMain);
     panelMain.setLayout(layout);
     layout.setHorizontalGroup(
             layout.createParallelGroup()
                     .addGroup(layout.createSequentialGroup()
                             .addContainerGap()
                             .addComponent(panelClients)
                             .addContainerGap())
                     .addGroup(layout.createSequentialGroup()
                             .addContainerGap()
                             .addComponent(panelProducts)
                             .addContainerGap())
                     .addGroup(layout.createSequentialGroup()
                             .addContainerGap()
                             .addComponent(buttonRefresh, 0, 0, Short.MAX_VALUE)
                             .addContainerGap()
                             ));
     layout.setVerticalGroup(
             layout.createSequentialGroup()
                     .addGroup(layout.createSequentialGroup()
                             .addContainerGap()
                             .addComponent(panelClients)
                             .addContainerGap())
                     .addGroup(layout.createSequentialGroup()
                             .addContainerGap()
                             .addComponent(panelProducts)
                             .addContainerGap())
                     .addGroup(layout.createSequentialGroup()
                             .addContainerGap()
                             .addComponent(buttonRefresh)
                             .addContainerGap()));

     GroupLayout layoutClients = new GroupLayout(panelClients);
     panelClients.setLayout(layoutClients);
     JScrollPane scrollClients = new JScrollPane(tableClients);
     layoutClients.setHorizontalGroup(
             layoutClients.createParallelGroup()
                     .addGroup(layoutClients.createSequentialGroup()
                             .addComponent(scrollClients)));
     layoutClients.setVerticalGroup(
             layoutClients.createSequentialGroup()
                     .addGroup(layoutClients.createSequentialGroup()
                             .addComponent(scrollClients)));

     GroupLayout layoutProducts = new GroupLayout(panelProducts);
     panelProducts.setLayout(layoutProducts);
     JScrollPane scrollProducts = new JScrollPane(tableProducts);
     layoutProducts.setHorizontalGroup(
             layoutProducts.createParallelGroup()
                     .addGroup(layoutProducts.createSequentialGroup()
                             .addComponent(scrollProducts)));
     layoutProducts.setVerticalGroup(
             layoutProducts.createSequentialGroup()
                     .addGroup(layoutProducts.createSequentialGroup()
                             .addComponent(scrollProducts)));

     // Define tamanho mínimo da janela
     this.setMinimumSize(new Dimension(650, 400));
     // Posiciona a janela no centro
     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
     Rectangle rScreen = ge.getMaximumWindowBounds();
     Rectangle rFrame = this.getBounds();
     this.setBounds(rScreen.width / 2 - rFrame.width / 2,
             rScreen.height / 2 - rFrame.height / 2,
             650,
             400);

     // Torna a janela visível
     this.setVisible(true);
 }

}
