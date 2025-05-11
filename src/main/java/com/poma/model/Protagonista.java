package com.poma.model;

public class Protagonista extends Personaje {

    private Direccion direccion = Direccion.ABAJO; // por defecto

    public Protagonista(String nombre, int defensa, int fuerza, int ataque, int puntosVida) {
        super(nombre, defensa, fuerza, ataque, puntosVida);
        this.nombre = nombre;
        // Posición se define luego en MotorJuego
    }

    public void setPosicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Direccion getDireccion() {
        return direccion;
    }

}
