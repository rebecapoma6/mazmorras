package com.poma.model;

public class Celda {
    private TipoCelda tipo;
    private boolean ocupado;

    private Celda(TipoCelda tipo){
        this.tipo = tipo;
        this.ocupado = false;
    }


    public TipoCelda getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoCelda tipo) {
        this.tipo = tipo;
    }

    public boolean isOcupado() {
        return this.ocupado;
    }

    public boolean getOcupado() {
        return this.ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public boolean esTransitable(){
        return tipo == TipoCelda.SUELO && !ocupado;
    }
    
}
