package com.poma;

import java.io.IOException;
import java.util.HashMap;
import com.poma.controller.Vista2Controller;
import com.poma.model.Protagonista;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    private static SceneManager instance;
    private Stage stage;
    private HashMap<SceneId, Scene> scenes;

    private SceneManager() {
        scenes = new HashMap<>();
    }

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void init(Stage stage) {
        this.stage = stage;
    }

    public void setScene(SceneId sceneID, String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/" + fxml + ".fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 600, 400);
            scene.setUserData(fxmlLoader); // Almacena el FXMLLoader en el UserData
            scenes.put(sceneID, scene); // Almacena la escena en el mapa con el identificador correspondiente
        } catch (IOException e) {
            e.printStackTrace(); // En caso de error al cargar el FXML
        }
    }

    public void loadScene(SceneId sceneID, Protagonista protagonista) {
        if (scenes.containsKey(sceneID)) {
            Scene scene = scenes.get(sceneID);

            // Setear el controlador ANTES de mostrar la escena
            FXMLLoader loader = (FXMLLoader) scene.getUserData();
            if (loader != null) {
                Object controller = loader.getController();
                if (controller instanceof Vista2Controller) {
                    ((Vista2Controller) controller).setProtagonista(protagonista);
                }
            }

            stage.setScene(scene); // ¡Esta línea va después del seteo!
            stage.show();
        } else {
            throw new IllegalStateException("La escena " + sceneID + " no está configurada.");
        }
    }

    public void loadScene(SceneId sceneID) {
        if (scenes.containsKey(sceneID)) {
            Scene scene = scenes.get(sceneID);
            stage.setScene(scene);
            stage.show();
        } else {
            throw new IllegalStateException("La escena " + sceneID + " no está configurada.");
        }
    }

    public Scene getScene(SceneId id) {
        return scenes.get(id);
    }
}