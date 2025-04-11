package controller;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Jugador {
	
	private String nombre;
	private ArrayList<Carta> mano;
	private int puntos;
	private boolean dijoUno;
	private boolean haRobadoCarta;
	
	public Jugador() {
		
	}
	public Jugador(String nombre) {
		this.nombre = nombre;
		this.mano = new ArrayList<Carta>();
		this.puntos = 0;
		this.dijoUno = false;
		this.haRobadoCarta = false;
	}
	// Getters y setters
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public ArrayList<Carta> getMano() {
		return mano;
	}
	public void setMano(ArrayList<Carta> mano) {
		this.mano = mano;
	}
	public int getPuntos() {
		return puntos;
	}
	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
	public boolean isDijoUno() {
		return dijoUno;
	}
	public void setDijoUno(boolean dijoUno) {
		this.dijoUno = dijoUno;
	}
	public boolean isHaRobadoCarta() {
		return haRobadoCarta;
	}
	public void setHaRobadoCarta(boolean haJugadoCarta) {
		this.haRobadoCarta = haJugadoCarta;
	}
	// Suma los puntos al jugador
	public void sumarPuntos(int puntosGanados) {
		this.puntos += puntosGanados;
	}
	// Lógica para jugar una carta
	public boolean jugarCarta(int indice, Baraja baraja, Juego juego) {
		if(indice >= 0 || indice < mano.size()) {
			Carta carta = mano.get(indice);
			if(carta.esJugable(baraja.obtenerUltimaCartaDeDescartes(), juego.getColorActual())) {
				JOptionPane.showMessageDialog(null, juego.getJugadores().get(juego.getTurnoActual()).getNombre() + " ha jugado esta carta: " + carta.toString());
				mano.remove(indice);
				baraja.agregarAlDescarte(carta);
				juego.setColorActual(baraja.obtenerUltimaCartaDeDescartes().getColor());
				juego.aplicarEfecto(carta);
				juego.siguienteTurno();
				return true;
			} else JOptionPane.showMessageDialog(null, "No se puede jugar esta carta: " + carta.toString());
		}
		return false;
	}
	// Recibe una carta mano
	public void recibirCarta(Carta carta) {
		mano.add(carta);
	}
	// Indica si una mano es jugable
	public boolean manoJugable(Juego juego) {
		for(Carta c : this.mano) {
			if(c.esJugable(juego.getBaraja().obtenerUltimaCartaDeDescartes(), juego.getColorActual())) return true;
		}
		return false;
	}

}
