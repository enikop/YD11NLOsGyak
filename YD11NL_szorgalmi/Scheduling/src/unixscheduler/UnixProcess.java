package unixscheduler;

public class UnixProcess implements Comparable<UnixProcess> {
	private String name;
	private int p_usrpri;
	private int p_cpu;
	private int p_nice;
	private boolean isRunning = false;
	private int lastRun = 0; //RR-hez kell a legutóbbi futás ideje
	private static final int P_USER = 50;
	
	public UnixProcess(String name, int p_usrpri, int p_cpu, int p_nice) {
		this.name = name;
		this.p_usrpri = p_usrpri;
		this.p_cpu = p_cpu;
		this.p_nice = p_nice;
	}
	public UnixProcess(UnixProcess uxp) {
		this.name = uxp.name;
		this.p_usrpri = uxp.p_usrpri;
		this.p_cpu = uxp.p_cpu;
		this.p_nice = uxp.p_nice;
	}
	
	public void increaseRuntime() {
		p_cpu++;
	}
	
	public void ageCpuTime() {
		p_cpu = (int)Math.floor(p_cpu/2.0);
	}
	
	public void calculatePriority(double kf) {
		p_usrpri = Math.min(127, (int)Math.floor(P_USER + p_cpu*kf + 2*p_nice));
		if(p_usrpri < 50) {
			p_usrpri = 50;
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getP_usrpri() {
		return p_usrpri;
	}
	public void setP_usrpri(int p_usrpri) {
		this.p_usrpri = p_usrpri;
	}
	public int getP_cpu() {
		return p_cpu;
	}
	public void setP_cpu(int p_cpu) {
		this.p_cpu = p_cpu;
	}
	public int getP_nice() {
		return p_nice;
	}
	public void setP_nice(int p_nice) {
		this.p_nice = p_nice;
	}

	public boolean getIsRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	public String toString() {
		return name+": p_usrpri="+p_usrpri+", p_cpu="+p_cpu+", p_nice="+p_nice;
	}

	public int getlastRun() {
		return lastRun;
	}

	public void setlastRun(int runtimeRR) {
		this.lastRun = runtimeRR;
	}

	@Override
	public int compareTo(UnixProcess o) {
		if(this.lastRun < o.lastRun) {
			return -1;
		} else if(this.lastRun < o.lastRun) {
			return 1;
		}
		return 0;
	}
	
}
