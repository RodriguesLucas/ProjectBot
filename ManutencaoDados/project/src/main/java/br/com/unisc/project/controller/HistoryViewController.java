package br.com.unisc.project.controller;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.com.unisc.project.dtos.ClientDto;
import br.com.unisc.project.dtos.HistoryDto;
import br.com.unisc.project.dtos.ProductDto;
import br.com.unisc.project.service.HistoryViewService;
import br.com.unisc.project.view.HistoryView;

public class HistoryViewController implements Runnable {
	private HistoryViewService historyViewService = new HistoryViewService();
	private HistoryView historyView;
	private Thread thread;
	private boolean exit;

	public HistoryViewController(HistoryView historyView) {
		this.historyView = historyView;
	}

	public synchronized void fillTableClients(JFrame frame, JTable clients) {
		DefaultTableModel model = (DefaultTableModel) clients.getModel();
		model.setRowCount(0);
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

	public synchronized void fillTableQueries(JTable queries, long id) {
		DefaultTableModel model = (DefaultTableModel) queries.getModel();
		model.setRowCount(0);
		HistoryDto[] queriesInfo = historyViewService.findAllQueriesByClientId(id);
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		if (queriesInfo != null) {
			for (HistoryDto h : queriesInfo) {
				ProductDto p = historyViewService.findProductById(h.getProductId());
				model.addRow(new Object[] { String.valueOf(h.getProductId()), p.getDescription(), p.getInfoTec(),
						String.valueOf(p.getPrice()), df.format(Date.from(h.getDate()))});
			}
		}
	}

	public void onWindowOpened(WindowEvent e) {
		HistoryView source = (HistoryView) e.getSource();
		JTable clients = source.getTableClients();
		fillTableClients((JFrame) source, clients);
		thread = new Thread(this);
		thread.start();
	}

	public void onTableActionPerformed(MouseEvent e) {
		JTable source = (JTable) e.getSource();
		int index = source.getSelectedRow();
		if (index != -1) {
			String stringClientId = (String) source.getModel().getValueAt(index, 0);
			fillTableQueries(historyView.getTableProducts(), Long.parseLong(stringClientId));
		}
	}

	public void onButtonActionPerformed(ActionEvent e) {
		updateData();
	}

	public synchronized void updateData() {
		JTable tableClients = historyView.getTableClients();
		int index = tableClients.getSelectedRow();
		if (index != -1) {
			String stringClientId = (String) tableClients.getModel().getValueAt(index, 0);
			fillTableClients((JFrame) historyView, tableClients);
			fillTableQueries(historyView.getTableProducts(), Long.parseLong(stringClientId));
		} else {
			fillTableClients((JFrame) historyView, tableClients);
		}
	}

	public void onWindowClosed(WindowEvent e) {
		this.exit = true;
	}

	@Override
	public void run() {
		while (!exit) {
			updateData();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				System.out.println("eeeee");
			}
		}
	}
}
