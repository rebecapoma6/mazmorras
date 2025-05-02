package com.poma.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.poma.model.LectorEscenario;
import com.poma.interfaces.Observer;
import com.poma.model.Celda;
import com.poma.model.Protagonista;
import com.poma.model.TipoCelda;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Vista2Controller implements Observer {
    // Recibir el objeto Protagonista
    private Protagonista protagonista;

    @FXML
    private VBox root;

    private GridPane mainGridPane;

    private static final int TAMANO_CELDA = 30; // Tamaño de cada celda del mapa

    private ImageView protagonistaImageView; // Imagen del protagonista

    /**
     * Este método se llama desde la vista anterior para recibir el protagonista
     * y luego llama a reproduce() para mostrarlo en el mapa.
     * 
     * @param protagonista
     */
    public void setProtagonista(Protagonista protagonista) {
        this.protagonista = protagonista;
        reproduce();
    }

    @FXML
    public void initialize() {

        // Cargar la imagen del protagonista

        Image protagonistaImage = new Image(getClass().getResourceAsStream("/imagen/protagonista.gif")); // Ruta de la
                                                                                                         // imagen
        if (protagonistaImage.isError()) {
            System.err.println("Error al cargar la imagen del protagonista.");
        }

        // Cuando se carga la vista, se carga la imagen del protagonista y se ajusta a
        // un tamaño adecuado.

        protagonistaImageView = new ImageView(protagonistaImage);
        protagonistaImageView.setFitWidth(TAMANO_CELDA);
        protagonistaImageView.setFitHeight(TAMANO_CELDA);

    }

    private void manejarMovimiento(KeyEvent event) {

        // Detecta la tecla pulsada y calcula la nueva posición del protagonista.
        // Llama a esPosicionValida para ver si puede moverse ahí.
        // Si es válido, actualiza la posición y redibuja el mapa.

        int nuevaFila = protagonista.getFila();
        int nuevaColumna = protagonista.getColumna();

        // Cambia la fila o columna según la tecla pulsada
        if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) {
            nuevaFila--; // Mover hacia arriba

        } else if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
            nuevaFila++; // Mover hacia abajo

        } else if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
            nuevaColumna--; // Mover hacia la izquierda

        } else if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
            nuevaColumna++; // Mover hacia la derecha
        }

        // Comprueba si la nueva posición es válida (no es pared ni está fuera del mapa)
        if (esPosicionValida(nuevaFila, nuevaColumna)) {
            protagonista.setPosicion(nuevaFila, nuevaColumna);
            reproduce(); // Redibuja el mapa con el protagonista en la nueva posición
        }
    }

    private boolean esPosicionValida(int fila, int columna) {
        // Evita que el protagonista salga del mapa o entre en una pared (#).
        // Muestra en consola el tipo de celda a la que se quiere mover (útil para
        // depurar).

        try {
            LectorEscenario lector = new LectorEscenario("/dataUrl/mapas.txt");
            // Comprueba que la posición esté dentro del mapa
            if (fila < 0 || fila >= lector.getAlto() || columna < 0 || columna >= lector.getAncho()) {
                return false;
            }

            // Comprobar si la celda es una pared
            Celda celda = lector.getCelda(fila, columna);
            System.out.println("Celda actual: " + celda.getTipo());
            return celda.getTipo() != TipoCelda.PARED;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
                    mainGridPane.add(label, c, f);
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
