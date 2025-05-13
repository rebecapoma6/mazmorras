package com.poma;

import java.io.IOException;
import java.util.HashMap;
import com.poma.controller.Vista2Controller;
import com.poma.model.Protagonista;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * {@code SceneManager} es un singleton encargado de gestionar el cambio y almacenamiento
 * de escenas en la aplicación JavaFX. Permite cargar FXML, asociar controladores
 * y cambiar de escena de forma centralizada.
 */
public class SceneManager {
    /** Instancia única de SceneManager (patrón Singleton). */
    private static SceneManager instance;
    /** Ventana principal de la aplicación JavaFX. */
    private Stage stage;
     /** Mapa que asocia identificadores de escena con objetos Scene. */
    private HashMap<SceneId, Scene> scenes;

    /**
     * Constructor privado para evitar la creación de múltiples instancias.
     * Inicializa el mapa de escenas.
     */
    private SceneManager() {
        scenes = new HashMap<>();
    }

    /**
     * Obtiene la instancia única de SceneManager.
     * Si no existe, la crea.
     * @return Instancia única de SceneManager.
     */
    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    /**
     * Inicializa la referencia al {@link Stage} principal de la aplicación.
     * @param stage Ventana principal de JavaFX.
     */
    public void init(Stage stage) {
        this.stage = stage;
    }

    /**
     * Carga un archivo FXML, crea una nueva escena y la almacena en el mapa de escenas
     * bajo el identificador proporcionado.
     * @param sceneID Identificador único de la escena.
     * @param fxml  Nombre del archivo FXML (sin la extensión) que define la vista.
     */
    public void setScene(SceneId sceneID, String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/" + fxml + ".fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 600, 400);
            scene.setUserData(fxmlLoader); 
            scenes.put(sceneID, scene); // Almacena la escena en el mapa con el identificador correspondiente
        } catch (IOException e) {
            e.printStackTrace(); // En caso de error al cargar el FXML
        }
    }

    /**
     * Cambia la escena actual a la asociada al identificador proporcionado.
     * Si el controlador de la escena es una instancia de {@link Vista2Controller},
     * le pasa el protagonista antes de mostrar la escena.
     * 
     * @param sceneID Identificador de la escena a mostrar.
     * @param protagonista Instancia del protagonista a pasar al controlador.
     * @throws IllegalStateException Si la escena no ha sido configurada previamente.
     */
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

    /**
     * Cambia la escena actual a la asociada al identificador proporcionado.
     * @param sceneID Identificador de la escena a mostrar.
     * @throws IllegalStateException Si la escena no ha sido configurada previamente.
     */
    public void loadScene(SceneId sceneID) {
        if (scenes.containsKey(sceneID)) {
            Scene scene = scenes.get(sceneID);
            stage.setScene(scene);
            stage.show();
        } else {
            throw new IllegalStateException("La escena " + sceneID + " no está configurada.");
        }
    }

    /**
     * Devuelve la escena asociada al identificador proporcionado.
     * @param id Identificador de la escena.
     * @return La escena correspondiente, o {@code null} si no existe.
     */
    public Scene getScene(SceneId id) {
        return scenes.get(id);
    }
}