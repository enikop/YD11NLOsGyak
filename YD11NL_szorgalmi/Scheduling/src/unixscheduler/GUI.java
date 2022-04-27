package unixscheduler;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import myschedulingprogram.graphics.CustomScrollbar;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;


public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel panel_1 = new JPanel();
	
	private JPanel panel = new JPanel();
	
	private OutputTable outTable;
	private JScrollPane pane;
	private JScrollPane smallpane;
	private JTextField tf_name;
	private JTextField tf_pri;
	private JTextField tf_cpu;
	private JTextField tf_nice;
	private JTextField tf_kf;
	private JTextField tf_ticks;
	
	private JTextPane lbl_summary;
	
	private JLabel lbl_kf;
	private JLabel lbl_ticks;
	private JLabel lbl_title;
	private JLabel lbl_name;
	private JLabel lbl_cpu;
	private JLabel lbl_pri;
	private JLabel lbl_nice;
	private JLabel lbl_errorMessage;
	private JLabel lbl_instructions1;
	
	private JCheckBox chckbx_rr;
	private JCheckBox chckbx_kf;
	
	private JButton b_startScheduling;
	private JButton b_newWindow;
	private JButton b_nextStep;
	private JButton b_addProcess;
	
	private ProcessArray processArray = new ProcessArray();
	
	public GUI() {
		setSize(1000, 600);
		getContentPane().setBackground(new Color(238, 235, 221));
		getContentPane().setLayout(null);
		setTitle("UNIX ütemezési algoritmus");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel lbl_about = new JLabel();
		lbl_about.setText("Palencsár Enikõ, Miskolci Egyetem, Informatikai Intézet, encipalencsar@gmail.com");
		lbl_about.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lbl_about.setForeground(new Color(92, 92, 92));
		lbl_about.setVisible(true);
		lbl_about.setBounds(425, 535, 500, 20);
		getContentPane().add(lbl_about);
		
		panel_1.setBackground(new Color(216, 182, 164));
		panel_1.setBounds(0, 25, 330, 50);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		panel.setBackground(new Color(128, 0, 0));
		panel.setBounds(0, 0, 300, 600);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		initializeLabels();
		initializeTextFields();
		initializeButtons();
		initializeOthers();
	}
	
	//Enterre gombnyomás
	private class ClickAddProcess implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				if(processArray.getLength()<19) {
					b_addProcess.doClick();
					return;
				}
				b_nextStep.doClick();
				
			}	
	}
	
	private class ClickSchedule implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			b_startScheduling.doClick();
			
		}	
}
	
	//gorgetheto tablak es checkboxok
	private void initializeOthers() {
		chckbx_rr = new JCheckBox("RR");
		chckbx_rr.setSelected(true);
		chckbx_rr.setBounds(230, 145, 50, 20);
		chckbx_rr.setBackground(new Color(216, 182, 164));
		chckbx_rr.setVisible(false);
		chckbx_rr.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					processArray.setRR(true);
				} else {
					processArray.setRR(false);
				}
				
			}
			
		});
		panel.add(chckbx_rr);
		
		chckbx_kf = new JCheckBox("KF");
		chckbx_kf.setSelected(true);
		chckbx_kf.setBounds(230, 185, 50, 20);
		chckbx_kf.setBackground(new Color(216, 182, 164));
		chckbx_kf.setVisible(false);
		chckbx_kf.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					lbl_kf.setVisible(true);
					tf_kf.setVisible(true);
				} else {
					lbl_kf.setVisible(false);
					tf_kf.setVisible(false);
					processArray.calculateKf(0);
				}
				
			}
			
		});
		panel.add(chckbx_kf);
		pane = new JScrollPane();
		pane.getVerticalScrollBar().setBackground(new Color(218, 215, 201));
    	pane.getHorizontalScrollBar().setBackground(new Color(218, 215, 201));
    	pane.setBackground(new Color(238, 235, 221));
		pane.getVerticalScrollBar().setUI(new CustomScrollbar(new Color(216, 182, 164)));
    	pane.getHorizontalScrollBar().setUI(new CustomScrollbar(new Color(216, 182, 164)));
    	pane.setVisible(false);
    	pane.setViewportView(outTable);
		pane.setBounds(350, 75, 600, 450);
		pane.setBorder(BorderFactory.createEmptyBorder());
    	getContentPane().add(pane);
    	
    	smallpane=new JScrollPane();
    	smallpane.getVerticalScrollBar().setBackground(new Color(108, 0, 0));
    	smallpane.getHorizontalScrollBar().setBackground(new Color(108, 0, 0));
    	smallpane.setBackground(new Color(238, 235, 221));
		smallpane.getVerticalScrollBar().setUI(new CustomScrollbar(new Color(238, 235, 221)));
    	smallpane.getHorizontalScrollBar().setUI(new CustomScrollbar(new Color(238, 235, 221)));
    	smallpane.setBorder(BorderFactory.createEmptyBorder());
    	smallpane.setViewportView(lbl_summary);
    	smallpane.setBounds(20, 440, 260, 100);
    	panel.add(smallpane);
		
	}
	
	//gombok
	private void initializeButtons() {
		b_startScheduling = new JButton("ok");
		b_startScheduling.setBounds(210, 250, 70, 20);
		b_startScheduling.setBackground(new Color(216, 182, 164));
		b_startScheduling.setBorder(BorderFactory.createEmptyBorder());
		b_startScheduling.setVisible(false);
		b_startScheduling.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int cycles;
				lbl_errorMessage.setText("");
				if(chckbx_kf.isSelected()) {
					//be kell olvasnunk a korrekciós faktort
					double kf;
					try {
						kf = Double.parseDouble(tf_kf.getText());
						if(kf > 1 || kf <= 0) throw new Exception();
					} catch(Exception exc) {
						lbl_errorMessage.setText("A korrekciós faktor 0 és 1 közötti szám.");
						return;
					}
					processArray.calculateKf(kf);
				}
				try {
					cycles = Integer.parseInt(tf_ticks.getText());
					if(cycles <= 0 || cycles > 1500) throw new Exception();
				} catch(Exception exc) {
					lbl_errorMessage.setText("Az óraütések száma 1500 alatti pozitív egész.");
					return;
				}
				//processzlista alapállapotba állítása
				processArray.resetProcessArray();
				outTable = new OutputTable(0, 10, 400, cycles, processArray);
				String bef, aft;
				//RR varakozasi sor felallitasa
				processArray.setQueue();
				for(int i=1; i<=cycles; i++) {
					//ki fut a kor elott
					bef = processArray.getNameOfRunning();
					processArray.doACycle(i);
					//ki fut a kor utan
					aft = processArray.getNameOfRunning();
					outTable.fillTableRow(i, processArray, bef, aft);
				}
				pane.setViewportView(outTable);
				pane.setBounds(350, 75, 600, Math.min(450, processArray.getLength()>2 ? (cycles+3)*17+10 :(cycles+3)*17));
				pane.setVisible(true);
				
				
			}
			
		});
		panel.add(b_startScheduling);
		
		
		b_newWindow = new JButton("új");
		b_newWindow.setBackground(new Color(128, 0, 0));
		b_newWindow.setForeground(new Color(238, 235, 221));
		b_newWindow.setBorder(BorderFactory.createEmptyBorder());
		b_newWindow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new GUI().setVisible(true);;	
			}	
		});
		b_newWindow.setBounds(867, 25, 80, 20);
		getContentPane().add(b_newWindow);
		
		
		b_nextStep = new JButton("tovább");
		b_nextStep.setBackground(new Color(128, 0, 0));
		b_nextStep.setForeground(new Color(238, 235, 221));
		b_nextStep.setBorder(BorderFactory.createEmptyBorder());
		b_nextStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				b_addProcess.doClick();
				b_addProcess.setVisible(false);
				b_nextStep.setVisible(false);
				tf_name.setVisible(false);
				tf_pri.setVisible(false);
				tf_cpu.setVisible(false);
				tf_nice.setVisible(false);
				lbl_instructions1.setVisible(false);
				lbl_name.setVisible(false);
				lbl_pri.setVisible(false);
				lbl_cpu.setVisible(false);
				lbl_nice.setVisible(false);
				
				chckbx_kf.setVisible(true);
				chckbx_rr.setVisible(true);
				b_startScheduling.setVisible(true);
				tf_kf.setVisible(true);
				tf_ticks.setVisible(true);
				lbl_kf.setVisible(true);
				lbl_ticks.setVisible(true);
				smallpane.setVisible(true);
				
				lbl_errorMessage.setText("");
				lbl_errorMessage.setBounds(580, 25, 455, 20);
				
			}
		});
		b_nextStep.setBounds(867, 490, 80, 20);
		b_nextStep.setVisible(false);
		getContentPane().add(b_nextStep);
		
		b_addProcess = new JButton("+");
		b_addProcess.setBackground(new Color(128, 0, 0));
		b_addProcess.setBorder(BorderFactory.createEmptyBorder());
		b_addProcess.setForeground(new Color(238, 235, 221));
		b_addProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(processArray.getLength()==18) {
					b_addProcess.setVisible(false);
				}
				lbl_errorMessage.setText("");
				String name = tf_name.getText();
				if(name.isEmpty()) {
					name = "P."+processArray.getLength();
				}
				int pri, cpu, nice;
				try {
					pri = Integer.parseInt(tf_pri.getText());
					cpu = Integer.parseInt(tf_cpu.getText());
					nice = Integer.parseInt(tf_nice.getText());
					try {
						if(pri < 50 || pri > 127) throw new Exception("A prioritás 50 és 127 közötti.");
						if(cpu<0) throw new Exception("A CPU idõ nem lehet negatív.");
						if(nice<-20 || nice>20) throw new Exception("A nice érték -20 és 20 közé essen!");
						processArray.addProcess(new UnixProcess(name, pri, cpu, nice));
						lbl_instructions1.setText("Írja be a(z) "+(processArray.getLength()+1)+". processz adatait!");
						lbl_errorMessage.setText("Felvéve: "+name+": p_usrpri="+pri+", p_cpu="+cpu+", p_nice="+nice);
						lbl_summary.setText(lbl_summary.getText()+name+": p_usrpri="+pri+", p_cpu="+cpu+", p_nice="+nice+"\n");
						b_nextStep.setVisible(true);
						tf_name.setText("");
						tf_pri.setText("");
						tf_cpu.setText("");
						tf_nice.setText("");
					} catch(Exception exc) {
						lbl_errorMessage.setText(exc.getMessage());
					}
				} catch(Exception exc) {
					lbl_errorMessage.setText("A prioritás, a cpu idõ és a nice érték számok.");
					return;
				}
			}
		});
		b_addProcess.setBounds(867, 460, 80, 20);
		getContentPane().add(b_addProcess);
		
	}

	//szovegmezok
	private void initializeTextFields() {
		
		tf_kf = new JTextField();
		tf_kf.setBounds(150, 185, 50, 20);
		tf_kf.setBackground(new Color(216, 182, 164));
		tf_kf.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
		tf_kf.setVisible(false);
		tf_kf.addActionListener(new ClickSchedule());
		panel.add(tf_kf);
		tf_kf.setColumns(10);
		
		tf_ticks = new JTextField();
		tf_ticks.setBounds(150, 145, 50, 20);
		tf_ticks.setBackground(new Color(216, 182, 164));
		tf_ticks.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
		tf_ticks.setVisible(false);
		tf_ticks.addActionListener(new ClickSchedule());
		panel.add(tf_ticks);
		tf_ticks.setColumns(10);

		tf_name = new JTextField();
		tf_name.setBounds(650, 160, 70, 20);
		tf_name.setBackground(new Color(216, 182, 164));
		tf_name.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
		tf_name.addActionListener(new ClickAddProcess());
		getContentPane().add(tf_name);
		tf_name.setColumns(10);
		
		tf_pri = new JTextField();
		tf_pri.setBounds(650, 220, 70, 20);
		tf_pri.setBackground(new Color(216, 182, 164));
		tf_pri.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
		tf_pri.addActionListener(new ClickAddProcess());
		getContentPane().add(tf_pri);
		tf_pri.setColumns(10);
		
		tf_cpu = new JTextField();
		tf_cpu.setBounds(650, 280, 70, 20);
		tf_cpu.setBackground(new Color(216, 182, 164));
		tf_cpu.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
		tf_cpu.addActionListener(new ClickAddProcess());
		getContentPane().add(tf_cpu);
		tf_cpu.setColumns(10);
		
		tf_nice = new JTextField();
		tf_nice.setBounds(650, 340, 70, 20);
		tf_nice.setBackground(new Color(216, 182, 164));
		tf_nice.addActionListener(new ClickAddProcess());
		tf_nice.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
		getContentPane().add(tf_nice);
		tf_nice.setColumns(10);
		
	}

	//cimkek, kiirasok inicializalasa
	private void initializeLabels() {
		
		lbl_summary = new JTextPane();
		lbl_summary.setText("");
		lbl_summary.setBackground(new Color(216, 182, 164));
		lbl_summary.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lbl_summary.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		lbl_summary.setEditable(false);
		
		JTextPane lblProcessList = new JTextPane();
    	lblProcessList.setBounds(20, 408, 260, 28);
    	lblProcessList.setBackground(new Color(216, 182, 164));
    	lblProcessList.setText("Felvett processzek:");
    	lblProcessList.setFont(new Font("Times New Roman", Font.PLAIN, 15));
    	lblProcessList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    	lblProcessList.setEditable(false);
    	panel.add(lblProcessList);

		StyledDocument documentStyle = lblProcessList.getStyledDocument();
		SimpleAttributeSet centerAttribute = new SimpleAttributeSet();
		StyleConstants.setAlignment(centerAttribute, StyleConstants.ALIGN_CENTER);
		documentStyle.setParagraphAttributes(0, documentStyle.getLength(), centerAttribute, false);
		
		lbl_kf = new JLabel("Korrekciós faktor");
		lbl_kf.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lbl_kf.setForeground(new Color(238, 235, 221));
		lbl_kf.setVisible(false);
		lbl_kf.setBounds(25, 185, 118, 20);
		panel.add(lbl_kf);
		
		lbl_ticks = new JLabel("Óraütés");
		lbl_ticks.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lbl_ticks.setForeground(new Color(238, 235, 221));
		lbl_ticks.setVisible(false);
		lbl_ticks.setBounds(25, 145, 83, 17);
		panel.add(lbl_ticks);
		
		lbl_title = new JLabel("UNIX ütemezés");
		lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_title.setBounds(0, 7, 330, 50);
		lbl_title.setFont(new Font("Perpetua Titling MT", Font.PLAIN, 33));
		panel_1.add(lbl_title);
		
		lbl_name = new JLabel("p_name");
		lbl_name.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lbl_name.setBounds(550, 160, 72, 20);
		getContentPane().add(lbl_name);
		
		lbl_pri = new JLabel("p_usrpri");
		lbl_pri.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lbl_pri.setBounds(550, 220, 72, 20);
		getContentPane().add(lbl_pri);
		
		lbl_cpu = new JLabel("p_cpu");
		lbl_cpu.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lbl_cpu.setBounds(550, 280, 46, 20);
		getContentPane().add(lbl_cpu);
		
		lbl_nice = new JLabel("p_nice");
		lbl_nice.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lbl_nice.setBounds(550, 340, 72, 20);
		getContentPane().add(lbl_nice);
		
		lbl_errorMessage = new JLabel("");
		lbl_errorMessage.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbl_errorMessage.setBounds(492, 395, 455, 45);
		getContentPane().add(lbl_errorMessage);
		
		lbl_instructions1 = new JLabel("Írja be a(z) 1. processz adatait!");
		lbl_instructions1.setFont(new Font("Times New Roman", Font.PLAIN, 28));
		lbl_instructions1.setBounds(450, 80, 390, 33);
		getContentPane().add(lbl_instructions1);
		
	}
}
