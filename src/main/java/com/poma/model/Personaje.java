package com.poma.model;

abstract class Personaje {
    
   protected String nombre;
    protected int puntosVida;
    protected int defensa;
    protected int fuerza;
    protected int fila ;
    protected int columna;

  

    Personaje(String nombre, int defensa, int fuerza, int fila, int vida){
        this.nombre = nombre;
        this.defensa = defensa;
        this.fuerza = fuerza;
        this.fila = fila;
        this.puntosVida = vida;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFila() {
        return this.fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return this.columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public int getPuntosVida() {
        return puntosVida;
    }

    public void setPuntosVida(int puntosVida) {
        this.puntosVida = puntosVida;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getFuerza() {
        return fuerza;
    }

    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    // public int getDanio() {
    //     return danio;
    // }

    // public void setDanio(int danio) {
    //     this.danio = danio;
    // }

    public void CalcularPuntosVida(int danio) {
        int danioReal = Math.max(danio - defensa, 0); // asegura que el daño no sea negativo
        puntosVida -= danioReal; // Resta el daño real de los puntos de vida
        
        if (puntosVida < 0) {
            puntosVida = 0; // Asegura que los puntos de vida no sean negativos
        }
    }

    public boolean estaVivo(){ //por ejemplo para cuando un enemigo muere... si su vida se queda a cero... "vivo" retorna a falso
         return puntosVida > 0;
    }

     public int calcularDanio(){

        return fuerza;
    }


    public void setPosicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    @Override
    public String toString() {
        return "Personaje [nombre=" + nombre + ", puntosVida=" + puntosVida + ", defensa=" + defensa + ", fuerza="
                + fuerza + ", ]";
    }

    


        
    }

    






