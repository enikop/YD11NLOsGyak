package myschedulingprogram.graphics;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

public class NotEditableTable extends JTable {
	private static final long serialVersionUID = 1L;
	
	public NotEditableTable(int i, int j) {
		super(i, j);
		this.setDefaultRenderer(Object.class, new MyTableRenderer());
	}

	@Override
    public boolean isCellEditable(int row, int column) {
       return false;
    }
	
	private class MyTableRenderer extends DefaultTableCellRenderer
	{
		private static final long serialVersionUID = 1L;

		@Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	    {
	        final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        //1. sor színe különbözõ, mint a többié
	        c.setBackground(row == 0 ? new Color(0, 102, 102) : UIManager.getColor("Button.light"));
	        c.setForeground(row == 0 ? UIManager.getColor("Button.light") : Color.black);
	        return c;
	    }
	}
	
}