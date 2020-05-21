package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalTime;

public class Event implements Comparable<Event>{
	
	public enum EventType{
		ARRIVO_GRUPPI_CLIENTI,TAVOLO_LIBERATO
	}
	
	private LocalTime time; // istante di tempo quando arrivano nuovi clienti
	public int numPersone; // numero di nuovi clienti nel gruppo
	private EventType type;
	private double tolleranza;
	
	private int tavoloOccupato=-1;
	
	public Event(LocalTime time, int numPersone, EventType type, double tolleranza) {
		super();
		this.time = time;
		this.numPersone = numPersone;
		this.type = type;
		this.tolleranza = tolleranza;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public int getNumPersone() {
		return numPersone;
	}

	public void setNumPersone(int numPersone) {
		this.numPersone = numPersone;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public double getTolleranza() {
		return tolleranza;
	}

	public void setTolleranza(double tolleranza) {
		this.tolleranza = tolleranza;
	}

	@Override
	public int compareTo(Event o) {
		return this.time.compareTo(o.getTime());
	}

	@Override
	public String toString() {
		return time + ", numPersone=" + numPersone +  ", tolleranza="
				+ tolleranza + " "+type+"]";
	}

	public int getTavoloOccupato() {
		return tavoloOccupato;
	}

	public void setTavoloOccupato(int tavoloOccupato) {
		this.tavoloOccupato = tavoloOccupato;
	}
	
	
	
	
	
}
