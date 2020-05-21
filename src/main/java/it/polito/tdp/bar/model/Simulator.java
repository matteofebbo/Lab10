package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.TreeMap;

import it.polito.tdp.bar.model.Event.EventType;

public class Simulator {
	
	// coda degli eventi
	private PriorityQueue<Event> coda= new PriorityQueue<>();
	
	// parametri di simulazione
	private final int tavoli4Posti=5; // numero tavoli da 4 posti
	private final int tavoli6Posti=4; // numero tavoli da 6 posti
	private final int tavoli8Posti=4; // numero tavoli da 8 posti
	private final int tavoli10Posti=2; // numero tavoli da 10 posti
	//private Duration distanza; // intervallo tra due gruppi di clienti
	
	private final LocalTime orarioApertura= LocalTime.of(12, 00);
	private final LocalTime orarioChiusura= LocalTime.of(21, 00);
	
	// modello del mondo
	private int nTav4; // numero attuale tavoli da 4 posti
	private int nTav6; // numero attuale tavoli da 6 posti
	private int nTav8; // numero attuale tavoli da 8 posti
	private int nTav10; // numero attuale tavoli da 10 posti
	private int nTavTot;
	
	// valori da calcolare
	private int clientiTot;
	private int soddisfatti;
	private int insoddisfatti;
	
	
	
	// metodi per restituire i risultati
	
	public int getClientiTot() {
		return clientiTot;
	}
	public int getSoddisfatti() {
		return soddisfatti;
	}
	public int getInsoddisfatti() {
		return insoddisfatti;
	}
	
	// simulazione:
	
	public void run() {
		// preparazione mondo
		this.clientiTot=0;
		this.insoddisfatti=0;
		this.soddisfatti=0;
		this.nTav10=this.tavoli10Posti;
		this.nTav8=this.tavoli8Posti;
		this.nTav6=this.tavoli6Posti;
		this.nTav4=this.tavoli4Posti;
		this.nTavTot=this.nTav10+this.nTav8+this.nTav6+this.nTav4;
		
		//coda
		this.coda.clear();
		LocalTime oraArrivoClienti=this.orarioApertura;
		int i=0;
		do {
			int numeroPersone=this.calcolaRand();
			double tolleranza=this.calcolaRand()/10.0;
			Event e= new Event(oraArrivoClienti,numeroPersone,EventType.ARRIVO_GRUPPI_CLIENTI,tolleranza);
			coda.add(e);
			Duration d= Duration.of(this.calcolaRand(), ChronoUnit.MINUTES);
			oraArrivoClienti=oraArrivoClienti.plus(d);
			i++;
		} while(i<2000);
		
		// esecuzione simulazione
		while(!coda.isEmpty()) {
			Event e=coda.poll();
			System.out.println(e);
			
			if(e.getTavoloOccupato()>0) {
				System.out.println(" tavolo occupato: "+e.getTavoloOccupato());
			}
			this.processa(e);
		}
	}
	
	
	private void processa(Event e) {
		
		switch(e.getType()) {
		
		case ARRIVO_GRUPPI_CLIENTI:
				
				if(e.getNumPersone()<=4 && (this.nTav4>0 || this.nTav6>0) && ((((float)e.getNumPersone()/(float)4.0)>=0.5) || ((float)e.getNumPersone()/(float)6.0)>=0.5)) {
					//tavolo disponibile per loro
					if(this.nTav4>0) {
						this.clientiSeduti(4,e);
					}
					else if(this.nTav6>0) {
						this.clientiSeduti(6,e);
					}
				}
				else if(e.getNumPersone()<=6 && (this.nTav6>0 || this.nTav8>0) && ((((float)e.getNumPersone()/(float)6.0)>=0.5) || ((float)e.getNumPersone()/(float)8.0)>=0.5)) {
					//tavolo disponibile per loro
					if(this.nTav6>0) {
						this.clientiSeduti(6,e);
					}
					else if(this.nTav8>0) {
						this.clientiSeduti(8,e);
					}
				}
				else if(e.getNumPersone()<=8 && (this.nTav8>0 || this.nTav10>0) && ((((float)e.getNumPersone()/(float)8.0)>=0.5) || ((float)e.getNumPersone()/(float)10.0)>=0.5)) {
					//tavolo disponibile per loro
					if(this.nTav8>0) {
						this.clientiSeduti(8,e);
					}
					else if(this.nTav10>0) {
						this.clientiSeduti(10,e);
					}
				}
				else if(e.getNumPersone()<=10 && this.nTav10>0 && ((float)e.getNumPersone()/(float)10.0)>=0.5) {
					//tavolo disponibile per loro
					this.clientiSeduti(10,e);
				}
				
				else {
					double num= Math.random();
					if(num<e.getTolleranza()) {
						//cliente al bancone ma soddisfatto
						this.clientiTot++;
						this.soddisfatti++;
					} else {
						//cliente non soddisfatto
						this.clientiTot++;
						this.insoddisfatti++;
					}
				}
				
			 
			break;
		case TAVOLO_LIBERATO:
			
			int tavoloLiberato=e.getTavoloOccupato();
			if(tavoloLiberato==4) {
				this.nTav4++;
				this.nTavTot++;
			}
			else if(tavoloLiberato==6) {
				this.nTav6++;
				this.nTavTot++;
			}
			else if(tavoloLiberato==8) {
				this.nTav8++;
				this.nTavTot++;
			}
			else if(tavoloLiberato==10) {
				this.nTav10++;
				this.nTavTot++;
			}
			break;
		
		}
	}
	
	public int calcolaRand() {
		int result=-1;
		double num=Math.random(); // [0,1)
		if(num<=0.1) {
			result=1;
		}
		else if(num<=0.2) {
			result=2;
		}
		else if(num<=0.3) {
			result=3;
		}
		else if(num<=0.4) {
			result=4;
		}
		else if(num<=0.5) {
			result=5;
		}
		else if(num<=0.6) {
			result=6;
		}
		else if(num<=0.7) {
			result=7;
		}
		else if(num<=0.8) {
			result=8;
		}
		else if(num<=0.9) {
			result=9;
		}
		else if(num<=1) {
			result=10;
		}
		return result;
	}
	
	
	
	public void clientiSeduti(int tav, Event e) {

		if(tav==4) {
			this.nTav4--;
		}
		else if(tav==6) {
			this.nTav6--;
		}
		else if(tav==8) {
			this.nTav8--;
		}
		else if(tav==10) {
			this.nTav10--;
		}
		this.nTavTot--;
		this.clientiTot=this.clientiTot+e.getNumPersone();
		this.soddisfatti=this.soddisfatti+e.getNumPersone();
		Random r= new Random();
		Duration permanenza=Duration.of(r.nextInt(61)+60, ChronoUnit.MINUTES);
		Event nuovo= new Event(e.getTime().plus(permanenza),e.getNumPersone(),EventType.TAVOLO_LIBERATO,e.getTolleranza());
		nuovo.setTavoloOccupato(tav);
		this.coda.add(nuovo);
	}
	
	
}
