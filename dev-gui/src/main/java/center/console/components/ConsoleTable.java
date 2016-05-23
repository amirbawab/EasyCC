package center.console.components;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ConsoleTable extends JTable {

	private static final long serialVersionUID = -6976897050297294281L;

	public ConsoleTable(Object[][] data, Object[] header) {
		setModel(new ConsoleTableModel(data, header));
		getTableHeader().setReorderingAllowed(false);
	}

	/**
	 * Color a row
	 * @param row
	 * @param color
     */
	public void colorRow(int row, Color color) {
		for(int col=0; col < getColumnCount(); col++) {
			JLabel l = (JLabel) getCellRenderer(row, col).getTableCellRendererComponent(this, null, false, false, row, col);
			l.setBackground(color);
		}
	}
	
	/**
	 * Add row to table
	 * @param data
	 */
	public void addRow(Object[] data) {
		ConsoleTableModel model = (ConsoleTableModel) getModel();
		model.addRow(data);
	}
	
	/**
	 * Clear all rows
	 */
	public void clearAll() {
		ConsoleTableModel model = (ConsoleTableModel) getModel();
		int rowCount = model.getRowCount();
		for(int i=0; i<rowCount; i++)
			model.removeRow(0);
	}
	
	/**
	 * Table custom model
	 */
	class ConsoleTableModel extends DefaultTableModel {

		private static final long serialVersionUID = 7902134137847991346L;

		/**
		 * Create model
		 * @param data
		 * @param header
		 */
		public ConsoleTableModel(Object[][] data, Object[] header) {
			super(data, header);
		}
		
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	}


}
