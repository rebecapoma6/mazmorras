package com.poma.model;

abstract class Personaje {
    
    protected String nombre;
    protected int puntosVida;
    protected int defensa;
    protected int fuerza;
    protected int danio;

    Personaje(String nombre, int puntosVida, int defensa, int fuerza){
        this.nombre = nombre;
        this.puntosVida = puntosVida;
        this.defensa = defensa;
        this.fuerza = fuerza;
        this.danio = 10;
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

    public void recibirDanio(int danio){
        this.puntosVida -= danio;

    }

    





}
