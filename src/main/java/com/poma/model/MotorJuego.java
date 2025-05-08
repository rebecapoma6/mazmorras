package com.poma.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.poma.interfaces.Observer;

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

    private int[] encontrarPosicionInicial() {
        for (int fila = 0; fila < mapa.getAlto(); fila++) {
            for (int columna = 0; columna < mapa.getAncho(); columna++) {
                if (mapa.getCelda(fila, columna).getTipo() == TipoCelda.SUELO) {
                    return new int[]{fila, columna};
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
    
            protagonista.setPosicion(nuevaFila, nuevaColumna);
    
            // Verifica si la posición se ha actualizado correctamente
            System.out.println("Nueva posición del protagonista: (" + protagonista.getFila() + ", " + protagonista.getColumna() + ")");
    
            gestorEnemigo.moverEnemigos(protagonista, mapa);
            notifyObservers();
        }
    }
    
    

    private boolean esPosicionValida(int fila, int columna) {
        if (fila < 0 || fila >= mapa.getAlto() || columna < 0 || columna >= mapa.getAncho()) {
            System.out.println("Posición fuera de los límites: (" + fila + ", " + columna + ")");
            return false; // Fuera de los límites
        }
        Celda celda = mapa.getCelda(fila, columna);
        if (celda.getTipo() == TipoCelda.PARED) {
            System.out.println("Posición bloqueada por una pared: (" + fila + ", " + columna + ")");
            return false; // Si la celda es una pared
        }
        return true;
    }
    

    

    public Protagonista getProtagonista() {
        return protagonista;
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