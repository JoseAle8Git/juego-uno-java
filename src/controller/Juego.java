package controller;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Juego {
	
	private ArrayList<Jugador> jugadores;
	private Baraja baraja;
	private Colores colorActual;
	private boolean sentidoHorario;
	private int turnoActual;
	private int puntosMaximos;
	private int numRonda;
	
	public Juego() {
		
	}
	public Juego(int numBots) {
		this.puntosMaximos = 500;
		this.jugadores = new ArrayList<Jugador>();
		this.baraja = new Baraja();
		this.turnoActual = 0;
		this.sentidoHorario = true;
		this.numRonda = 1;
		inicializarNombreJugador(numBots);
		repartirCartasIniciales();
	}
	// Getters y setters
	public ArrayList<Jugador> getJugadores() {
		return jugadores;
	}
	public void setJugadores(ArrayList<Jugador> jugadores) {
		this.jugadores = jugadores;
	}
	public Baraja getBaraja() {
		return baraja;
	}
	public void setBaraja(Baraja baraja) {
		this.baraja = baraja;
	}
	public Colores getColorActual() {
		return colorActual;
	}
	public void setColorActual(Colores colorActual) {
		this.colorActual = colorActual;
	}
	public boolean isSentidoHorario() {
		return sentidoHorario;
	}
	public void setSentidoHorario(boolean sentidoHorario) {
		this.sentidoHorario = sentidoHorario;
	}
	public int getTurnoActual() {
		return turnoActual;
	}
	public void setTurnoActual(int turnoActual) {
		this.turnoActual = turnoActual;
	}
	public int getPuntosMaximos() {
		return puntosMaximos;
	}
	public void setPuntosMaximos(int puntosMaximos) {
		this.puntosMaximos = puntosMaximos;
	}
	public int getNumRonda() {
		return numRonda;
	}
	public void setNumRonda(int numRonda) {
		this.numRonda = numRonda;
	}
	// Pide el nombre al usuario
	public void inicializarNombreJugador(int numBots) {
		String nombreJugador;
		do {
			nombreJugador = JOptionPane.showInputDialog("Introduce tu nombre:");
		} while(nombreJugador == null || nombreJugador.trim().isEmpty());
		jugadores.add(new Jugador(nombreJugador));
		for (int i = 1; i <= numBots; i++) {
			jugadores.add(new Bot("Bot " + i));
		}
	}
	// Reparte cartas a los jugadores y pone la primera carta en el mazoDescartes
	public void repartirCartasIniciales() {
		for(Jugador j : jugadores) {
			for(int i = 0; i < 7; i ++) {
				j.recibirCarta(baraja.robarCarta());
			}
		}
		Carta primeraCarta;
		do {
			primeraCarta = baraja.robarCarta();
			if(primeraCarta.esComodin()) baraja.getMazoRobar().add(primeraCarta);
		} while(primeraCarta.esComodin());
		baraja.agregarAlDescarte(primeraCarta);
		this.colorActual = primeraCarta.getColor();
	}
	// Pasa al siguiente tunro
	public void siguienteTurno() {
		this.turnoActual = (turnoActual + (sentidoHorario ? 1 : -1) + jugadores.size()) % jugadores.size();
		System.out.println(turnoActual);
	}
	// Aplica el efecto de las cartas
	public void aplicarEfecto(Carta carta) {
		switch(carta.getTipo()) {
			case TOMA_DOS -> {
				siguienteTurno();
				jugadores.get(turnoActual).recibirCarta(baraja.robarCarta());
				jugadores.get(turnoActual).recibirCarta(baraja.robarCarta());
			}
			case REVERSA -> this.sentidoHorario = !sentidoHorario;
			case SALTO_TURNO -> {
				siguienteTurno();
			}
			case COMODIN, COMODIN_TOMA_CUATRO -> {
				if(getJugadores().get(0) == getJugadores().get(getTurnoActual())) seleccionarNuevoColor();
				if(carta.getTipo() == TipoCarta.COMODIN_TOMA_CUATRO) {
					siguienteTurno();
					for(int i = 0; i < 4; i++) {
						jugadores.get(turnoActual).recibirCarta(baraja.robarCarta());
					}
				}
			}
		}
	}
	// Selecciona el colorActual
	public void seleccionarNuevoColor() {
		Colores[] colores = {
			Colores.AMARILLO, Colores.AZUL, Colores.ROJO, Colores.VERDE
		};
		Colores nuevoColor = (Colores) JOptionPane.showInputDialog(null, "Seleccionar un color: ", "Cambio de color", JOptionPane.QUESTION_MESSAGE, null, colores, colores[0]);
		if(nuevoColor != null) {
			setColorActual(nuevoColor);
		}
	}
	// Comprube si hay un jugador sin cartas
	public Jugador sinCartas() {
		Jugador jugador = null;
		for(Jugador j : jugadores) {
			if(j.getMano().isEmpty()) {
				jugador = j;
				return jugador;
			}
		}
		return jugador;
	}
	// Suma los puntos al ganador
	public void registrarPuntos(Jugador ganador) {
		int puntos = 0;
		for(Jugador j : jugadores) {
			if(!j.equals(ganador)) {
				for(Carta c : j.getMano()) {
					puntos += c.getValor();
				}
			}
		}
		ganador.sumarPuntos(puntos);
	}
	public boolean rondaTerminada() {
		for(Jugador j : jugadores) {
			if(j.getMano().size() == 0) {
				JOptionPane.showMessageDialog(null, "Ronda " + this.numRonda + " terminada, " + j.getNombre() + " se ha quedado sin cartas!!!");
				this.numRonda++;
				return true;
			}
		}
		return false;
	}
	// Indica si una partida ha terminado
	public boolean partidaTerminada() {
		for(Jugador j : jugadores) {
			if(j.getPuntos() >= getPuntosMaximos()) {
				JOptionPane.showMessageDialog(null, "El jugador " + j.getNombre() + " ha ganado la partida!!!");
				return true;
			}
		}
		return false;
	}
	// Reinicia una nueva ronda
	public void reiniciarRonda() {
		getJugadores().get(0).setDijoUno(false);
		for(Jugador j : jugadores) {
			baraja.getMazoDescartes().addAll(j.getMano());
			j.getMano().clear();
		}
		baraja.recontruirMazo();
		repartirCartasIniciales();
	}

}
