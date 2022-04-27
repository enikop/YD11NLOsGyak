package myschedulingprogram.algorithm;

import java.util.Arrays;

//utemezesi algoritmusokat vegzo osztaly
public class BasicScheduler {
	private Process[] p_infos;
	private boolean[] alreadyScheduled;
	private int filledSpaces = 0; //a mar feltoltott elemek szama
	
	//meret megvaltoztatasa, mindenkeppen hivodnia kell a processzam meghatarozasa utan
	//ismerjuk a processzek szamat, letrehozzuk a processz tombot
	//SJF-hez kell az alreadyScheduled tomb, jelzi, utemeztuk-e mar az adott processzt
	public void resetSize(int numOfProcesses) {
		p_infos = new Process[numOfProcesses];
		alreadyScheduled = new boolean[numOfProcesses];
	}
	
	//processz beletevese a tombbe i. helyre
	public void addProcessToArray(int index, Process p) {
		p_infos[index] = p;
		if(filledSpaces<p_infos.length) {
			filledSpaces++;
		}
	}
	
	//betoltott helyek szamanak csokkentese, utolso elem torlese eseten kell
	public void decrementFilledSpaces() {
		filledSpaces--;
	}
	
	//getterek
	public Process getProcessByIndex(int index) {
		return p_infos[index];
	}
	
	public Process[] getP_infos() {
		return p_infos;
	}
	
	public int getFilledSpaces() {
		return filledSpaces;
	}
	
	public int getSize() {
		return p_infos.length;
	}
	
	//RR-hoz. Visszadja a processzek hanyszor fognak futni adott idoszeletnel, hany CS lesz
	//azaz hany eleme lesz az utemezo tablazatnak
	public int countRunningInstances(int timeslice) {
		int counter=0;
		for(int i=0; i<p_infos.length; i++) {
			counter+=Math.ceil((float)p_infos[i].getP_cpu() /timeslice);
		}
		return counter;
	}
	
	
	//RR utemezes lebonyolitasa
	public RRScheduledTable rr(int timeslice) {
		Arrays.sort(p_infos); //erkezes szerint rendezes, ez lesz a korforgas alapja
		int switches = countRunningInstances(timeslice); //ennyi CS lesz, ilyen hosszu tablazat kell
		RRScheduledTable out = new RRScheduledTable(switches, p_infos);
		Process[] deepCopyOfInfos = new Process[p_infos.length];
		//deep copy keszitese a p_infos tombrol, hogy a processzek adatait fuggvenyen belul
		//valtoztathassuk, de ennek kivul ne legyen hatasa
		for(int i=0; i<p_infos.length; i++) {
			deepCopyOfInfos[i]=new Process(p_infos[i].getP_name(), p_infos[i].getP_arrival(), p_infos[i].getP_cpu());
		}
		//elso futo adatainak beallitasa
		out.setP_info(0, deepCopyOfInfos[0]);
		out.setP_start(0, deepCopyOfInfos[0].getP_arrival());
		out.setP_finish(0, out.getP_start(0)+Math.min(timeslice,  deepCopyOfInfos[0].getP_cpu())); //vagy idoszeletnyit fut vagy amennyi meg hatravan a cpu idobol
		out.calculateTableInformation(0); //segedszamitasok az osszegzett ertekek kiszamolasahoz
		//idoszelet levonasa a futasi idobol es erkezes beallitasa az adott processz legutobbi befejezesere
		deepCopyOfInfos[0].decrementByTimeslice(Math.min(timeslice,  deepCopyOfInfos[0].getP_cpu()));
		deepCopyOfInfos[0].setP_arrival(out.getP_finish(0));
		
		/*stabil rendezeseket alkalmazunk, ahhoz, hogy a legutobb futott processz az erkezes szerinti sor vegere
		keruljon, manualisan a tomb vegere kell tennunk a rendezes elott
		pl: ha 4-ben befejezodik az idoszelete, de pont ekkor beerkezik egy masik processz is,
		a masik fut majd eloszor, o csak utana*/
		
		Process tmpForReordering = deepCopyOfInfos[0];
		for(int i=0; i<deepCopyOfInfos.length-1; i++) {
			deepCopyOfInfos[i]=deepCopyOfInfos[i+1];
		}
		deepCopyOfInfos[deepCopyOfInfos.length-1]=tmpForReordering;
		
		
		int next;
		for(int i=1; i<switches; i++) {
			Arrays.sort(deepCopyOfInfos); //erkezes szerint
			next=-1;
			//a rendezett listaban az 1. olyan, akinek van meg hatra cpu ideje futhat legkozelebb
			for(int j=0; j<deepCopyOfInfos.length; j++) {
				if(deepCopyOfInfos[j].getP_cpu()> 0) {
					next=j;
					break;
				}
			}
			//next fut, ezutan hozza tartozo ertekek modosulnak
			out.setP_info(i, deepCopyOfInfos[next]);
			out.setP_start(i, Math.max(deepCopyOfInfos[next].getP_arrival(), out.getP_finish(i-1)));
			out.setP_finish(i, out.getP_start(i)+Math.min(timeslice,  deepCopyOfInfos[next].getP_cpu()));
			out.calculateTableInformation(i);
			deepCopyOfInfos[next].decrementByTimeslice(Math.min(timeslice,  deepCopyOfInfos[next].getP_cpu()));
			deepCopyOfInfos[next].setP_arrival(out.getP_finish(i));
			
			//a most lefutott processz a tomb vegere kerul
			tmpForReordering = deepCopyOfInfos[next];
			for(int j=next; j<deepCopyOfInfos.length-1; j++) {
				deepCopyOfInfos[j]=deepCopyOfInfos[j+1];
			}
			deepCopyOfInfos[deepCopyOfInfos.length-1]=tmpForReordering;
		
		}
		out.calculateRRValues(); //varakozas, korulfordulas osszegzese processzenkent
		
		return out;
	}
	
	//FCFS utemezes
	public ScheduledTable fcfs() {
		Arrays.sort(p_infos); //rendezunk erkezesi sorrendbe
		ScheduledTable out = new ScheduledTable(this);
		out.setP_start(0, p_infos[0].getP_arrival());
		out.setP_finish(0, out.getP_start(0)+p_infos[0].getP_cpu());
		out.calculateTableInformation(0);
		//kezdo es befejezesi idopontok meghatarozasa a 2. processztol kezdve
		for(int i=1; i<p_infos.length; i++) {
			out.setP_start(i, Math.max(p_infos[i].getP_arrival(), out.getP_finish(i-1))); //sajat erkezes es elozo befejezesenek maximuma
			out.setP_finish(i, out.getP_start(i)+p_infos[i].getP_cpu());
			out.calculateTableInformation(i);
		}
		
		return out;
	}
	
	//SJF utemezes
	public ScheduledTable sjf() {
		Arrays.sort(p_infos); //erkezesi sorrendbe
		ScheduledTable out = new ScheduledTable(this);
		int min_index = 0;
		//ha a 0. idopillanatban tobb kulonbozo hosszu processz is beerkezik, kivalasztjuk a minimalis hosszut (nagyon nem valoszinu, de megis)
		for(int i=1; i<p_infos.length && p_infos[0].getP_arrival()==p_infos[i].getP_arrival(); i++) {
			if(p_infos[min_index].getP_cpu()>p_infos[i].getP_cpu()) {
				min_index=i;
			}
		}
		//ez fut eloszor, beallitjuk, hogy mar beutemeztuk
		out.setP_info(0, p_infos[min_index]);
		out.setP_start(0, p_infos[min_index].getP_arrival());
		out.setP_finish(0, out.getP_start(0)+p_infos[min_index].getP_cpu());
		out.calculateTableInformation(0);
		alreadyScheduled[min_index]=true;
		//sorba beallitjuk a ScheduledTable tobbi elemet is
		for(int i=1; i<p_infos.length; i++) {
			min_index=-1;
			//keressuk az erkezesek minimumat a meg nem utemezett processzek kozul azoknal, amik legkozelebb erkeznek az elozo befejezeshez kepest
			for(int j=0; j<p_infos.length; j++){
				//j. nincs meg utemezve - j. mar megerkezett, mire befejezodott az elozo processz - j. vagy az elso ilyen processz vagy rovidebb, mint az elozoleg megtalaltak
				if(!alreadyScheduled[j] && p_infos[j].getP_arrival()<=out.getP_finish(i-1) && (min_index==-1 || p_infos[j].getP_cpu() < p_infos[min_index].getP_cpu())) {
					min_index=j;
				}
			}
			
			//ha nem talaltunk ilyen processzt, az azt jelenti, hogy nem futott be az elozo befejezeseig
			//egy masik processz sem, igy az erkezes szerint rendezett tombbol
			//az elso olyan lesz a kovetkezo futo, amit meg nem utemeztunk
			if(min_index==-1) {
				for(int j=1; j<p_infos.length; j++){
					if(!alreadyScheduled[j]) {
						min_index=j;
						break;
					}
				}
			}
			//egyebkent a min_indexu fog futni
			alreadyScheduled[min_index]=true;
			out.setP_info(i, p_infos[min_index]);
			out.setP_start(i, Math.max(p_infos[min_index].getP_arrival(), out.getP_finish(i-1)));
			out.setP_finish(i, out.getP_start(i)+p_infos[min_index].getP_cpu());
			out.calculateTableInformation(i);
		}
		
		//utemezes utan a segedtomb visszaallitasa
			Arrays.fill(alreadyScheduled, false);
		
		return out;
	}
	
	//kiiras konzolra ellenorzeshez
	public void printProcessCharacteristics() {
		for(Process p: p_infos) {
			System.out.println(p);
		}
	}
}
