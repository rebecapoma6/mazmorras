package com.poma.controller;

import com.poma.model.Celda;
import com.poma.model.Direccion;
import com.poma.model.GestorImg;
import com.poma.model.MotorJuego;
import com.poma.model.Protagonista;
import com.poma.model.Proveedor;
import com.poma.interfaces.Observer;
import javafx.fxml.FXML;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class Vista2Controller implements Observer {

    private Image protagonistaArriba;
    private Image protagonistaAbajo;
    private Image protagonistaIzquierda;
    private Image protagonistaDerecha;
    private Image enemigoAbajo;
    private Image imgSuelo;
    private Image imgPared;

    @FXML
    private HBox root;

    private GridPane mainGridPane;
    private MotorJuego motorJuego;
    private Protagonista protagonista;

    public void setProtagonista(Protagonista protagonista) {
        this.protagonista = protagonista;
        Proveedor.getInstance().setProtagonista(protagonista);

        // Verifica si mainGridPane ya está inicializado
        if (mainGridPane != null) {
            actualizarVista();
        }
    }

    @FXML
    public void initialize() {
        try {
            motorJuego = Proveedor.getInstance().getMotorJuego();
            protagonista = Proveedor.getInstance().getProtagonista();

            if (motorJuego == null || protagonista == null) {
                System.err.println("MotorJuego o Protagonista no están inicializados aún.");
                return;
            }

            // Carga las imágenes
            protagonistaArriba = GestorImg.getImagen(GestorImg.PROTA_ARRIBA);
            protagonistaAbajo = GestorImg.getImagen(GestorImg.PROTA_ABAJO);
            protagonistaIzquierda = GestorImg.getImagen(GestorImg.PROTA_IZQUIERDA);
            protagonistaDerecha = GestorImg.getImagen(GestorImg.PROTA_DERECHA);
            //IMAGEN POR DEFECTO DE ENEMIGO
            enemigoAbajo = GestorImg.getImagen(GestorImg.ENE_ABAJO);           

            imgSuelo = GestorImg.getImagen(GestorImg.SUELO);
            imgPared = GestorImg.getImagen(GestorImg.PARED);

            motorJuego.addObserver(this);
            mainGridPane = new GridPane();

               // Establecer el tamaño de las celdas
        for (int i = 0; i < motorJuego.getMapa().getAncho(); i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setMinWidth(60);  // Establece el ancho mínimo de cada columna
            columnConstraints.setPrefWidth(60);  // Establece el ancho preferido
            columnConstraints.setMaxWidth(60);  // Establece el ancho máximo
            mainGridPane.getColumnConstraints().add(columnConstraints);
        }

        for (int i = 0; i < motorJuego.getMapa().getAlto(); i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(60);  // Establece la altura mínima de cada fila
            rowConstraints.setPrefHeight(60);  // Establece la altura preferida
            rowConstraints.setMaxHeight(60);  // Establece la altura máxima
            mainGridPane.getRowConstraints().add(rowConstraints);
        }
            root.getChildren().add(mainGridPane);

            actualizarVista();
            mainGridPane.setOnMouseClicked(event -> {
                mainGridPane.requestFocus();
            });
            mainGridPane.setOnKeyPressed(event -> manejarMovimiento(event));
            mainGridPane.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void manejarMovimiento(KeyEvent event) {
        System.out.println("Tecla presionada: " + event.getCode()); 
        int nuevaFila = protagonista.getFila();
        int nuevaColumna = protagonista.getColumna();

        switch (event.getCode()) {
            case UP:
                nuevaFila--;
       
                
                break;
            case DOWN:
                nuevaFila++;
            
               
                break;

            case LEFT:
                nuevaColumna--;
          

                break;

            case RIGHT:
                nuevaColumna++;
          

                break;

            default:
                return; 
        }    
        // delega la lógica de movimiento al modelo motorJuego
        motorJuego.moverProtagonista(nuevaFila, nuevaColumna);
        actualizarVista();
    }


    private void actualizarVista() {
          System.out.println("Actualizando vista...");
     if (motorJuego == null || protagonista == null || mainGridPane == null) {
         System.err.println("Vista no puede actualizarse: motorJuego, protagonista o mainGridPane son null.");
         return;
     }

        mainGridPane.getChildren().clear();

    // Dibujar el mapa
    for (int fila = 0; fila < motorJuego.getMapa().getAlto(); fila++) {
        for (int columna = 0; columna < motorJuego.getMapa().getAncho(); columna++) {
            Celda celda = motorJuego.getMapa().getCelda(fila, columna);
            ImageView celdaImageView = null;

            switch (celda.getTipo()) {
                case SUELO:
                    celdaImageView = new ImageView(imgSuelo);
                    break;
                case PARED:
                    celdaImageView = new ImageView(imgPared);
                    break;
                default:
                    continue;
            }

            celdaImageView.setFitWidth(60);
            celdaImageView.setFitHeight(60);
            mainGridPane.add(celdaImageView, columna, fila);
        }
    }
        // Dibujar al protagonista
        Image imgProta;
        switch (protagonista.getDireccion()) {
            case ARRIBA:
                imgProta = protagonistaArriba;
                break;
            case ABAJO:
                imgProta = protagonistaAbajo;
                break;
            case IZQUIERDA:
                imgProta = protagonistaIzquierda;
                break;
            case DERECHA:
                imgProta = protagonistaDerecha;
                break;
            default:
                imgProta = protagonistaAbajo;
                break;
        }

        ImageView protagonistaView = new ImageView(imgProta);
        protagonistaView.setFitWidth(60);
        protagonistaView.setFitHeight(60);
        mainGridPane.add(protagonistaView, protagonista.getColumna(), protagonista.getFila());

        // Dibujar a los enemigos
        motorJuego.getGestorEnemigo().getEnemigos().forEach(enemigo -> {
            if (enemigo.getDireccion() == null) {
                System.err.println("Enemigo sin dirección asignada en la posición: (" + enemigo.getFila() + ", "
                        + enemigo.getColumna() + ")");
                // Asigna una dirección por defecto si es necesario
                enemigo.setDireccion(Direccion.ABAJO); 
            }
            ImageView enemigoView = new ImageView(enemigoAbajo);
            enemigoView.setFitWidth(60);
            enemigoView.setFitHeight(60);
            mainGridPane.add(enemigoView, enemigo.getColumna(), enemigo.getFila());

        });

    }

    @Override
    public void onChange() {
        actualizarVista();
    }

}

