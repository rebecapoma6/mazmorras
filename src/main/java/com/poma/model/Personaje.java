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

    /**
     * @param danio es un parametro de entrada que se resta, de la defensa para obtener el daño real
     * es lo que une entre puntos de vida y ataque (fuerza):Con el ataque se incrementa el numero de daños(guardado en daño real) 
     * y asu vez se decrementa estos puntos a puntosVida
     * si tenemos 15 de puntosVida , y 5 de defensa , y me atacan con 10 de fuerza , yo(protagonista) defenderia 5, pero los otros
     * 5 se incrementan en puntosDanio q asu vez se restan de sus puntosVida, entonces nos quedaria 10 de puntosVida.
     */

    public void CalcularPuntosVida(int danio) {
        //int danioReal = Math.max(danio - defensa, 0); // asegura que el daño no sea negativo
        int danioReal = (danio - defensa);
        puntosVida -= danioReal; // Resta el daño real de los puntos de vida
        
        if (puntosVida < 0) {
            puntosVida = 0; // Asegura que los puntos de vida no sean negativos
        }
    }
    /**
     * por ejemplo para cuando un enemigo muere... si su vida se queda a cero... "vivo" retorna a falso
     * @return
     */
    public boolean estaVivo(){ 
         return puntosVida > 0;
    }
    /**
     * retorna fuerza porq es la forma en la q se calcula el daño , el cual esta relacionado con los puntos de vida
     * @return
     */
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

    






