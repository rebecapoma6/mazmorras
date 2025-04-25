package com.poma.controller;

import com.poma.model.Protagonista;

import javafx.fxml.FXML;

public class Vista2Controller {
    //Recibir el objeto Protagonista
    private Protagonista protagonista;

    public void setProtagonista(Protagonista protagonista){
        this.protagonista = protagonista;
    }

    @FXML
    public void initialize(){
        if(protagonista !=null){
            System.out.println("Nombre del protagonista " + protagonista.getNombre());
            System.out.println("Puntos de vida: " + protagonista.getPuntosVida());

        }else{
            System.out.println("Protagonista no ha sido inicializado.");
        }
       
    }

    
    
}
