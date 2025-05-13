package com.poma.controller;

import com.poma.SceneId;
import com.poma.SceneManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
/**
 * Controlador de la vista de bienvenida de la aplicación.
 * Se encarga de mostrar la pantalla inicial y gestionar el cambio a la vista del formulario
 * cuando el usuario pulsa el botón "Empezar".
 */
public class VistaBienvenida {
    /** Botón para comenzar el juego y pasar a la siguiente vista. */
    @FXML
    private Button btnEmpezar;

    /** Panel raíz de la vista de bienvenida, utilizado para establecer el fondo. */
    @FXML
    private AnchorPane ancho;

    /**
     * Inicializa la vista de bienvenida.
     * Establece la imagen de fondo y configura el evento del botón para cambiar de escena.
     */
    @FXML
    public void initialize() {
        String rutaImg = "/com/poma/images/Bienvenida.jpg";
        ancho.setStyle("-fx-background-image: url('" + getClass().getResource(rutaImg) + "'); -fx-background-size: cover;");


        btnEmpezar.setOnAction(event -> {
            // Aquí cambias a la vista del formulario
            // Por ejemplo:
            SceneManager.getInstance().loadScene(SceneId.VISTA1);
        });
    }

}
