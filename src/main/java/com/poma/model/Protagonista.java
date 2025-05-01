package com.poma.model;

public class Protagonista extends Personaje {

    private int fila;
    private int columna;

    

    public Protagonista(String nombre, int defensa, int fuerza, int danio, int puntosVida){
        super(nombre, defensa, fuerza, danio, puntosVida);

        

    }



    public int getFila() {
        return fila;
    }



    public void setFila(int fila) {
        this.fila = fila;
    }



    public int getColumna() {
        return columna;
    }



    public void setColumna(int columna) {
        this.columna = columna;
    }


    public void setPosicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }




    

    
}
