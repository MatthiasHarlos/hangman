package de.newenergycoes.hangman.gui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JTable;

public class MyTable {

	private JTable table;
	
	public MyTable(String data[][], String col[]) {
		table = new JTable(data, col);
		table.setSurrendersFocusOnKeystroke(true);
		table.setFillsViewportHeight(true);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setBackground(new Color(245, 222, 179));
		table.setPreferredScrollableViewportSize(new Dimension(300, 100));
	}
	
	public JTable getTable() {
		return this.table;
	}
	
	//stretches Table in best way!
	public void setAutoResizeTable() {
		int widthForAllColumn = table.getWidth() / table.getColumnCount();
		for (int column = 0; column < table.getColumnCount(); column++) {
			table.getColumnModel().getColumn(column).setWidth(widthForAllColumn);
		}
	}


}
