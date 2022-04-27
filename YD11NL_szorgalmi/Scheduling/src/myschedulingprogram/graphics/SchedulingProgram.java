package myschedulingprogram.graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import myschedulingprogram.algorithm.BasicScheduler;
import myschedulingprogram.algorithm.Process;
import myschedulingprogram.algorithm.RRScheduledTable;
import myschedulingprogram.algorithm.ScheduledTable;

public class SchedulingProgram extends GraphicsSetup {
	private static final long serialVersionUID = 1L;
	private BasicScheduler data;
	
	public SchedulingProgram() {
		data = new BasicScheduler();
		b_deleteIn.addActionListener(new SchedulingButtonHandler());  
        b_numIn.addActionListener(new SchedulingButtonHandler());
        b_procIn.addActionListener(new SchedulingButtonHandler());
        b_switchTables.addActionListener(new SchedulingButtonHandler());
        b_newScheduling.addActionListener(new SchedulingButtonHandler());
        choiceOfScheduling.addActionListener(new MenuHandler());
    	input_num.addActionListener(new SchedulingButtonHandler());
    	input_process.addActionListener(new SchedulingButtonHandler());
    	input_arrival.addActionListener(new SchedulingButtonHandler());
    	input_cpu.addActionListener(new SchedulingButtonHandler());
	}
	
	private class SchedulingButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==b_numIn || e.getSource() == input_num) {
				numInHandler();
				return;
			}
			if(e.getSource()==b_procIn || e.getSource()==input_arrival || e.getSource()==input_cpu || e.getSource()==input_process) {
				procInHandler();
				return;
			}
			if(e.getSource()==b_deleteIn) {
				deleteInHandler();
				return;
			}
			if(e.getSource()==b_switchTables) {
				switchTablesHandler();
				return;
			}
			if(e.getSource()==b_newScheduling) {
				new SchedulingProgram();
			}
			
		}

		//RR eset�n v�lt�s a k�t eredm�ny t�bl�zat k�z�tt
		private void switchTablesHandler() {
			if(b_switchTables.getText().equals("<< vissza")) {
        		b_switchTables.setText("�sszes�t�s >>");
        		pane.setVisible(true);
        		paneRR.setVisible(false);
        	} else {
        		b_switchTables.setText("<< vissza");
        		paneRR.setVisible(true);
        		pane.setVisible(false);
        	}
			
		}

		//legut�bbi processz input t�rl�se
		private void deleteInHandler() {
			//dat�b�l az utols� felt�lt�tt processzt t�r�lj�k
			Process deleted = data.getProcessByIndex(data.getFilledSpaces()-1);
	    	checkinfo.setText("T�r�lve: "+deleted);
	    	data.decrementFilledSpaces();
	    	instructions.setText("Processz "+(data.getFilledSpaces()+1)+" neve, �rkez�se, l�ketideje:");
	    	if(data.getFilledSpaces()==0) {
	    		b_deleteIn.setVisible(false);
	    	}
		}

		//processz input v�gleges�t�s
		private void procInHandler() {
			String userInProcess = input_process.getText();
        	String userInArrival = input_arrival.getText();
        	String userInCpu = input_cpu.getText();
        	input_process.setText("");
        	input_cpu.setText("");
        	input_arrival.setText("");
        	checkinfo.setText("");
        	int cpuTime, arrivalTime;
        	try {
        		arrivalTime=Integer.parseInt(userInArrival);
        		cpuTime=Integer.parseInt(userInCpu);
        		//hib�s sz�madatok eset�n kiv�tel dob�sa
        		if(arrivalTime<0 || cpuTime<1 ) {
        			throw(new Exception());
        		}
        		//ha a felhaszn�l� nem adott meg processz nevet, legyen alap�rtelmezetten p
        		if(userInProcess.isEmpty()) {
        			userInProcess = "p";
        		}
        		data.addProcessToArray(data.getFilledSpaces(), new Process(userInProcess, arrivalTime, cpuTime));
        		checkinfo.setText("Felv�ve: "+userInProcess+", "+userInArrival+", "+userInCpu);
        		instructions.setText("Processz "+(data.getFilledSpaces()+1)+" neve, �rkez�se, l�ketideje:");
        		b_deleteIn.setVisible(true);
        	} catch(Exception exc) {
        		checkinfo.setText("Hiba. Pozit�v eg�sz sz�mokat adjon meg!");
        	}
        	//ha az utols� processzhez �rt�nk
        	if(data.getFilledSpaces()==data.getSize()) {
        		//ne legyen ugyanolyan nev� processz k�tszer, ez �sszezavarn� az RR-t
        		String s;
        		int count;
        		for(int i=0; i<data.getSize(); i++) {
        			count = 1;
        			s = data.getProcessByIndex(i).getP_name();
        			for(int j=i+1; j<data.getSize(); j++) {
        				if(s.equals(data.getProcessByIndex(j).getP_name())) {
        					//uj nevek: nev.1 nev.2 stb
        					data.getProcessByIndex(j).setP_name(s+"."+count);
        					count++;
        				}
        			}
        		}
        		
        		input_process.setVisible(false);
        		input_arrival.setVisible(false);
            	input_cpu.setVisible(false);
    			b_procIn.setVisible(false);
    			b_deleteIn.setVisible(false);
    			b_numIn.setVisible(false);
    			lblName.setVisible(false);
    	        lblArrival.setVisible(false);
    	        lblCpu.setVisible(false);
    	        
    			checkinfo.setText("");
    			instructions.setText("V�lasszon algoritmust!");
    			choiceOfScheduling.setVisible(true);
        	}
			
		}

		//a processzek sz�m�nak beolvas�sa
		private void numInHandler() {
			String userIn = input_num.getText();
        	input_num.setText("");
        	checkinfo.setText("");
        	int numOfProcesses;
        	try {
            	numOfProcesses = Integer.parseInt(userIn);
            	if(numOfProcesses < 2 || numOfProcesses > 20) {
            		throw(new Exception());
            	}
            	//�ll�tsuk be a t�mb m�ret�t
            	data.resetSize(numOfProcesses);
            	
            	input_num.setVisible(false);
            	b_numIn.setVisible(false);
            	instructions.setText("Processz 1 neve, �rkez�se, l�ketideje:");
            	input_process.setVisible(true);
            	input_arrival.setVisible(true);
            	input_cpu.setVisible(true);
    			b_procIn.setVisible(true);
    			lblName.setVisible(true);
    	        lblArrival.setVisible(true);
    	        lblCpu.setVisible(true);
            } catch (Exception exc) {
            	checkinfo.setText("Hiba. Egyn�l nagyobb, h�szn�l kisebb eg�sz sz�mot adjon meg!");
            }
			
		}

	}
	
	//az algoritmus v�laszt� kezel�se
	private class MenuHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			ScheduledTable table;
			String x = String.valueOf(choiceOfScheduling.getSelectedItem());
            instructions.setText("�temez�s "+x+ " algoritmussal: ");
            checkinfo.setText("");
            checkinfo.setVisible(true);
            pane.setVisible(false);
        	paneRR.setVisible(false);
        	b_switchTables.setVisible(false);
            checkinfo.setBounds(100, 120, 600, 40);
            if(x.equals("FCFS")) {
            	try {
            		//utemezes elvegzese, tablazat keszitese
            		table = data.fcfs();
            		createTable(table);
            		pane.setBounds(10, 270, 750, Math.min(17*(table.getSize()+1), 300));
                	pane.setVisible(true);
                	//Gantt diagram keszitese, ha megfeleloek az adatok (nem lenne tul szeles/zsufolt)
                	try {
                		createGantt(table);
                	} catch (Exception exc){
                		checkinfo.setText(exc.getMessage());
                	}
            	} catch (Exception exc ){
            		checkinfo.setText("Hiba, pr�b�lja �jra!");
            	}
            }else if(x.equals("SJF")) {
            	try {
            		//utemezes, tablazat keszitese
	        		table = data.sjf();
	        		createTable(table);
	        		pane.setBounds(10, 270, 750, Math.min(17*(table.getSize()+1), 300));
	            	pane.setVisible(true);
	            	//Gantt diagram
	            	try {
	            		createGantt(table);
	            	} catch (Exception exc){
	            		checkinfo.setText(exc.getMessage());
	            	}
            	} catch(Exception exc) {
            		checkinfo.setText("Mem�riahiba, pr�b�lja �jra!");
            	}
            } else if(x.equals("RR")) {
            	instructions.setText("�temez�s "+x+ " algoritmussal, �rja be a timeslice �rt�k�t!");
            	//idoszelet bekerese
            	String userIn = JOptionPane.showInputDialog(panel1, "Id�szelet:", null);
            	b_switchTables.setText("�sszes�t�s >>");
        		pane.setVisible(true);
        		paneRR.setVisible(false);
            	try {
            		//idoszelet ellenorzese
                	int timeslice = Integer.parseInt(userIn);
                	if(timeslice < 1) {
                		throw new IllegalArgumentException("Pozit�v �rt�ket adjon meg!");
                	}
                	//utemezes, tablazat es osszesito tablazat keszitese
                	table=data.rr(timeslice);
                	createTable(table);
                	createRRTable((RRScheduledTable)table);
                	pane.setBounds(10, 270, 750, Math.min(17*(table.getSize()+1), 300));
                	pane.setVisible(true);
                	//tablazatok kozti valto gomb megjelenitese
                	b_switchTables.setVisible(true);
                	paneRR.setBounds(10, 270, 750, Math.min(17*(((RRScheduledTable)table).getSizeOfSummaryTable()+1), 300));
                	//Gantt diagram generalasa, ha nem lenne tul zsufolt
                	createRRGantt(table, timeslice);
                	
                } catch(NumberFormatException nfe){
                	checkinfo.setText("Sz�mot adjon meg!");
                } catch(IllegalArgumentException iae) {
                	checkinfo.setText(iae.getMessage());
                }
            	catch (Exception exc) {
                	checkinfo.setText(exc.getMessage());
                }
            } else {
            	instructions.setText("V�lasszon algoritmust!");
            }
        }
		
		//utemezesi tablazat feltoltese
		private void createTable(ScheduledTable table) {
			output.setModel(new DefaultTableModel(table.getSize()+1, 8));
			output.setValueAt("Processz", 0, 0);
			output.setValueAt("�rkez�s", 0, 1);
			output.setValueAt("CPU l�ket", 0, 2);
			output.setValueAt("Indul�s", 0, 3);
			output.setValueAt("Befejez�s", 0, 4);
			output.setValueAt("V�rakoz�s", 	0, 5);
			output.setValueAt("V�laszid�", 0, 6);
			output.setValueAt("K�r�lfordul�s", 0, 7);
			for(int i=0; i<table.getSize(); i++) {
				output.setValueAt(table.getP_info(i).getP_name(), i+1, 0);
				output.setValueAt(table.getP_info(i).getP_arrival(), i+1, 1);
				output.setValueAt(table.getP_info(i).getP_cpu(), i+1, 2);
				output.setValueAt(table.getP_start(i), i+1, 3);
				output.setValueAt(table.getP_finish(i), i+1, 4);
				output.setValueAt(table.getP_wait(i), i+1, 5);
				output.setValueAt(table.getP_response(i), i+1, 6);
				output.setValueAt(table.getP_turnaround(i), i+1, 7);
			}
		}
		
		//RR osszesito tablazat feltoltese
		private void createRRTable(RRScheduledTable table) {
			outputRR.setModel(new DefaultTableModel(table.getSizeOfSummaryTable()+1, 4));
			outputRR.setValueAt("Processz", 0, 0);
			outputRR.setValueAt("V�rakoz�s", 0, 1);
			outputRR.setValueAt("V�laszid�", 0, 2);
			outputRR.setValueAt("K�r�lfordul�s", 0, 3);
			for(int i=0; i<table.getSizeOfSummaryTable(); i++) {
				outputRR.setValueAt(table.getProcess_array(i), i+1, 0);
				outputRR.setValueAt(table.getTotal_wait(i), i+1, 1);
				outputRR.setValueAt(table.getResponse_time(i), i+1, 2);
				outputRR.setValueAt(table.getTotal_turnaround(i), i+1, 3);
			}
		}
		
		//Gantt diagram keszitese
		private void createGantt(ScheduledTable table) throws Exception {
			String[] vaxis = new String[table.getSize()];
	    	int[][] source = new int[table.getSize()][3];
	    	//source szerkezete: erkezes, kezdes, befejezes
	    	for(int i=0; i<table.getSize(); i++) {
	    		vaxis[i]=table.getP_info(i).getP_name();
	    		source[i][0]=table.getP_info(i).getP_arrival();
	    		source[i][1]=table.getP_start(i);
	    		source[i][2]=table.getP_finish(i);
	    	}
	    	int width = calculateWidth(source);
	    	//ha 1000-nel kisebb, generalhatjuk, ha nem, kivetelt dobunk, tul szeles lenne a diagram
	    	if(width <= 1000) {
	    		new GanttDiagramGenerator(vaxis, source, width).setVisible(true);
	    	} else {
	    		throw new Exception("Gantt diagram nem gener�lhat�, pr�b�lja kevesebb/r�videbb processzel!");
	    	}
	    		
		}
		
		//Gantt diagram keszitese RR-hoz
		private void createRRGantt(ScheduledTable table, int timeslice) throws Exception {
			String[] vaxis =new String[data.getSize()];
	    	int[][] source;
	    	//soruce szerkezete: erkezes, indulas, befejezes, indulas, befejezes, indulas...stb
	    	//Maximalis fordulasok szamanak kiszamitasa - a legtobbszor futo futasainak szama
	    	int max=0;
	    	int tmp;
	    	for(int i=0; i<data.getSize(); i++) {
	    		vaxis[i]=data.getProcessByIndex(i).getP_name();
	    		tmp=(int)Math.ceil(data.getProcessByIndex(i).getP_cpu()*1.0/timeslice);
	    		if(tmp > max) {
	    			max = tmp;
	    		}
	    		
	    	}
	    	//source 2. dimenzi�ja: fordul�sok sz�ma*2+1 (2: indulas, befejezes, +1:�rkez�s)
	    	source = new int[data.getSize()][1+max*2];
	    	int counter;
	    	for(int i=0; i<vaxis.length; i++) {
	    		counter=0;
	    		for(int j=0; j<table.getSize(); j++ ) {
	        		if(vaxis[i].equals(table.getP_info(j).getP_name())) {
	        			//ha meg nincs erkezesi idonk felveve a processzhez, felvesszuk
	        			if(counter==0) {
	        				source[i][0]=table.getP_info(j).getP_arrival();
	        				counter++;
	        			}
	        			
	        			//felvesszuk a processzhez az adott indulast es erkezest
	        			source[i][counter]=table.getP_start(j);
	        			source[i][counter+1]=table.getP_finish(j);
	        	        counter+=2;
	        		}
	        	}
	    	}
	    	int width = calculateWidth(source);
	    	//ha a timeslice nem tul kicsi a hosszhoz viszonyitva es az ossz hossz nem tul nagy: generaljuk a diagramot
	    	if(width <= 1000 && width/timeslice < 30) {
	    		new GanttDiagramGenerator(vaxis, source, width).setVisible(true);
	    	} else if(width > 1000) {
	    		throw new Exception("Gantt diagram nem gener�lhat�, pr�b�lja kevesebb/r�videbb processzel!");
	    	} else {
	    		throw new Exception("Gantt diagram nem gener�lhat�, a timeslice t�l kicsi (legyen nagyobb, mint "+(int)Math.ceil(width/30)+").");
	    	}
			
		}
		
		//kiszamitja a legutolso processz befejezesi idejet
		 private int calculateWidth(int[][] source) {
		    	int max=0;
		    	
		    	for(int i=1; i<source.length; i++) {
		    		for(int j=2; j<source[0].length; j+=2) {
		    			if(source[i][j] > max) max = source[i][j];
		    		}
		    	}
		    	return max;
		}

	}
}
