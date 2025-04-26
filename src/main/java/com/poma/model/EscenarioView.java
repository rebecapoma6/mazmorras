package com.poma.model;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class EscenarioView {
    private static final int MAPA_SIZE = 50;
    private final GridPane gridPane;

    public EscenarioView(Escenario escenario){
        gridPane = new GridPane();
        reproducirEscenario(escenario);
    }


/**
 * Metodo que me va a renderizar o reproducir visual el escenario en el gridpane
 * @param escenario
 */
    private void reproducirEscenario(Escenario escenario){
        gridPane.getChildren().clear();


        for(int fila = 0;fila < escenario.getAlto();fila++){
            for(int col = 0; col < escenario.getAncho();col++){
                Celda celda = escenario.getCelda(fila, col);

                Rectangle rect = new Rectangle(MAPA_SIZE,MAPA_SIZE);

                if (celda.getTipo() == TipoCelda.PARED) {
                    rect.setFill(Color.DARKSLATEGRAY);
                }else if (celda.isOcupado()) {
                    rect.setFill(Color.RED);
                }else{
                    rect.setFill(Color.BEIGE);
                }

                rect.setStroke(Color.BLACK);

                gridPane.add(rect, col, fila);
            }
        }
    }


    public GridPane getVista(){
        return gridPane;
    }



    /**
     * Metodo para actualizar el mapa su ha cambiado
     * @param newEscenario
     */
    public void actulizarVista(Escenario newEscenario){
        reproducirEscenario(newEscenario);
    }




   

  
}
