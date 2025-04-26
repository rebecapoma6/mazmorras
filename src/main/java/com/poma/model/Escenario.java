package com.poma.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Escenario {
    private Celda [][] mapa;

    public Escenario(String rutaRelativa)throws IOException{
        leerDesdeArchivo(rutaRelativa);
    }

    // public Escenario(){
    //     leerDesdeArchivo();
    // }

    private void leerDesdeArchivo(String path) throws IOException {
        // InputStream is = getClass().getClassLoader().getResourceAsStream("com/poma/data/mapa.txt");
        // if (is == null) {
        //     throw new IOException("Archivo no encontrado: " + path);
        // }


        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(path),StandardCharsets.UTF_8))){
            
            
            
            List<String> lineas = new ArrayList<>();            
            
            String linea;
            while ((linea = br.readLine()) != null) {
                // System.out.println(linea); 
                lineas.add(linea);
            }

            int filas = lineas.size();
            int columnas = lineas.get(0).length();


            mapa = new Celda[filas][columnas];
            for(int y = 0; y < filas;y++){
                String fila = lineas.get(y);
                for(int x = 0; x < columnas; x++){
                    char simbolos = fila.charAt(x);

                    TipoCelda tipo;
                    switch (simbolos){
                        case '.': 
                        tipo = TipoCelda.SUELO;
                        break;
                        case '#': 
                        tipo = TipoCelda.PARED;
                        break;
                        default:
                         throw new IllegalArgumentException("Simbolo no reconocido " + simbolos);
                    }
                    mapa[y][x] = new Celda(tipo);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Error leyendo el mapa: " + e.getMessage(),e);
        }
    }

    public Celda getCelda(int fila, int columna){
        return mapa [fila][columna];
    }

    public int getAlto(){
        return mapa.length;
    }

    public int getAncho(){
        return mapa[0].length;
    }



}
