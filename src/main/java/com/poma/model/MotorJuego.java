package com.poma.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.poma.interfaces.Observer;
import javafx.application.Platform;
import javafx.scene.control.Alert;
/**
 * Motor principal del juego. Se encarga de la lógica de movimiento, combate,
 * gestión de enemigos, control del mapa y notificación a los observadores.
 */
public class MotorJuego {
    private Protagonista protagonista;
    private GestorEnemigo gestorEnemigo;
    private LectorEscenario mapa;
    private List<Observer> observadores = new ArrayList<>();

    /**
     * Crea un nuevo motor de juego, carga el mapa y posiciona al protagonista.
     * @param rutaMapa Ruta al archivo del mapa.
     * @param protagonista Instancia del protagonista.
     * @throws RuntimeException Si ocurre un error al cargar el mapa.
     */
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

    /**
     * Busca la primera celda de tipo SUELO en el mapa para ubicar al protagonista.
     * @return Un array con la fila y columna de la posición inicial.
     * @throws IllegalStateException Si no se encuentra una celda SUELO.
     */
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

    /**
     * Intenta mover al protagonista a la posición indicada.
     * Si hay un enemigo en la nueva posición, inicia un combate.
     * Si la posición es válida y libre, mueve al protagonista y a los enemigos.
     * @param nuevaFila nuevaFila Nueva fila de destino.
     * @param nuevaColumna nuevaColumna Nueva columna de destino.
     */
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

    /**
     * Verifica si hay un enemigo en la posición indicada.
     * @param fila  Fila a comprobar.
     * @param columna Columna a comprobar.
     * @return true si hay un enemigo en esa posición, false en caso contrario.
     */
    public boolean hayEnemigoEnPosicion(int fila, int columna) {

        for (Enemigo enemigo : gestorEnemigo.getEnemigos()) {
            if (enemigo.getFila() == fila && enemigo.getColumna() == columna) {
                return true;
            }
        }

        return false;
    }

    /**
     * Inicia un combate entre el protagonista y el enemigo en la posición dada.
     * Calcula el daño, actualiza los puntos de vida y elimina al enemigo si es derrotado.
     * Finaliza el juego si el protagonista muere.
     * @param fila Fila donde ocurre el combate.
     * @param columna Columna donde ocurre el combate.
     */

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

    /**
     * Finaliza el juego mostrando una alerta y cerrando la aplicación.
     */
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
     * Verifica si la posición indicada es válida para el protagonista (dentro de los límites y no es pared).
     * @param fila Fila a comprobar.
     * @param columna  Columna a comprobar.
     * @return true si la posición es válida, false en caso contrario.
     */

    private boolean esPosicionValida(int fila, int columna) {

        if (fila < 0 || fila >= mapa.getAlto() || columna < 0 || columna >= mapa.getAncho()) {
            System.out.println("Posición fuera de los límites: (" + fila + ", " + columna + ")");
            return false; // Fuera de los límites
        }
        Celda celda = mapa.getCelda(fila, columna);
        if (celda.getTipo() == TipoCelda.PARED) {
            System.out.println("Celda actual: " + celda.getTipo());
            return false; // Si la celda es una pared
        }
        return true;

    }

    /**
     * Devuelve el lector de escenario (mapa) actual.
     * @return LectorEscenario utilizado en el juego.
     */
    public LectorEscenario getMapa() {
        return mapa;
    }

    /**
     * Devuelve el gestor de enemigos actual.
     * @return GestorEnemigo utilizado en el juego.
     */
    public GestorEnemigo getGestorEnemigo() {
        return gestorEnemigo;
    }

    /**
     * Añade un observador para recibir notificaciones de cambios en el juego.
     * @param o Observador a añadir.
     */
    public void addObserver(Observer o) {
        observadores.add(o);
    }

    /**
     * Notifica a todos los observadores registrados sobre un cambio en el estado del juego.
     */
    private void notifyObservers() {
        for (Observer o : observadores) {
            o.onChange();
        }
    }
}