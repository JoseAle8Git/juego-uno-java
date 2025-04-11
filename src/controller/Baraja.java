package controller;

import java.util.ArrayList;
import java.util.Collections;

public class Baraja {
	
	private ArrayList<Carta> mazoRobar;
	private ArrayList<Carta> mazoDescartes;
	
	public Baraja() {
		this.mazoRobar = new ArrayList<Carta>();
		this.mazoDescartes = new ArrayList<Carta>();
		inicializarBaraja();
		barajar();
	}
	// Getters
	public ArrayList<Carta> getMazoRobar() {
		return mazoRobar;
	}
	public ArrayList<Carta> getMazoDescartes() {
		return mazoDescartes;
	}
	// Crea el mazo de cartas y lo guarda en mazoRobar
	public void inicializarBaraja() {
		Colores[] colores = {Colores.ROJO, Colores.AZUL, Colores.AMARILLO, Colores.VERDE};
        for (Colores color : colores) {
            mazoRobar.add(new Carta(TipoCarta.NUMERICA, color, 0));
            for (int i = 1; i <= 9; i++) {
                mazoRobar.add(new Carta(TipoCarta.NUMERICA, color, i));
                mazoRobar.add(new Carta(TipoCarta.NUMERICA, color, i));
            }
            for (int i = 0; i < 2; i++) {
                mazoRobar.add(new Carta(TipoCarta.SALTO_TURNO, color, 20));
                mazoRobar.add(new Carta(TipoCarta.REVERSA, color, 20));
                mazoRobar.add(new Carta(TipoCarta.TOMA_DOS, color, 20));
            }
        }
        for (int i = 0; i < 4; i++) {
            mazoRobar.add(new Carta(TipoCarta.COMODIN, Colores.SIN_COLOR, 50));
            mazoRobar.add(new Carta(TipoCarta.COMODIN_TOMA_CUATRO, Colores.SIN_COLOR, 50));
        }
	}
	// Baraja las cartas del mazoRobar
	public void barajar() {
		Collections.shuffle(mazoRobar);
	}
	// Reconstruye el mazoRobar
	public void recontruirMazo() {
		if(mazoDescartes.size() > 1) {
			Carta ultimaCarta = mazoDescartes.remove(mazoDescartes.size() - 1);
			mazoRobar.addAll(mazoDescartes);
			mazoDescartes.clear();
			mazoDescartes.add(ultimaCarta);
			barajar();
		}
	}
	// Roba una carta de mazoRobar
	public Carta robarCarta() {
		if(mazoRobar.isEmpty()) recontruirMazo();
		return mazoRobar.isEmpty() ? null : mazoRobar.remove(0);
	}
	// Agrega una carta al mazoDescartes
	public void agregarAlDescarte(Carta carta) {
		mazoDescartes.add(carta);
	}
	// Obtiene la última carta de mazoDescartes
	public Carta obtenerUltimaCartaDeDescartes() {
		return mazoDescartes.isEmpty() ? null : mazoDescartes.get(mazoDescartes.size() - 1);
	}

}
