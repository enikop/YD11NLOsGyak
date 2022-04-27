package unixscheduler;

import java.util.ArrayList;
import java.util.Collections;

//ket tablazat egymas alatt, jol beallitva a cellameretet

public class ProcessArray {
	ArrayList<UnixProcess> processes;
	ArrayList<UnixProcess> queue;
	private static double kf;
	private static boolean rr = true;
	//az eredeti processzek eltárolására, ha mást választanánk oldalt (RR, kf)
	ArrayList<UnixProcess> original;
	
	public ProcessArray() {
		processes = new ArrayList<UnixProcess>();
		queue = new ArrayList<UnixProcess>();
		original = new ArrayList<UnixProcess>();
	}
	
	public void addProcess(UnixProcess uxp) {
		processes.add(uxp);
		original.add(new UnixProcess(uxp));
		queue.add(uxp);
	}
	
	//original allapotot visszallitja
	public void resetProcessArray() {
		for(int i=0; i<original.size(); i++) {
			processes.get(i).setName(original.get(i).getName());
			processes.get(i).setP_usrpri(original.get(i).getP_usrpri());
			processes.get(i).setP_cpu(original.get(i).getP_cpu());
			processes.get(i).setP_nice(original.get(i).getP_nice());
			processes.get(i).setlastRun(0);
		}
	}
	
	public void printProcessArray(ArrayList<UnixProcess> processes) {
		for(int i=0; i<processes.size(); i++) {
			System.out.println(processes.get(i));
		}
		System.out.println();
	}
	
	public String getNameOfRunning() {
		return queue.get(0).getName();
	}
	
	
	public void doACycle(int cycle) {
		queue.get(0).increaseRuntime();
		if(cycle%100==0) {
			queue.get(0).setlastRun(cycle);
			for(int i=0; i<processes.size(); i++) {
				processes.get(i).ageCpuTime();
				processes.get(i).calculatePriority(kf);
			}
			setQueue();
		} else if(cycle%10==0 && rr) {
			queue.get(0).setlastRun(cycle);
			queue.add(queue.get(0));
			queue.remove(0);
		}
	}
	
	public void setQueue() {
		queue.clear();
		setWhoRunsNext();
		for(int i=0; i<processes.size(); i++) {
			//ha fut az i. processz, hozzaadjuk a sorhoz
			if(processes.get(i).getIsRunning()) {
				queue.add(processes.get(i));
			}
		}
		//rendezzük a sort legutóbbi futási idõk szerint
		Collections.sort(queue);
		
	}

	public void resetIsRunning() {
		for(int i=0; i<processes.size(); i++) {
			processes.get(i).setRunning(false);
		}
	}
	//beallitja az isRunning ertekeket a legkisebb prioritasuaknal true-ra
	public void setWhoRunsNext() {
		resetIsRunning();
		int min_pr = getMinUsrpri();
		for(int i=0; i<processes.size(); i++) {
			if(processes.get(i).getP_usrpri()==min_pr) {
				processes.get(i).setRunning(true);
			}
		}
		
	}
	//legkisebb elofordulo prioritas
	private int getMinUsrpri() {
		int min_pr = 127;
		for(int i=0; i<processes.size(); i++) {
			if(processes.get(i).getP_usrpri() < min_pr) {
				min_pr = processes.get(i).getP_usrpri();
			}
		}
		return min_pr;
	}

	public void calculateKf(double outerkf) {
		kf=outerkf;
		if(outerkf==0) {
			//varakozok/(varakozok+1)
			kf=(processes.size()-1)/(double)processes.size();
		}
	}

	public ArrayList<UnixProcess> getProcesses() {
		return processes;
	}

	public ArrayList<UnixProcess> getQueue() {
		return queue;
	}
	
	public UnixProcess getProcess(int index) {
		return processes.get(index);
	}
	
	public int getLength() {
		return processes.size();
	}

	public double getKf() {
		return kf;
	}
	
	public void setRR(boolean b) {
		rr=b;
	}
}
