package myschedulingprogram.algorithm;

//Osztály a bejövõ processzek tárolására
public class Process implements Comparable<Process> {
	private String p_name;
	private int p_arrival;
	private int p_cpu;
	
	public Process(String name, int arrival, int cpu) {
		p_name = name;
		p_arrival = arrival;
		p_cpu = cpu;
	}
	
	//hatralevo cpu ido csokkentese az idoszelet ertekevel
	public void decrementByTimeslice(int timeslice) {
		p_cpu-=timeslice;
	}
	
	public String toString() {
		return p_name+", "+p_arrival+", "+p_cpu;
	}
	
	//getterek, setterek
	public String getP_name() {
		return p_name;
	}

	public int getP_arrival() {
		return p_arrival;
	}

	public int getP_cpu() {
		return p_cpu;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	public void setP_arrival(int p_arrival) {
		this.p_arrival = p_arrival;
	}

	public void setP_cpu(int p_cpu) {
		this.p_cpu = p_cpu;
	}
	
	//erkezes szerinti rendezesi elv
	public int compareTo(Process p) {
		return this.p_arrival- p.p_arrival;
	}

}
