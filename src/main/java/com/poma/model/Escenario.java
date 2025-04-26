package com.poma.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Escenario {
    private Celda[][] mapa;

    public Escenario(String rutaRelativa) throws IOException {
        leerDesdeArchivo(rutaRelativa);
    }

    private void leerDesdeArchivo(String path) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("com/poma/data/mapas.txt");
if (inputStream == null) {
    throw new IOException("No se pudo encontrar el archivo: com/poma/data/mapas.txt");
}

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            List<String> lineas = new ArrayList<>();
            String linea;

            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }

            int filas = lineas.size();
            int columnas = lineas.get(0).length();
            mapa = new Celda[filas][columnas];

            for (int y = 0; y < filas; y++) {
                String fila = lineas.get(y);
                for (int x = 0; x < columnas; x++) {
                    char simbolo = fila.charAt(x);

                    TipoCelda tipo;
                    switch (simbolo) {
                        case '.':
                            tipo = TipoCelda.SUELO;
                            break;
                        case '#':
                            tipo = TipoCelda.PARED;
                            break;
                        default:
                            throw new IllegalArgumentException("Símbolo no reconocido: " + simbolo);
                    }

                    mapa[y][x] = new Celda(tipo);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Error leyendo el mapa: " + e.getMessage(), e);
        }
    }

    public Celda getCelda(int fila, int columna) {
        return mapa[fila][columna];
    }

    public int getAlto() {
        return mapa.length;
    }

    public int getAncho() {
        return mapa[0].length;
    }

}
