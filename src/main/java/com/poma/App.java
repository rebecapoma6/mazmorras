package com.poma;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    // private static Scene scene;

    // @Override
    // public void start(Stage stage) throws IOException {
    //     scene = new Scene(loadFXML("Vista1"), 640, 480);
    //     stage.setScene(scene);
    //     stage.show();
    // }

    // static void setRoot(String fxml) throws IOException {
    //     scene.setRoot(loadFXML(fxml));
    // }

    // private static Parent loadFXML(String fxml) throws IOException {
    //     FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/" + fxml + ".fxml"));
    //     return fxmlLoader.load();
    // }


    @Override
    public void start(Stage stage) {
        SceneManager sceneManager = SceneManager.getInstance();
        sceneManager.init(stage);


        sceneManager.setScene(SceneId.VISTA1,"Vista1");
        sceneManager.setScene(SceneId.VISTA2,"Vista2");


        // stage.setScene(sceneManager.getScene(SceneId.VISTA1));
        stage.setScene(sceneManager.getScene(SceneId.VISTA1));
        stage.setTitle("Juego_Mazmorras");
        stage.show();

    }


    public static void main(String[] args) {
        launch();
    }

}