
package com.poma;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**@author
 * Rebeca Poma
 * Diego Guerrero
 * Ronic Leal
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        SceneManager sceneManager = SceneManager.getInstance();
        
        

        sceneManager.setScene(SceneId.VISTABIENVENIDA,"VistaBienvenida");
        sceneManager.setScene(SceneId.VISTA1,"Vista1");
        //sceneManager.setScene(SceneId.VISTA2,"Vista2");

        sceneManager.init(stage);
        sceneManager.loadScene(SceneId.VISTABIENVENIDA);
        stage.setTitle("Juego_Mazmorras");
        stage.show();

    }


    public static void main(String[] args) {
        launch();
    }

}