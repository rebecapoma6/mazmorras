package com.poma.controller;

import com.poma.SceneId;
import com.poma.SceneManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Vista1Controller {
    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPuntosVida;

    @FXML
    private TextField txtDefensa;

    @FXML
    private TextField txtFuerza;

    @FXML
    private Button btnIniciar;

    @FXML
    private AnchorPane rootVista1;

    @FXML
    public void initialize(){

        String rutaImg = "data/fondoMazmorra";
        rootVista1.setStyle("-fx-background-image: url('" + getClass().getResource(rutaImg) + "'); -fx-background-size: cover;");

        btnIniciar.setOnAction(event ->{
            SceneManager.getInstance().loadScene(SceneId.VISTA1);
        });
    }

   


    
}
