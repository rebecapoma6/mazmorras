package com.poma.model;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

public class GestorImg {

    private static final Map<String, Image> cache = new HashMap<>();

    // Carga la imagen y la guarda en cache
    public static Image getImagen(String ruta) {
        if (!cache.containsKey(ruta)) {
            try {
                Image imagen = new Image(GestorImg.class.getResourceAsStream(ruta));
                cache.put(ruta, imagen);
            } catch (Exception e) {
                System.err.println("No se pudo cargar la imagen: " + ruta);
            }
        }
        return cache.get(ruta);
    }

    // Constantes para rutas
    public static final String PROTA_ARRIBA = "/com/poma/images/protagonista_arriba.png";
    public static final String PROTA_ABAJO = "/com/poma/images/protagonista_abajo.png";
    public static final String PROTA_IZQUIERDA = "/com/poma/images/protagonista_izquierda.png";
    public static final String PROTA_DERECHA = "/com/poma/images/protagonista_derecha.png";

    public static final String ENE_ABAJO = "/com/poma/images/zombito_abajo.png";

    public static final String SUELO = "/com/poma/images/suelo.jpg";
    public static final String PARED = "/com/poma/images/pared.jpg";
    
}
