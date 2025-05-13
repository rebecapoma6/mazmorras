package com.poma.controller;
import com.poma.SceneId;
import com.poma.SceneManager;
import com.poma.model.MotorJuego;
import com.poma.model.Protagonista;
import com.poma.model.Proveedor;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
/**
 * Controlador de la vista inicial (Vista1) de la aplicación.
 * Permite al usuario introducir los datos del protagonista, valida los puntos,
 * inicializa el juego y gestiona el cambio a la siguiente escena.
 */
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

    /**
     * Inicializa la vista. Establece el fondo y configura el evento del botón "Iniciar".
     * Valida los datos introducidos, crea el protagonista y el motor del juego,
     * inicializa el singleton Proveedor y cambia a la siguiente escena.
     */
    @FXML
    public void initialize() {
        String rutaImg = "/com/poma/images/fondoMazmorra.png";
        rootVista1.setStyle(
                "-fx-background-image: url('" + getClass().getResource(rutaImg) + "'); -fx-background-size: cover;");

        btnIniciar.setOnAction(event -> {
            try {
                String nombre = txtNombre.getText();
                int puntosVida = Integer.parseInt(txtPuntosVida.getText());
                int defensa = Integer.parseInt(txtDefensa.getText());
                int fuerza = Integer.parseInt(txtFuerza.getText());

                if (puntosVida < 0 || defensa < 0 || fuerza < 0 || (puntosVida + defensa + fuerza) != 30) {
                    mostrarError("Error de puntos", "Los puntos deben ser positivos y sumar exactamente 30.");
                    return;
                }

                Protagonista protagonista = new Protagonista(nombre, defensa, fuerza, fuerza, puntosVida);
                MotorJuego motorJuego = new MotorJuego("/dataUrl/mapas.txt", protagonista);

                // Inicializa los datos compartidos antes de cargar la escena
                Proveedor.getInstance().inicializar(protagonista, motorJuego);

                // Carga el FXML (esto ejecuta Vista2Controller.initialize())
                SceneManager.getInstance().setScene(SceneId.VISTA2, "Vista2");

                // Muestra la escena. No es necesario pasar el protagonista aquí porque ya está
                // en Proveedor
                SceneManager.getInstance().loadScene(SceneId.VISTA2);

            } catch (NumberFormatException e) {
                mostrarError("Error de formato", "Por favor, ingresa valores numéricos válidos.");
            } catch (Exception e) {
                e.printStackTrace();
                mostrarError("Error inesperado", "Ocurrió un error inesperado.");
            }
        });
    }

    /**
     * Muestra un cuadro de diálogo de error con el título y mensaje especificados.
     * @param titulo Título de la ventana de error.
     * @param mensaje Mensaje a mostrar en el cuadro de diálogo.
     */
    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
