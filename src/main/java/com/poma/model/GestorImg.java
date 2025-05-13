package com.poma.model;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;
/**
 * Clase utilitaria para gestionar la carga y caché de imágenes en el juego.
 * Permite optimizar el rendimiento evitando cargar varias veces la misma imagen.
 */
public class GestorImg {
    /**
     * Mapa estático que almacena las imágenes ya cargadas para reutilizarlas
     */
    private static final Map<String, Image> cache = new HashMap<>();

    /**
     * Carga una imagen desde la ruta especificada y la almacena en caché para futuros usos.
     * Si la imagen ya fue cargada previamente, la devuelve directamente desde la caché.
     * @param ruta Ruta relativa del recurso de imagen (por ejemplo: "/com/poma/images/archivo.png")
     * @return La imagen cargada o null si no se pudo cargar.
     */
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

    /**
     * CONSTANTES DE RUTAS DE IMÁGENES
     * Ruta de la imagen del protagonista mirando hacia arriba, abajo, izquierda, derecha
     */
    public static final String PROTA_ARRIBA = "/com/poma/images/protagonista_arriba.png";
    public static final String PROTA_ABAJO = "/com/poma/images/protagonista_abajo.png";
    public static final String PROTA_IZQUIERDA = "/com/poma/images/protagonista_izquierda.png";
    public static final String PROTA_DERECHA = "/com/poma/images/protagonista_derecha.png";
    /**
     * Ruta de la imagen del enemigo mirando hacia abajo.
     */
    public static final String ENE_ABAJO = "/com/poma/images/zombito_abajo.png";
    /**
     * Ruta de la imagen de la celda de pared y suelo.
     */
    public static final String SUELO = "/com/poma/images/suelo.jpg";
    public static final String PARED = "/com/poma/images/pared.jpg";
    
}
