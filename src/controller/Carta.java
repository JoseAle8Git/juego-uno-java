package controller;

public class Carta {
	
	private TipoCarta tipo;
	private Colores color;
	private int valor;
	
	public Carta() {
		
	}
	public Carta(TipoCarta tipo, Colores color, int valor) {
		this.tipo = tipo;
		this.color = color;
		this.valor = valor;
	}
	// Getters y setters
	public TipoCarta getTipo() {
		return tipo;
	}
	public void setTipo(TipoCarta tipo) {
		this.tipo = tipo;
	}
	public Colores getColor() {
		return color;
	}
	public void setColor(Colores color) {
		this.color = color;
	}
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}
	// Indica si una carta se puede jugar
	public boolean esJugable(Carta carta, Colores color) {
		return this.color == color || this.valor == carta.getValor() && this.tipo == TipoCarta.NUMERICA || this.tipo == carta.getTipo() && this.tipo != TipoCarta.NUMERICA || esComodin();
	}
	// Indica si la carta es un comodin
	public boolean esComodin() {
		return this.tipo == TipoCarta.COMODIN || this.tipo == TipoCarta.COMODIN_TOMA_CUATRO;
	}
	// Información de la cartas
	@Override
	public String toString() {
		return tipo + " / " + color + " / " + valor;
	}

}
