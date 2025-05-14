package com.poma.model;
/**
 * Representa al protagonista del juego, controlado por el jugador.
 * Hereda los atributos y métodos básicos de la clase abstracta Personaje,
 * y añade la gestión de la dirección en la que está mirando.
 */
public class Protagonista extends Personaje {
    /**
     * Dirección actual en la que mira el protagonista. Por defecto, ABAJO. 
     */
    private Direccion direccion = Direccion.ABAJO;

    /**
     * Crea una nueva instancia de Protagonista con los atributos principales.
     * @param nombre  Nombre del protagonista.
     * @param defensa Valor de defensa.
     * @param fuerza Valor de fuerza.
     * @param ataque (No se utiliza directamente, pero se pasa al constructor padre).
     * @param puntosVida Puntos de vida iniciales.
     */
    public Protagonista(String nombre, int defensa, int fuerza, int ataque, int puntosVida) {
        super(nombre, defensa, fuerza, ataque, puntosVida);
        this.nombre = nombre;
        // La posición inicial se define posteriormente desde MotorJuego.
    }

    /**
     * Establece la posición del protagonista en el mapa.
     * @param fila    Nueva fila.
     * @param columna Nueva columna.
     */
    public void setPosicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    /**
     * Establece la dirección en la que mira el protagonista.
     * @param direccion Nueva dirección.
     */
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene la dirección actual en la que mira el protagonista.
     * @return Dirección actual.
     */

    public Direccion getDireccion() {
        return direccion;
    }


    public void danioNvaCelda(int cantidad){
        this.puntosVida -= defensa;
    }

}
