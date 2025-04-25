package com.poma.controller;

import com.poma.SceneId;
import com.poma.SceneManager;
import com.poma.model.Protagonista;

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

        //Recoger datos del jugador
        //Para recoger los datos del jugador en la primera vista se debe declaras las variables con sus atributos
        //luego se iguala al id que hemos puesto en el Scene Builder de cada campo de texto y se llama al metodo get.Text();
        //En caso de que el campo sea TextField y que el dato que ingresamos sea un numero, tenemos que convertir ese texto
        //en numero, por eso se llama a Interger.parseInt(mentemos el id del TextField con el metodo getText())
         btnIniciar.setOnAction(event ->{
        String nombre = txtNombre.getText();
        int puntosVida = Integer.parseInt(txtPuntosVida.getText());
        int defensa = Integer.parseInt(txtDefensa.getText());
        int fuerza = Integer.parseInt(txtFuerza.getText());

        //Crear protagonista
        Protagonista protagonista = new Protagonista (nombre, defensa, fuerza, puntosVida, fuerza);

        //Pasar el protagonista a la siguiente vista
        SceneManager.getInstance().loadScene(SceneId.VISTA2, protagonista);

        });
    }

   


    
}
