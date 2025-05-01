package com.poma.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import com.poma.model.LectorEscenario;
import com.poma.interfaces.Observer;
import com.poma.model.Celda;
import com.poma.model.Protagonista;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Vista2Controller implements Observer {
    // Recibir el objeto Protagonista
    private Protagonista protagonista;

    @FXML
    private VBox root;

    private GridPane mainGridPane;

    public void setProtagonista(Protagonista protagonista) {
        this.protagonista = protagonista;
        reproduce();
    }

    @FXML
    public void initialize() {

    }

    private void reproduce() {
        if (protagonista != null) {
            System.out.println("Nombre del protagonista " + protagonista.getNombre());
            System.out.println("Puntos de vida: " + protagonista.getPuntosVida());
        }

        try {
            // LectorEscenario escenario = new LectorEscenario(" /dataUrl/mapas.txt");
            // System.out.println("Ancho: " + escenario.getAncho() + ", Alto: " +
            // escenario.getAlto()); // <-- DEBUG

            // EscenarioView vista = new EscenarioView(escenario);
            // containerMapa.getChildren().add(vista.getVista());

            LectorEscenario lectorEscenario = new LectorEscenario("dataUrl/mapas.txt");

            mainGridPane = new GridPane();
            mainGridPane.setPadding(new Insets(10));

            int filas = lectorEscenario.getAlto();
            int columnas = lectorEscenario.getAncho();
            double porcentaje = 100.0 / columnas;

            for (int i = 0; i < columnas; i++) {
                ColumnConstraints col = new ColumnConstraints();
                col.setPercentWidth(porcentaje);
                col.setHgrow(Priority.ALWAYS);
                col.setHalignment(HPos.CENTER);
                mainGridPane.getColumnConstraints().add(col);
            }

            for (int f = 0; f < filas; f++) {
                for (int c = 0; c < columnas; c++) {
                    Celda celda = lectorEscenario.getCelda(f, c);
                    Label label = new Label();

                    // Si la posición es la del protagonista, lo dibujamos allí
                    // Si el protagonista existe y su posición coincide con la celda actual (fila,
                    // col)
                    if (protagonista != null && f == protagonista.getFila() && c == protagonista.getColumna()) {
                        label.setText("P"); // aqui ponemos P , porq luego cargamos la img de protagonista suponemos
                        label.setTextFill(Color.GREEN);
                        // Si no está el protagonista, simplemente se muetra la celda que se crea aqui
                        // en el switch
                    } else {
                        switch (celda.getTipo()) {
                            case SUELO:
                                label.setText(".");
                                label.setTextFill(Color.RED);
                                break;
                            case PARED:
                                label.setText("#");
                                label.setTextFill(Color.DARKGRAY);
                                break;
                            // default:
                            // break;
                        }
                    }
                    label.setFont(Font.font("Consolas", 18));
                    mainGridPane.add(label, f, c);
                }
            }

            root.getChildren().clear();
            root.getChildren().add(mainGridPane);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onChange() {
        reproduce();
    }

}
