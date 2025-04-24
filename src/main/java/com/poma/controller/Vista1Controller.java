package com.poma.controller;

import com.poma.SceneId;
import com.poma.SceneManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
    public void initialize(){
        btnIniciar.setOnAction(event ->{
            SceneManager.getInstance().loadScene(SceneId.VISTA1);
        });
    }

   


    
}
