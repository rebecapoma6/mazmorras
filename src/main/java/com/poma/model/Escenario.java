package com.poma.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Escenario {
    private TipoCelda [][] celdas;

    public Escenario(String rutaRelativa)throws IOException{
        leerDesdeArchivo(rutaRelativa);
    }

    private void leerDesdeArchivo(String path) throws IOException{
        // InputStream is = getClass().getClassLoader().getResourceAsStream("com/poma/data/mapa.txt");
        // if (is == null) {
        //     throw new IOException("Archivo no encontrado: " + path);
        // }


        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("com/poma/data/mapa.txt"),StandardCharsets.UTF_8))){
            List<String> lineas = new ArrayList<>();
            String linea;
            while ((linea = br.readLine()) != null) {
                // System.out.println(linea); 
                lineas.add(linea);
            }

            int filas = lineas.size();
            int columnas = lineas.get(0).length();
            celdas = new TipoCelda[filas][columnas];

            for(int y = 0; y < filas;y++){
                String fila = lineas.get(y);
                for(int x = 0; x < columnas; x++){
                    char simbolos = fila.charAt(x);
                    celdas[y][x] = switch (simbolos){
                        case '.' -> TipoCelda.SUELO;
                        case '#' -> TipoCelda.PARED;
                        default -> throw new IllegalArgumentException("Simbolo no reconocido " + simbolos)
                    };
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Error leyendo el mapa: " + e.getMessage());
        }
    }

    public TipoCelda getTipo(int filas , int columnas){
        return celdas [filas][columnas];
    }

    public int getFilas(){
        return celdas.length;
    }

    public int getColumnas(){
        return celdas[0].length;
    }



}
