package unixscheduler;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

public class CustomTable extends JTable {
	private static final long serialVersionUID = 1L;
	
	public CustomTable(int i, int j) {
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
	        //minden 100. sor kiemelve, az 1. adatokat kiveve
	        c.setBackground((row-1)%100==0 && row!=1 ? new Color(128, 0, 0) : UIManager.getColor("Button.light"));
	        c.setForeground((row-1)%100==0 && row!=1 ? UIManager.getColor("Button.light") : Color.black);
	        //fejlec szines
	        if(row==0) {
	        	c.setBackground(new Color(128, 0, 0));
	        	c.setForeground(Color.WHITE);
	        }
	        setHorizontalAlignment(SwingConstants.CENTER);
	        return c;
	    }
	}
	
}
