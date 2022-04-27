package myschedulingprogram.graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

//protected tagokat a leszármazott használja, a kód átláthatósága miatt lett a setup és a scheduling osztály kettészedve

public class GraphicsSetup extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	protected JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	
	protected JLabel checkinfo;
	protected JLabel instructions;
	protected JLabel title;
	protected JLabel lblName;
	protected JLabel lblArrival;
	protected JLabel lblCpu;
	
	protected JButton b_numIn;
	protected JButton b_procIn;
	protected JButton b_deleteIn;
	protected JButton b_switchTables;
	protected JButton b_newScheduling;
	
	protected JTextField input_process;
	protected JTextField input_arrival;
	protected JTextField input_cpu;
	protected JTextField input_num;
	
	protected JComboBox<String> choiceOfScheduling;
	protected JScrollPane pane;
	protected JScrollPane paneRR;
	protected NotEditableTable output;
	protected NotEditableTable outputRR;
	
	public GraphicsSetup() {
		panel1=new JPanel(null);
		panel2 = new JPanel();
		checkinfo = new JLabel("");
		instructions = new JLabel("");
		title = new JLabel("");
		lblName = new JLabel("");
		lblArrival = new JLabel("");
		lblCpu = new JLabel("");
		b_numIn = new JButton("ok");
		b_procIn = new JButton("ok");
		b_deleteIn = new JButton("<< visszavon");
		b_switchTables = new JButton("összesítés >>");;
		b_newScheduling = new JButton("új");;
		input_process = new JTextField("");
		input_arrival = new JTextField("");
		input_cpu = new JTextField("");
		input_num = new JTextField("");
		String[] choices =  {"algoritmus", "FCFS", "SJF", "RR"};
		choiceOfScheduling = new JComboBox<String>(choices);
		pane = new JScrollPane();
		paneRR = new JScrollPane();
		output = new NotEditableTable(1,1);
		outputRR = new NotEditableTable(1,1);
    	
    	initializeLabels();
    	initializeButtons();
        initializeTextFields();
        initializeOthers();
        
        this.setVisible(true);
	}

	
	private void initializeLabels() {
		Font smallFont= new Font(Font.SERIF, Font.PLAIN,  13);
		Color bg = new Color(192, 192, 192);
		
		setBackground(bg);
		setSize(800, 700); 
		getContentPane().setLayout(null);
		setTitle("Klasszikus ütemezési algoritmusok");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel1.setBounds(0, 0, 800, 700);
		panel1.setBackground(bg);
		getContentPane().add(panel1);
		
		panel2.setBackground(new Color(0, 102, 102));
        panel2.setBounds(0, 0, 790, 250);
        panel2.setLayout(null);
        
        panel3 = new JPanel();
        panel3.setBackground(new Color(192, 192, 192));
        panel3.setBounds(0, 20, 790, 43);
        panel3.setLayout(null);
        panel2.add(panel3);
        panel1.add(panel2);
		
		instructions.setText("Az ütemezésre váró processzek száma:");
		instructions.setFont(new Font("Perpetua", Font.PLAIN, 19));
		instructions.setBounds(95, 90, 678, 29);
		instructions.setForeground(UIManager.getColor("CheckBox.background"));
        instructions.setVerticalAlignment(SwingConstants.BOTTOM);
		
		JLabel footer = new JLabel ("Palencsár Enikõ, Miskolci Egyetem, Informatikai Intézet, encipalencsar@gmail.com", SwingConstants.CENTER);
		footer.setBounds(0, 600, 800, 60);
		footer.setBackground(bg);
		footer.setFont(smallFont);
		
		checkinfo.setText("");
		checkinfo.setBounds(100, 190, 400, 20);
		checkinfo.setFont(new Font("Perpetua", Font.PLAIN, 16));
		checkinfo.setForeground(new Color(255, 255, 255));
		
		title.setText("Klasszikus ütemezés");
        title.setBackground(new Color(192, 192, 192));
        title.setVerticalAlignment(SwingConstants.BOTTOM);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Perpetua Titling MT", Font.PLAIN, 30));
        title.setBounds(0, 3, 780, 43);
        
        lblName.setText("Név");
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblName.setForeground(UIManager.getColor("Button.light"));
        lblName.setBounds(100, 135, 46, 14);
        lblName.setVisible(false);
        
        
        lblArrival.setText("Érkezés");
        lblArrival.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblArrival.setForeground(UIManager.getColor("Button.light"));
        lblArrival.setBounds(280, 135, 46, 14);
        lblArrival.setVisible(false);
        
        
        lblCpu.setText("CPU löket");
        lblCpu.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblCpu.setForeground(UIManager.getColor("Button.light"));
        lblCpu.setBounds(460, 135, 46, 14);
        lblCpu.setVisible(false);
       
        panel3.add(title);
        panel2.add(instructions);
    	panel2.add(checkinfo);
        panel2.add(lblName);
        panel2.add(lblArrival);
        panel2.add(lblCpu);
        panel1.add(footer, BorderLayout.CENTER);
        
		
	}
	
	private void initializeButtons() {
		Color bg = new Color(192, 192, 192);
		
		b_numIn.setBounds(640, 147, 60, 27);
		b_numIn.setBackground(bg);
		
		b_procIn.setBounds(640, 147, 60, 27);
		b_procIn.setVisible(false);
		b_procIn.setBackground(bg);
		
		b_newScheduling.setBounds(640, 600, 60, 27);
		b_newScheduling.setVisible(true);
		b_newScheduling.setBackground(new Color(0, 102, 102));
		b_newScheduling.setForeground(bg);
		
		b_deleteIn.setBounds(610, 189, 120, 27);
		b_deleteIn.setVisible(false);
		b_deleteIn.setBackground(bg);
		
		b_switchTables.setBounds(610, 147, 120, 27);
		b_switchTables.setVisible(false);
		b_switchTables.setBackground(bg);
		
		panel1.add(b_newScheduling);
		panel2.add(b_deleteIn);
		panel2.add(b_switchTables);
		panel2.add(b_numIn);
		panel2.add(b_procIn);
		
		
	}
	
	private void initializeOthers() {
    	choiceOfScheduling.setBounds(100, 190, 250, 30);
    	choiceOfScheduling.setBackground(new Color(192, 192, 192));
    	choiceOfScheduling.setVisible(false);
    	
    	output.setRowHeight(17);
    	output.setTableHeader(null);
    	
    	outputRR.setRowHeight(17);
    	outputRR.setTableHeader(null);
    	
    	pane.setViewportView(output);
    	pane.getVerticalScrollBar().setBackground(new Color(170, 170, 170));
    	pane.getHorizontalScrollBar().setBackground(new Color(170, 170, 170));
    	pane.getVerticalScrollBar().setUI(new CustomScrollbar(new Color(0, 102, 102)));
    	pane.getHorizontalScrollBar().setUI(new CustomScrollbar(new Color(0, 102, 102)));
    	pane.setBorder(BorderFactory.createEmptyBorder());
    	pane.setVisible(false);
    	
    	paneRR.setViewportView(outputRR);
    	paneRR.getVerticalScrollBar().setBackground(new Color(170, 170, 170));
    	paneRR.getHorizontalScrollBar().setBackground(new Color(170, 170, 170));
    	paneRR.getVerticalScrollBar().setUI(new CustomScrollbar(new Color(0, 102, 102)));
    	paneRR.getHorizontalScrollBar().setUI(new CustomScrollbar(new Color(0, 102, 102)));
    	paneRR.setBorder(BorderFactory.createEmptyBorder());
    	paneRR.setVisible(false);
    	
    	panel2.add(choiceOfScheduling);
    	panel1.add(pane);
		panel1.add(paneRR);
		
	}

	private void initializeTextFields() {
		Color bg = new Color(192, 192, 192);
		
		input_num.setBounds(100, 150, 80, 20);
		input_num.setBackground(bg);
		input_num.setBorder(BorderFactory.createEmptyBorder());
		
		input_process.setBounds(100, 150, 80, 20);
		input_process.setBackground(bg);
		input_process.setBorder(BorderFactory.createEmptyBorder());
		input_process.setVisible(false);
		
    	input_arrival.setBounds(280, 150, 80, 20);
    	input_arrival.setBackground(bg);
    	input_arrival.setBorder(BorderFactory.createEmptyBorder());
    	input_arrival.setVisible(false);
    	
		input_cpu.setBounds(460, 150, 80, 20);
		input_cpu.setBackground(bg);
		input_cpu.setBorder(BorderFactory.createEmptyBorder());
		input_cpu.setVisible(false);
		
		panel2.add(input_num);
		panel2.add(input_process);
		panel2.add(input_arrival);
    	panel2.add(input_cpu);
		
	}   
}