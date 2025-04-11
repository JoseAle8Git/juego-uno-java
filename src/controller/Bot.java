package controller;

import java.util.Random;

import javax.swing.JOptionPane;

public class Bot extends Jugador {
	
	public Bot() {
		
	}
	public Bot(String nombre) {
		super(nombre);
	}
	// Toma de desicion del bot
	public void tomarDesicion(Baraja baraja, Juego juego) {
		if(getMano().size() == 1) {
			JOptionPane.showMessageDialog(null, getNombre() + " ha dicho UNO!!!");
		}
		for(int i = 0; i < getMano().size(); i++) {
			Carta carta = getMano().get(i);
			if(carta.esJugable(baraja.obtenerUltimaCartaDeDescartes(), juego.getColorActual())) {
				jugarCarta(i, baraja, juego);
				if(carta.esComodin()) seleccionarColorAleatorio(juego);
				return;
			}
		}
		recibirCarta(baraja.robarCarta());
		JOptionPane.showMessageDialog(null, getNombre() + " ha robado una carta!!!");
		Carta nuevaCarta = getMano().get(getMano().size() - 1);
		if(nuevaCarta.esJugable(baraja.obtenerUltimaCartaDeDescartes(), juego.getColorActual())) {
			jugarCarta(getMano().size() - 1, baraja, juego);
			if(nuevaCarta.esComodin()) seleccionarColorAleatorio(juego);
			return;
		}
		JOptionPane.showMessageDialog(null, "El bot no ha podido jugar ninguna carta");
		juego.siguienteTurno();
	}
	// Se selecciona un color eleatorio
	public void seleccionarColorAleatorio(Juego juego) {
		Colores[] colores = {
			Colores.AZUL, Colores.AMARILLO, Colores.ROJO, Colores.VERDE	
		};
		juego.setColorActual(colores[new Random().nextInt(colores.length)]);
	}

}
