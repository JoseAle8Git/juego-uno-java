package controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javazoom.jl.player.advanced.AdvancedPlayer;

public class Reproductor {
	
	private AdvancedPlayer player;
	private String archivo;
	private Thread hilo;
	private FileInputStream entradaSinBuffer;
	private BufferedInputStream entradaConBuffer;
	private int totalArchivo;
	private int posicionPausa;
	private boolean reproduciendo = false;
	
	public Reproductor(String archivo) {
		this.archivo = archivo;
	}
	// Reproduce el archivo MP3 o lanza una excepción
	public void reproducir() {
		if(reproduciendo) return;
		reproduciendo = true;
		try {
			entradaSinBuffer = new FileInputStream(archivo);
			entradaConBuffer = new BufferedInputStream(entradaSinBuffer);
			player = new AdvancedPlayer(entradaConBuffer);
			totalArchivo = entradaSinBuffer.available();
			hilo = new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						do {
							entradaSinBuffer = new FileInputStream(archivo);
							entradaConBuffer = new BufferedInputStream(entradaSinBuffer);
							player = new AdvancedPlayer(entradaConBuffer);
							player.play();
						} while(reproduciendo);
					} catch(Exception ex) {
						ex.printStackTrace();
						System.out.println("Fallo en el hilo dentro del método reproducir!!!");
					}
				}
			});
			hilo.start();
		} catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Fallo en el método reproducir!!!");
		}
	}
	// Pausa la reproducción del archivo MP3
	public void pausar() {
		try {
			if(entradaConBuffer != null) posicionPausa = entradaConBuffer.available();
			reproduciendo = false;
			if(player != null) player.close();
		} catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Fallo en el método pausar!!!");
		}
	}
	// Reanuda la reproduccion del archivo MP3
	public void reanudar() {
		if(reproduciendo) return;
		reproduciendo = true;
		hilo = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				boolean pausa = true;
				try {
					do {
						entradaSinBuffer = new FileInputStream(archivo);
						entradaConBuffer = new BufferedInputStream(entradaSinBuffer);
						player = new AdvancedPlayer(entradaConBuffer);
						if(pausa) {
							entradaSinBuffer.skip(totalArchivo - posicionPausa);
							pausa = false;
						}
						player.play();
						if(pausa) posicionPausa = 0;
					} while(reproduciendo);
				} catch(Exception ex) {
					ex.printStackTrace();
					System.out.println("Fallo en el método reanudar!!!");
				}
			}
		});
		hilo.start();
	}

}
