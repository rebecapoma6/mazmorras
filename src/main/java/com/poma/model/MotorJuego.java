package com.poma.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.poma.interfaces.Observer;
import javafx.application.Platform;
import javafx.scene.control.Alert;

public class MotorJuego {
    private Protagonista protagonista;
    private GestorEnemigo gestorEnemigo;
    private LectorEscenario mapa;
    private List<Observer> observadores = new ArrayList<>();

    public MotorJuego(String rutaMapa, Protagonista protagonista) {
        this.protagonista = protagonista;
        try {
            this.mapa = new LectorEscenario(rutaMapa);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al cargar el mapa: " + e.getMessage());
        }

        int[] posInicial = encontrarPosicionInicial();
        this.protagonista.setPosicion(posInicial[0], posInicial[1]);

        this.gestorEnemigo = new GestorEnemigo();
        notifyObservers();
    }

    public int[] encontrarPosicionInicial() {
        for (int fila = 0; fila < mapa.getAlto(); fila++) {
            for (int columna = 0; columna < mapa.getAncho(); columna++) {
                if (mapa.getCelda(fila, columna).getTipo() == TipoCelda.SUELO) {
                    return new int[] { fila, columna };
                }
            }
        }
        throw new IllegalStateException("No se encontró una celda de tipo SUELO para ubicar al protagonista.");
    }

    public void moverProtagonista(int nuevaFila, int nuevaColumna) {
        if (esPosicionValida(nuevaFila, nuevaColumna)) {
            System.out.println("Nueva posición del protagonista: (" + nuevaFila + ", " + nuevaColumna + ")");
            int filaActual = protagonista.getFila();
            int columnaActual = protagonista.getColumna();

            if (nuevaFila < filaActual) {
                protagonista.setDireccion(Direccion.ARRIBA);
            } else if (nuevaFila > filaActual) {
                protagonista.setDireccion(Direccion.ABAJO);
            } else if (nuevaColumna < columnaActual) {
                protagonista.setDireccion(Direccion.IZQUIERDA);
            } else if (nuevaColumna > columnaActual) {
                protagonista.setDireccion(Direccion.DERECHA);
            }

            if (hayEnemigoEnPosicion(nuevaFila, nuevaColumna)) {
                iniciarCombate(nuevaFila, nuevaColumna);

            } else {
                protagonista.setPosicion(nuevaFila, nuevaColumna);
                gestorEnemigo.moverEnemigos(protagonista, mapa);
                notifyObservers();

            }

        }
    }

    // Método para verificar si hay un enemigo en la posición
    public boolean hayEnemigoEnPosicion(int fila, int columna) {

        for (Enemigo enemigo : gestorEnemigo.getEnemigos()) {
            if (enemigo.getFila() == fila && enemigo.getColumna() == columna) {
                return true;
            }
        }

        return false;
    }

    // Metodo para realizar el combate

    public void iniciarCombate(int fila, int columna) {

        for (Enemigo enemigo : new ArrayList<>(gestorEnemigo.getEnemigos())) {
            if (enemigo.getFila() == fila && enemigo.getColumna() == columna) {
                System.out.println("¡Combate entre " + protagonista.getNombre() + " y " + enemigo.getNombre());

                // CALCULAR DAÑO
                protagonista.CalcularPuntosVida(enemigo.calcularDanio());
                enemigo.CalcularPuntosVida(protagonista.calcularDanio());

                // MOSTRAR RESULTADO

                System.out.println(protagonista.getNombre() + " tiene " + protagonista.getPuntosVida() + "de vida");
                System.out.println(enemigo.getNombre() + "tiene" + enemigo.getPuntosVida() + "de vida ");

                // VERIFICAR SI EL ENEMIGO HA SIDO DERROTADO
                if (!enemigo.estaVivo()) {
                    System.out.println(enemigo.getNombre() + " ha sido derrotado ");
                    gestorEnemigo.eliminarEnemigo(enemigo);

                }

                // VERIFICAR QUE EL PROTAGONISTA HA SIDO DERROTADO
                if (!protagonista.estaVivo()) {
                    System.out.println("El protagonista ha muerto. Fin del juego");
                    finalizarJuego();
                }

                notifyObservers();

            }
        }

    }

    public void finalizarJuego() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Juego Terminado");
        alert.setHeaderText(null);
        alert.setContentText("¡Has perdido! El juego se cerrará.");
        alert.showAndWait();
        Platform.exit();// AÑADIDO PARA QUE SALGA DE LA APLICACION FX

        System.out.println("Juego terminado. Gracias por jugar.");

    }

    /**
     * es para controlar que la posicion sea valida para el protagonista que su
     * limite sea pared     * 
     * @param fila
     * @param columna
     * @return
     */

    private boolean esPosicionValida(int fila, int columna) {

        if (fila < 0 || fila >= mapa.getAlto() || columna < 0 || columna >= mapa.getAncho()) {
            System.out.println("Posición fuera de los límites: (" + fila + ", " + columna + ")");
            return false; // Fuera de los límites
        }
        Celda celda = mapa.getCelda(fila, columna);
        // if (celda.getTipo() == TipoCelda.PARED && celda.getTipo() == TipoCelda.SUELO)
        if (celda.getTipo() == TipoCelda.PARED) {
            System.out.println("Celda actual: " + celda.getTipo());
            return false; // Si la celda es una pared
        }
        return true;

    }

    public LectorEscenario getMapa() {
        return mapa;
    }

    public GestorEnemigo getGestorEnemigo() {
        return gestorEnemigo;
    }

    public void addObserver(Observer o) {
        observadores.add(o);
    }

    private void notifyObservers() {
        for (Observer o : observadores) {
            o.onChange();
        }
    }
}