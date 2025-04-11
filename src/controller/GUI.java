package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GUI {

	private JFrame ventana;
	private Juego juego;
	private JPanel panelMenu;
	private JLabel titulo;
	private JPanel panelJuego;
	private JPanel panelBot1;
	private JPanel panelBot2;
	private JPanel panelBot3;
	private JLabel[] infoBots;
	private JPanel panelInferior;
	private JPanel panelCartas;
	private JButton btnRobar;
	private JButton btnTerminarTurno;
	private JButton btnUNO;
	private JPanel panelCentral;
	private JLabel mazoDescartar;
	private JPanel panelInfoPuntos;
	private JLabel puntuacion;
	private int numBots;
	private Reproductor reproductor;
	private JMenuBar menuDesplegable;
	private JMenu menuOpciones;
	private JMenuItem pausa;
	private JMenuItem reanudar;
	private JMenuItem opciones;

	public GUI() {
		ventana = new JFrame("UNO");
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setSize(800, 600);
		ventana.setLocationRelativeTo(null);
		ventana.setLayout(new BorderLayout());
		menuDesplegable = new JMenuBar();
		pausa = new JMenuItem("Pausar canción");
		reanudar = new JMenuItem("Reanudar canción");
		ventana.setJMenuBar(menuDesplegable);
		reproductor = new Reproductor("Canciones/Retro.mp3");
		reproductor.reproducir();
		menuOpciones = new JMenu("Opciones");
		opciones = new JMenuItem("Salir al menú");
		menuDesplegable.add(menuOpciones);
		menuOpciones.add(opciones);
		menuOpciones.add(pausa);
		menuOpciones.add(reanudar);
		ventana.getContentPane().setBackground(Color.CYAN);
		mostrarMenu();
	}
	// Muestra la pantalla del menú
	private void mostrarMenu() {
		opciones.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mostrarMenu();
			}
		});
		pausa.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				reproductor.pausar();
			}
		});
		reanudar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				reproductor.reanudar();
			}
		});
		ventana.getContentPane().removeAll();
		ventana.repaint();
		panelMenu = new JPanel();
		panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
		panelMenu.setBackground(Color.CYAN);
		titulo = new JLabel("UNO", SwingConstants.CENTER);
		titulo.setFont(new Font("Arial", Font.BOLD, 200));
		ventana.add(titulo, BorderLayout.NORTH);
		JButton btnJugar = new JButton("Jugar partida");
		JButton btnInstrucciones = new JButton("Reglas");
		JButton btnSalir = new JButton("Salir");
		btnJugar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				configurarPartida();
			}
		});
		btnInstrucciones.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mostrarReglas();
			}
		});
		btnSalir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		btnJugar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnInstrucciones.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelMenu.add(Box.createVerticalGlue());
		panelMenu.add(btnJugar);
		panelMenu.add(Box.createRigidArea(new Dimension(0, 20)));
		panelMenu.add(btnInstrucciones);
		panelMenu.add(Box.createRigidArea(new Dimension(0, 20)));
		panelMenu.add(btnSalir);
		panelMenu.add(Box.createVerticalGlue());
		ventana.add(panelMenu, BorderLayout.CENTER);
		ventana.setVisible(true);
	}
	// El usuario introduce contra cuántos bots juegar
	private void configurarPartida() {
		Integer[] opciones = {
			1, 2, 3	
		};
		Integer seleccion = (Integer) JOptionPane.showInputDialog(panelJuego, "Selecciona el número de bots (1-3):", "Configurar de la partida", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
		if(seleccion != null) {
			numBots = seleccion;
			infoBots = new JLabel[numBots];
			JOptionPane.showMessageDialog(ventana, "Partida contra " + numBots + " bots");
			juego = new Juego(numBots);
			mostrarTablero();
		}
	}
	private void mostrarTablero() {
		ventana.getContentPane().removeAll();
		ventana.repaint();
		panelJuego = new JPanel(new BorderLayout());
		for(int i = 0; i < numBots; i++) {
			infoBots[i] = new JLabel("Bot " + (i + 1) + ": " + juego.getJugadores().get(i + 1).getMano().size() + " cartas restantes | Puntos: " + juego.getJugadores().get(i + 1).getPuntos());
		}
		if(numBots == 1) {
			panelBot1 = new JPanel(new GridBagLayout());
			panelBot1.add(infoBots[0], new GridBagConstraints());
			panelBot1.setBackground(Color.PINK);
			panelJuego.add(panelBot1, BorderLayout.WEST);
		} else if(numBots == 2) {
			panelBot1 = new JPanel(new GridBagLayout());
			panelBot1.add(infoBots[0], new GridBagConstraints());
			panelBot1.setBackground(Color.PINK);
			panelJuego.add(panelBot1, BorderLayout.WEST);
			panelBot2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
			panelBot2.add(infoBots[1]);
			panelBot2.setBackground(Color.MAGENTA);
			panelJuego.add(panelBot2, BorderLayout.NORTH);
		} else if(numBots == 3) {
			panelBot1 = new JPanel(new GridBagLayout());
			panelBot1.add(infoBots[0], new GridBagConstraints());
			panelBot1.setBackground(Color.PINK);
			panelJuego.add(panelBot1, BorderLayout.WEST);
			panelBot2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
			panelBot2.add(infoBots[1]);
			panelBot2.setBackground(Color.MAGENTA);
			panelJuego.add(panelBot2, BorderLayout.NORTH);
			panelBot3 = new JPanel(new GridBagLayout());
			panelBot3.add(infoBots[2], new GridBagConstraints());
			panelBot3.setBackground(Color.LIGHT_GRAY);
			panelJuego.add(panelBot3, BorderLayout.EAST);
		}
		panelCentral = new JPanel();
		panelCentral.setLayout(new GridBagLayout());
		mazoDescartar = new JLabel();
		mazoDescartar.setText(juego.getBaraja().obtenerUltimaCartaDeDescartes().toString());
		panelCentral.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelCentral.setAlignmentY(Component.CENTER_ALIGNMENT);
		
		panelCentral.add(mazoDescartar, new GridBagConstraints());
		actualizarBarajaDescartes();
		panelJuego.add(panelCentral, BorderLayout.CENTER);
		panelInferior = new JPanel(new BorderLayout());
		panelInfoPuntos = new JPanel(new FlowLayout());
		panelInfoPuntos.setAlignmentX(Component.CENTER_ALIGNMENT);
		puntuacion = new JLabel(juego.getJugadores().get(0).getNombre() + " | Puntos: " + juego.getJugadores().get(0).getPuntos());
		panelInfoPuntos.add(puntuacion);
		panelInferior.add(panelInfoPuntos, BorderLayout.NORTH);
		btnRobar = new JButton("Robar carta");
		btnRobar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(juego.getJugadores().get(0) == juego.getJugadores().get(juego.getTurnoActual())) {
					if(!juego.getJugadores().get(0).manoJugable(juego)) {
						if(!juego.getJugadores().get(0).isHaRobadoCarta()) {
							juego.getJugadores().get(0).setHaRobadoCarta(true);
							juego.getJugadores().get(0).recibirCarta(juego.getBaraja().robarCarta());
							actualizarMano();
						} else JOptionPane.showMessageDialog(ventana, "No puedes robar una carta porque ya has robado una carta!!!");
					} else JOptionPane.showMessageDialog(ventana, "No puedes robar una carta porque tienes una carta jugable!!!");
				} else JOptionPane.showMessageDialog(ventana, "No es su turno!!!");
			}
		});
		panelInferior.add(btnRobar, BorderLayout.EAST);
		btnTerminarTurno = new JButton("Terminar turno");
		btnTerminarTurno.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(juego.getJugadores().get(0) == juego.getJugadores().get(juego.getTurnoActual())) {
					if(!juego.getJugadores().get(0).manoJugable(juego)) {
						if(juego.getJugadores().get(0).isHaRobadoCarta()) {
							juego.siguienteTurno();
							verificarPartida();
							jueganBots();
						}
						else JOptionPane.showMessageDialog(ventana, "No puedes terminar tu turno, aún prueba a robar una carta!!!");
					} else JOptionPane.showMessageDialog(ventana, "No puedes terminar tu turno porque tienes una carta jugable!!!");
				} else JOptionPane.showMessageDialog(ventana, "No es su turno!!!");
			}
		});
		panelInferior.add(btnTerminarTurno, BorderLayout.SOUTH);
		btnUNO = new JButton("¡UNO!");
		btnUNO.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(juego.getJugadores().get(0) == juego.getJugadores().get(juego.getTurnoActual())) {
					if(juego.getJugadores().get(0).getMano().size() == 1) {
						JOptionPane.showMessageDialog(ventana, "¡UNO!");
						juego.getJugadores().get(0).setDijoUno(true);
					} else {
						JOptionPane.showMessageDialog(ventana, "Has dicho UNO antes de tiempo!!! Roba dos cartas");
						juego.getJugadores().get(0).recibirCarta(juego.getBaraja().robarCarta());
						juego.getJugadores().get(0).recibirCarta(juego.getBaraja().robarCarta());
						actualizarMano();
						juego.siguienteTurno();
						jueganBots();
					}
				} else JOptionPane.showMessageDialog(ventana, "No es su turno!!!");
			}
		});
		panelInferior.add(btnUNO, BorderLayout.WEST);
		panelCartas = new JPanel();
		actualizarMano();
		panelInferior.add(panelCartas, BorderLayout.CENTER);
		panelJuego.add(panelInferior, BorderLayout.SOUTH);
		ventana.add(panelJuego);
		ventana.revalidate();
		ventana.repaint();
		ventana.setVisible(true);
	}
	// Pinta el panelCentral
	private void actualizarBarajaDescartes() {
		panelCentral.removeAll();
		String nombreArchivo = construirNombreArchivoImagen(juego.getBaraja().obtenerUltimaCartaDeDescartes());
		ImageIcon icono = new ImageIcon("imagenes/" + nombreArchivo);
		mazoDescartar = new JLabel(icono);
		mazoDescartar.setPreferredSize(new Dimension(100, 150));
		panelCentral.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelCentral.setAlignmentY(Component.CENTER_ALIGNMENT);
		panelCentral.add(mazoDescartar, new GridBagConstraints());
		panelCentral.setBackground(obtenerColor(juego.getColorActual()));
		panelCentral.revalidate();
		panelCentral.repaint();
	}
	// Actualiza la información de los bots
	private void actualizarInfoBots() {
		for(int i = 0; i < numBots; i++) {
			infoBots[i].setText("Bot " + (i + 1) + ": " + juego.getJugadores().get(i + 1).getMano().size() + " cartas restantes | Puntos: " + juego.getJugadores().get(i + 1).getPuntos());
		}
	}
	// Actualiza los botones que representan las cartas
	public void actualizarMano() {
		panelCartas.removeAll();
		int cartasTotales = juego.getJugadores().get(0).getMano().size();
		int cartasPorFila = 10;
		int filas = (int) Math.ceil((double) cartasTotales / cartasPorFila);
		panelCartas.setLayout(new GridLayout(filas, cartasPorFila, 10, 10));
		for(int i = 0; i < juego.getJugadores().get(0).getMano().size(); i++) {
			Carta carta = juego.getJugadores().get(0).getMano().get(i);
			String nombreArchivo = construirNombreArchivoImagen(carta);
			ImageIcon icono = new ImageIcon("imagenes/" + nombreArchivo);
			JButton cartaBtn = new JButton(icono);
			cartaBtn.setPreferredSize(new Dimension(50, 100));
			int indice = i;
			cartaBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(juego.getJugadores().get(juego.getTurnoActual()) == juego.getJugadores().get(0)) jugarCarta(indice);
					else JOptionPane.showMessageDialog(ventana, "No es su turno!!!");
				}
			});
			panelCartas.add(cartaBtn);
		}
		panelCartas.revalidate();
		panelCartas.repaint();
	}
	private String construirNombreArchivoImagen(Carta carta) {
		String color = carta.getColor().toString().toLowerCase();
		String tipo = carta.getTipo().toString().toLowerCase();
		if(carta.getTipo() == TipoCarta.NUMERICA) return color + "_" + carta.getValor() + ".png";
		else if(carta.esComodin()) return tipo + ".png";
		else return color + "_" + tipo + ".png";
	}
	// Juega una carta
	public void jugarCarta(int indice) {
		if(juego.getJugadores().get(0).jugarCarta(indice, juego.getBaraja(), juego)) {
			actualizarBarajaDescartes();
			actualizarInfoBots();
			System.out.println(juego.getJugadores().get(0).getMano().size());
			if(!juego.getJugadores().get(0).isDijoUno() && juego.getJugadores().get(0).getMano().size() < 1) {
				JOptionPane.showMessageDialog(ventana, "Por no decir UNO toma dos cartas!!!");
				for(int i = 0; i < 2; i++) {
					juego.getJugadores().get(0).recibirCarta(juego.getBaraja().robarCarta());
				}
			}
			actualizarMano();
			verificarPartida();
			jueganBots(); // Mirar esto, puede ser la razón de que el bot termine sus cartas cunado se ha terminado la partida estando en el menú
		}
	}
	// Verifica el estado de la partida
	public void verificarPartida() {
		if(juego.rondaTerminada()) {
			juego.registrarPuntos(juego.sinCartas());
			if(juego.partidaTerminada()) {
				juego.setTurnoActual(0);
				mostrarMenu();
			}
			else {
				juego.reiniciarRonda();
				actualizarBarajaDescartes();
				actualizarPuntosJugador();
				actualizarInfoBots();
				actualizarMano();
			}
		}
	}
	// Lógica para que jueguen los bots
	public void jueganBots() {
		juego.getJugadores().get(0).setDijoUno(false);
		juego.getJugadores().get(0).setHaRobadoCarta(false);
		if(juego.getTurnoActual() > 0) {
			do {
				if(juego.getJugadores().get(juego.getTurnoActual()) instanceof Bot) {
					((Bot) juego.getJugadores().get(juego.getTurnoActual())).tomarDesicion(juego.getBaraja(), juego);
				}
				actualizarBarajaDescartes();
				actualizarInfoBots();
				actualizarMano();
				verificarPartida();
			} while(juego.getTurnoActual() > 0);
		}
	}
	private void actualizarPuntosJugador() {
		panelInfoPuntos.removeAll();
		puntuacion.setText(juego.getJugadores().get(0).getNombre() + " | Puntos: " + juego.getJugadores().get(0).getPuntos());
		panelInfoPuntos.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelInfoPuntos.add(puntuacion);
		panelInfoPuntos.repaint();
	}
	// Devuelve el color correspondiente
	private Color obtenerColor(Colores color) {
		return switch (color) {
			case ROJO -> Color.RED;
			case AZUL -> Color.BLUE;
			case VERDE -> Color.GREEN;
			case AMARILLO -> Color.YELLOW;
			default -> Color.LIGHT_GRAY;
		};
	}
	// Enseña las reglas del juego
	public void mostrarReglas() {
		JOptionPane.showMessageDialog(ventana, "Reglas del UNO:\n"
				+ "- Cada jugador empieza con 7 cartas\n"
				+ "- El juego funciona por turnos y los turnos se terminan jugando una carta o robando una carta si no se pueden jugar cartas.\n"
				+ "- Solo se pueden jugar cartas del mismo color, valor o si es un comodín se puede jugar cuando sea.\n"
				+ "- La carta salto turno le salta el turno al siguiente jugador.\n"
				+ "- La  carta reversa cambia el sentido de los turnos del juego.\n"
				+ "- La carta toma dos hace el siguiente jugador tenga robar dos cartas y pierde el turno.\n"
				+ "- La carta comodin te permite cambiar el color del juego.\n"
				+ "- La carta comodin toma cuatro te permite cambiar el color del juego, hace que el siguiente jugador tenga que tomar cuatro cartas y pierde el turno.\n"
				+ "- Para poder quedarte sin cartas se debe decir UNO cuando a un jugador le falte una carta en su mano.\n"
				+ "- Si se dice UNO teniendo más de una carta se penalizará al jugador tomando dos cartas del mazo de robar.\n"
				+ "- Si el jugador juega su última carta sin decir UNO se le penalizará teniendo que robar dos cartas del mazo de robar.\n"
				+ "- Si un jugador se queda sin cartas legalmente se le sumarán los puntos con respecto a las cartas sobrantes del resto de jugadores.\n"
				+ "- Cuando un jugador se queda sin cartas y no ha llegado a 500 puntos se vuelven a repartir 7 cartas y se juagará una ronda más.\n"
				+ "- Gana el primer jugador en conseguir 500 puntos.");
		}

}
