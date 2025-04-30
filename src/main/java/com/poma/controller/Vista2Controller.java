package com.poma.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.poma.model.Escenario;
import com.poma.model.EscenarioView;
import com.poma.model.Protagonista;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class Vista2Controller {
    //Recibir el objeto Protagonista
    private Protagonista protagonista;


    @FXML
    private GridPane containerMapa;

   
   


    public void setProtagonista(Protagonista protagonista){
        this.protagonista = protagonista;
        reproduce();
    }

    @FXML
    public void initialize(){
        // if(protagonista !=null){
        //     System.out.println("Nombre del protagonista " + protagonista.getNombre());
        //     System.out.println("Puntos de vida: " + protagonista.getPuntosVida());

        // }else{
        //     System.out.println("Protagonista no ha sido inicializado.");
        // }
        
        
       
    }



    private void reproduce(){
        if (protagonista != null) {
            System.out.println("Nombre del protagonista " + protagonista.getNombre());
            System.out.println("Puntos de vida: " + protagonista.getPuntosVida());
        }

        try {
            Escenario escenario = new Escenario(" /dataUrl/mapas.txt");
            System.out.println("Ancho: " + escenario.getAncho() + ", Alto: " + escenario.getAlto()); // <-- DEBUG

            EscenarioView vista = new EscenarioView(escenario);
            containerMapa.getChildren().add(vista.getVista());
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    
    
}
