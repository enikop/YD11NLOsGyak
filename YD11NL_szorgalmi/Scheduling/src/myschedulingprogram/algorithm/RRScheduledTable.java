package myschedulingprogram.algorithm;

//RR esetere az utemezett kimeneti tablazat, a sima ScheduledTable leszarmazottja plusz osszesitett adattagokkal
public class RRScheduledTable extends ScheduledTable {
	private String[] process_array; //P_infos-zal ellentetben minden processz egyszer szerepel benne
	private int[] total_wait;
	private int[] response_time;
	private int[] total_turnaround;
	
	
	public RRScheduledTable(int size, Process[] p_infos) {
		super(size);
		int numberOfProcesses = p_infos.length;
		process_array = new String[numberOfProcesses];
		total_wait = new int[numberOfProcesses];
		response_time = new int[numberOfProcesses];
		total_turnaround = new int[numberOfProcesses];
		//BasicSchedulertol p_infos tombbol atvesszuk a processz neveket
		for(int i=0; i<numberOfProcesses; i++) {
			process_array[i]=p_infos[i].getP_name();
		}
	}
	
	//processzenkent az osszes varakozas, es a korulfordulas szamitasa, a valaszidok eltarolasa
	public void calculateRRValues() {
		boolean isFirstOccurence; //valaszidonek csak az elso futasig valo varakozast tekintjuk
		for(int j=0; j<process_array.length; j++) {
			isFirstOccurence = true;
			for(int i=0; i<getSize(); i++) {
				if(process_array[j].equals(p_infos[i].getP_name())) {
					total_wait[j]+=p_wait[i];
					total_turnaround[j]+=p_turnaround[i];
					if(isFirstOccurence) {
						response_time[j]+=p_response[i];
						isFirstOccurence = false;
					}
				}
			}
		}
	}
	
	
	//kiiras teszteleshez
	@Override
	public void writeOut() {
		super.writeOut();
		for(int i=0; i<process_array.length; i++) {
			System.out.println(process_array[i]+":"+total_wait[i]+":"+total_turnaround[i]+":"+response_time[i]);
		}
	}
	
	//osszefoglalo tabla meretenek lekerese
	public int getSizeOfSummaryTable() {
		return process_array.length;
	}

	//getterek INDEX szerint
	public String getProcess_array(int i) {
		return process_array[i];
	}


	public int getTotal_wait(int i) {
		return total_wait[i];
	}


	public int getResponse_time(int i) {
		return response_time[i];
	}


	public int getTotal_turnaround(int i) {
		return total_turnaround[i];
	}
}
