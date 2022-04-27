package myschedulingprogram.algorithm;

//FCFS es SJF eseten az utemezett tablazat adatai osztalyba csomagolva
//a protected adattagokat a leszármazott használja, szorosan összefüggõ osztályok
public class ScheduledTable {
	protected Process[] p_infos;
	private int[] p_start;
	private int[] p_finish;
	protected int[] p_wait;
	protected int[] p_response;
	protected int[] p_turnaround;
	
	//atadott processz tomb atmasolasa p_infos[]-ba (shallow copy), tobbi tomb meretenek meghatarozasa
	public ScheduledTable(BasicScheduler input) {
		p_infos = new Process[input.getP_infos().length];
		for(int i=0; i<p_infos.length; i++) {
			p_infos[i]=input.getP_infos()[i];
		}
		p_start = new int[p_infos.length];
		p_finish = new int[p_infos.length];
		p_wait = new int[p_infos.length];
		p_response = new int[p_infos.length];
		p_turnaround = new int[p_infos.length];
	}
	
	//adott meretu tombok letrehozasa, a processz feltoltese nullas elemekkel, a leszarmazott RRScheduledTable letrehozasahoz
	public ScheduledTable(int size) {
		p_infos = new Process[size];
		for(int i=0; i<p_infos.length; i++) {
			p_infos[i]=new Process("p0", 0, 0);
		}
		p_start = new int[p_infos.length];
		p_finish = new int[p_infos.length];
		p_wait = new int[p_infos.length];
		p_response = new int[p_infos.length];
		p_turnaround = new int[p_infos.length];
	}
	
	//a tarolt processzek szamanak lekerdezese (sorok lesznek)
	public int getSize() {
		return p_infos.length;
	}
	
	//ha megvan p_start es p_finish, ebbol a tobbi adat kiszamitasa
	public void calculateTableInformation(int index) {
		p_wait[index] = p_start[index]-p_infos[index].getP_arrival();
		p_response[index] = p_start[index]-p_infos[index].getP_arrival();
		p_turnaround[index] = p_finish[index]-p_infos[index].getP_arrival();
	}
	
	//tomb kiirasa teszteleshez
	public void writeOut() {
		for(int i=0; i<p_infos.length; i++) {
			System.out.println(p_infos[i]+":"+p_start[i]+":"+p_finish[i]+":"+p_wait[i]+":"+p_response[i]+":"+p_turnaround[i]);
		}
	}
	
	
	//getterek, setterek de adott INDEXRE
	public Process getP_info(int index) {
		return p_infos[index];
	}

	public void setP_info(int index, Process p_info) {
		this.p_infos[index] = new Process(p_info.getP_name(), p_info.getP_arrival(), p_info.getP_cpu());
	}

	public int getP_start(int index) {
		return p_start[index];
	}

	public void setP_start(int index, int p_start) {
		this.p_start[index] = p_start;
	}

	public int getP_finish(int index) {
		return p_finish[index];
	}

	public void setP_finish(int index, int p_finish) {
		this.p_finish[index] = p_finish;
	}

	public int getP_wait(int index) {
		return p_wait[index];
	}

	public void setP_wait(int index, int p_wait) {
		this.p_wait[index] = p_wait;
	}

	public int getP_response(int index) {
		return p_response[index];
	}

	public void setP_response(int index, int p_response) {
		this.p_response[index] = p_response;
	}

	public int getP_turnaround(int index) {
		return p_turnaround[index];
	}

	public void setP_turnaround(int index, int p_turnaround) {
		this.p_turnaround[index] = p_turnaround;
	}
	
	
}
