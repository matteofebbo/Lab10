package it.polito.tdp.bar.model;

public class TestSimulator {

	public static void main(String[] args) {
		
		Simulator s= new Simulator();
		s.run();
		
		int clienti=s.getClientiTot();
		int soddisfatti=s.getSoddisfatti();
		int insoddisfatti=s.getInsoddisfatti();
		double num=s.calcolaRand();
		System.out.println(String.format("Arrivati %d clienti, %d soddisfatti, %d insoddisfatti\n",clienti,soddisfatti,insoddisfatti));
	}

}
