package br.com.unisc.project.controller;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.com.unisc.project.dtos.ClientDto;
import br.com.unisc.project.dtos.HistoryDto;
import br.com.unisc.project.dtos.ProductDto;
import br.com.unisc.project.service.HistoryViewService;
import br.com.unisc.project.view.HistoryView;

public class HistoryViewController implements Runnable{
	private HistoryViewService historyViewService = new HistoryViewService();
	private HistoryView historyView;
	private Thread thread;
	private boolean exit;

	public HistoryViewController(HistoryView historyView) {
		this.historyView = historyView;
	}

	public void fillTableClients(JFrame frame, JTable clients) {
		DefaultTableModel model = (DefaultTableModel) clients.getModel();
		ClientDto[] clientsInfo = historyViewService.findAllClients();
		if (clientsInfo != null) {
			for (ClientDto c : clientsInfo) {
				model.addRow(new Object[] { String.valueOf(c.getChatId()), c.getName(),
						String.valueOf(c.getNumQueries()), String.valueOf(c.getPriceMean()) });
			}
		} else {
			JOptionPane.showMessageDialog(frame, "Nenhum cliente fez consultas.", "Nenhuma consulta",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void fillTableQueries(JTable queries, long id) {
		DefaultTableModel model = (DefaultTableModel) queries.getModel();
		HistoryDto[] queriesInfo = historyViewService.findAllQueriesByClientId(id);
		if (queriesInfo != null) {
			for (HistoryDto h : queriesInfo) {
				ProductDto p = historyViewService.findProductById(h.getProductId());
				model.addRow(new Object[] { String.valueOf(h.getProductId()), p.getDescription(), p.getInfoTec(),
						String.valueOf(p.getPrice()), h.getDate().toString() });
			}
		}
	}

	public void onWindowOpened(WindowEvent e) {
		HistoryView source = (HistoryView) e.getSource();
		JTable clients = source.getTableClients();
		fillTableClients((JFrame) source, clients);
		thread = new Thread(this);
	}

	public void onTableActionPerformed(MouseEvent e) {
		JTable source = (JTable) e.getSource();
		int index = source.getSelectedRow();
		if(index != -1) {
			String stringClientId = (String) source.getModel().getValueAt(index, 0);
			fillTableQueries(historyView.getTableProducts(), Long.parseLong(stringClientId));
		}
	}

	public void onButtonActionPerformed(ActionEvent e) {
		updateData();
	}
	
	public void updateData() {
		JTable tableClients = historyView.getTableClients();
		String stringClientId = (String) tableClients.getModel().getValueAt(tableClients.getSelectedRow(), 0);
		fillTableClients((JFrame) historyView, tableClients);
		fillTableQueries(historyView.getTableProducts(), Long.parseLong(stringClientId));
	}
	
	public void onWindowClosed(WindowEvent e) {
		this.exit = true;
	}
	
	@Override
	public void run() {
		while(!exit) {
			updateData();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				
			}
		}
	}
}
