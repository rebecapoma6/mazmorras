package com.poma.controller;

import com.poma.model.Celda;
import com.poma.model.Direccion;
import com.poma.model.GestorImg;
import com.poma.model.MotorJuego;
import com.poma.model.Protagonista;
import com.poma.model.Proveedor;
import com.poma.interfaces.Observer;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
/**
 * Controlador de la segunda vista principal del juego.
 * Se encarga de mostrar el mapa, el protagonista, los enemigos y las estadísticas,
 * así como de gestionar el movimiento del protagonista y la actualización de la vista.
 */
public class Vista2Controller implements Observer {

    private Image protagonistaArriba;
    private Image protagonistaAbajo;
    private Image protagonistaIzquierda;
    private Image protagonistaDerecha;
    private Image enemigoAbajo;
    private Image imgSuelo;
    private Image imgPared;

    /**Contenedor raíz de la vista, definido en el FXML. */
    @FXML
    private HBox root;
     /** GridPane principal para dibujar el mapa. */
    private GridPane mainGridPane;
    /** Referencia al motor del juego. */
    private MotorJuego motorJuego;
    /** Referencia al protagonista. */
    private Protagonista protagonista;
    /** VBox para mostrar las estadísticas del jugador. */
    private VBox datosJugador;

    /**
     * Permite establecer el protagonista desde fuera del controlador.
     * Actualiza la vista si el GridPane ya está inicializado.
     * @param protagonista Protagonista a mostrar.
     */
    public void setProtagonista(Protagonista protagonista) {
        this.protagonista = protagonista;
        Proveedor.getInstance().setProtagonista(protagonista);

        // Verifica si mainGridPane ya está inicializado
        if (mainGridPane != null) {
            actualizarVista();
        }
    }

    /**
     * Inicializa la vista: carga imágenes, configura el mapa, añade observador,
     * crea el panel de estadísticas y configura los eventos de teclado.
     */
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
            // IMAGEN POR DEFECTO DE ENEMIGO
            enemigoAbajo = GestorImg.getImagen(GestorImg.ENE_ABAJO);

            imgSuelo = GestorImg.getImagen(GestorImg.SUELO);
            imgPared = GestorImg.getImagen(GestorImg.PARED);

            motorJuego.addObserver(this);
            mainGridPane = new GridPane();

            // Establecer el tamaño de las celdas
            for (int i = 0; i < motorJuego.getMapa().getAncho(); i++) {
                ColumnConstraints columnConstraints = new ColumnConstraints();
                columnConstraints.setMinWidth(60); // Establece el ancho mínimo de cada columna
                columnConstraints.setPrefWidth(60); // Establece el ancho preferido
                columnConstraints.setMaxWidth(60); // Establece el ancho máximo
                mainGridPane.getColumnConstraints().add(columnConstraints);
            }

            for (int i = 0; i < motorJuego.getMapa().getAlto(); i++) {
                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setMinHeight(60); // Establece la altura mínima de cada fila
                rowConstraints.setPrefHeight(60); // Establece la altura preferida
                rowConstraints.setMaxHeight(60); // Establece la altura máxima
                mainGridPane.getRowConstraints().add(rowConstraints);
            }

            // VBox con estadísticas del protagonista
            datosJugador = new VBox(10); // Espacio vertical entre los label
            datosJugador.setPadding(new Insets(10));
            actualizarDatosJugador();

            root.getStylesheets().add(getClass().getResource("/com/poma/styles/style.css").toExternalForm());

            root.setSpacing(30); // Espacio horizontal entre mainGridPane y datosJugador
            
            root.getChildren().addAll(mainGridPane, datosJugador);

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

    /**
     * Actualiza los datos y estadísticas mostrados del protagonista.
     */
    private void actualizarDatosJugador() {
        datosJugador.getChildren().clear();

        if (protagonista != null) {
            Label lblNombre = new Label("Nombre del protagonista: " + protagonista.getNombre());
            Label lblVida = new Label("Puntos de vida: " + protagonista.getPuntosVida());
            Label lblDefensa = new Label("Defensa: " + protagonista.getDefensa());
            Label lblFuerza = new Label("Fuerza: " + protagonista.getFuerza());
            Label lblPosicion = new Label(
                    "Protagonista en fila: " + protagonista.getFila() + ", columna: " + protagonista.getColumna());

            /** Estilos CSS */
            datosJugador.getStyleClass().add("datos-jugador");
            lblNombre.getStyleClass().add("datos-jugador-label");
            lblVida.getStyleClass().add("datos-jugador-label");
            lblDefensa.getStyleClass().add("datos-jugador-label");
            lblFuerza.getStyleClass().add("datos-jugador-label");
            lblPosicion.getStyleClass().add("datos-jugador-label");

            datosJugador.getChildren().addAll(lblNombre, lblVida, lblDefensa, lblFuerza, lblPosicion);
        }
    }

    /**
     * Gestiona el evento de teclado para mover al protagonista según la tecla pulsada.
     * @param event Evento de teclado.
     */
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

        motorJuego.moverProtagonista(nuevaFila, nuevaColumna);

    }

    /**
     * Redibuja el mapa, el protagonista, los enemigos y actualiza las estadísticas.
     */
    private void actualizarVista() {
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

        // Actualiza las estadísticas del protagonista
        actualizarDatosJugador();

    }

    /**
     * Método del patrón Observer. Se llama automáticamente cuando el modelo cambia.
     * Actualiza la vista para reflejar los cambios.
     */
    @Override
    public void onChange() {
        actualizarVista();
    }

}
