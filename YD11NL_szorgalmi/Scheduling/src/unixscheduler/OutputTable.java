package unixscheduler;

import java.awt.BorderLayout;

import javax.swing.JPanel;

//a kimeneti tablazat formatuma
public class OutputTable extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private CustomTable output;
	private CustomTable header;

	public OutputTable(int posx, int posy, int width, int cycles, ProcessArray pa) {
		super();
		int processes = pa.getLength();
		setBounds(posx, posy, 400, (cycles+3)*17);
		setLayout(new BorderLayout());
		//2 kulon tablazat a fejlecnek es a tablazatnak, ezek kozvetlenul egymas alatt lesznek
		output= new CustomTable(cycles+2, processes*2+3);
		header = new CustomTable(1, processes+2);
		output.setRowHeight(17);
		header.setRowHeight(17);
		createHeader(pa);
		add(header,BorderLayout.NORTH);
		add(output, BorderLayout.SOUTH);
	}
	
	//az elso sorokban az oszlopcimek beallitasa
	private void createHeader(ProcessArray pa) {
		header.setValueAt("",0,0);
		header.setValueAt("Reschedule", 0, header.getColumnCount()-1);
		for(int i=0; i<pa.getLength(); i++) {
			header.setValueAt("Process "+pa.getProcess(i).getName(), 0, i+1);
			output.setValueAt(pa.getProcess(i).getP_usrpri(), 1, 1+2*i);
			output.setValueAt(pa.getProcess(i).getP_cpu(), 1, 2+2*i);
			output.setValueAt("p_pri", 0, 1+2*i);
			output.setValueAt("p_cpu", 0, 2+2*i);
		}
		output.setValueAt("Clock tick", 0, 0);
		output.setValueAt("Starting point", 1, 0);
		output.setValueAt("Running after", 0, output.getColumnCount()-1);
		output.setValueAt("Running before", 0, output.getColumnCount()-2);
		setDimensions();
		
	}
	//1. es utolso oszlopok meretenek igazitasa
	public void setDimensions() {
		header.getColumnModel().getColumn(0).setPreferredWidth(80);
		header.getColumnModel().getColumn(0).setMinWidth(80);
		header.getColumnModel().getColumn(0).setMaxWidth(80);
		output.getColumnModel().getColumn(0).setPreferredWidth(80);
		output.getColumnModel().getColumn(0).setMinWidth(80);
		output.getColumnModel().getColumn(0).setMaxWidth(80);
		
		header.getColumnModel().getColumn(header.getColumnCount()-1).setPreferredWidth(200);
		header.getColumnModel().getColumn(header.getColumnCount()-1).setMinWidth(200);
		header.getColumnModel().getColumn(header.getColumnCount()-1).setMaxWidth(200);
		output.getColumnModel().getColumn(output.getColumnCount()-1).setPreferredWidth(100);
		output.getColumnModel().getColumn(output.getColumnCount()-1).setMinWidth(100);
		output.getColumnModel().getColumn(output.getColumnCount()-1).setMaxWidth(100);
		output.getColumnModel().getColumn(output.getColumnCount()-2).setPreferredWidth(100);
		output.getColumnModel().getColumn(output.getColumnCount()-2).setMinWidth(100);
		output.getColumnModel().getColumn(output.getColumnCount()-2).setMaxWidth(100);
	}
	
	//adott sort feltolt
	public void fillTableRow(int cycle, ProcessArray pa, String bef, String aft) {
		output.setValueAt(cycle, cycle+1, 0);
		for(int i=0; i<pa.getLength(); i++) {
			output.setValueAt(pa.getProcess(i).getP_usrpri(), cycle+1, 1+2*i);
			output.setValueAt(pa.getProcess(i).getP_cpu(), cycle+1, 2+2*i);
			
		}
		if(cycle==1) {
			output.setValueAt(aft, cycle, output.getColumnCount()-1);
		}
		output.setValueAt(bef, cycle+1, output.getColumnCount()-2);
		output.setValueAt(aft, cycle+1, output.getColumnCount()-1);
	}
}
