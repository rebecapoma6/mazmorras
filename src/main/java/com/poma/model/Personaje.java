package com.poma.model;

abstract class Personaje {
    
    protected String nombre;
    protected int puntosVida;
    protected int defensa;
    protected int fuerza;
    protected int danio;

  

    Personaje(String nombre, int defensa, int fuerza, int danio, int puntosVida){
        this.nombre = nombre;
        this.defensa = defensa;
        this.fuerza = fuerza;
        this.danio = danio;
        this.puntosVida = puntosVida;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public int getDanio() {
        return danio;
    }

    public void setDanio(int danio) {
        this.danio = danio;
    }

    public int calcularPuntosVida(int puntosVida, int fuerza, int defensa) {
        int danio = fuerza - defensa;
        if(danio < 0){
            danio = 0;
        }
        return danio -= puntosVida;

        
    }

    





}
