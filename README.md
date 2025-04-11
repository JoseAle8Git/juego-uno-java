# 🎮 UNOGui - Juego de UNO en Java

Este es un proyecto desarrollado en Java utilizando Eclipse que simula el clásico juego de cartas **UNO**, con una interfaz gráfica integrada.

---

## 🛠️ Tecnologías utilizadas

- Java 8+
- Eclipse IDE
- Swing (para la interfaz gráfica)
- Programación orientada a objetos (POO)
- JLayer (para reproducir archivos MP3)
- Hilos (Thread) para reproducir música de fondo sin bloquear la interfaz

---

## 🚀 Características principales

- Juego completo para múltiples jugadores.
- Interfaz gráfica intuitiva y fácil de usar.
- Lógica de cartas especiales implementada:
  - +2, +4, cambio de color, reversa, salta turno.
- Mecanismo de turnos y control de victoria.
- Gestión de mazo y cartas jugadas.

---

## 🧠 Detalles técnicos

- La música del juego se reproduce utilizando un **hilo sencillo (Thread)** que permite ejecutar audio en segundo plano sin bloquear la interfaz gráfica.
- Se emplea la librería **JLayer (jl1.0.1.jar)** para la reproducción de archivos MP3.
- Esto permite ambientar el juego con música sin afectar la jugabilidad ni la respuesta del usuario.

---

## 🧑‍💻 Cómo ejecutar el proyecto

### Opción 1: Desde Eclipse
1. Clona el repositorio:
   ```bash
   git clone https://github.com/JoseAle8Git/juego-uno-java.git
