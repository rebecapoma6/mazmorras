package com.poma.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GestorEnemigo {

    private List<Enemigo> enemigos;

    public GestorEnemigo() {
        enemigos = new ArrayList<>();
        cargarEnemigos("dataUrl/enemigos.txt");
    }

    private void cargarEnemigos(String ruta) {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(ruta)))) {
            String linea;
            br.readLine();

            while ((linea = br.readLine()) != null) {

                String[] partes = linea.split(",");

                String tipo = partes[0];
                int x = Integer.parseInt(partes[1]);
                int y = Integer.parseInt(partes[2]);
                int vida = Integer.parseInt(partes[3]);
                int fuerza = Integer.parseInt(partes[4]);
                int defensa = Integer.parseInt(partes[5]);
                int velocidad = Integer.parseInt(partes[6]);
                int percepcion = Integer.parseInt(partes[7]);

                Enemigo enemigo = new Enemigo(tipo, y, x, vida, fuerza, defensa, velocidad, percepcion);
                enemigos.add(enemigo);
            }

        } catch (Exception e) {

        }

      

    }

    public List<Enemigo> getEnemigos(){
        return enemigos;
    }



    public void moverEnemigos(Protagonista protagonista, LectorEscenario escenario){

        Random rm = new Random();

        for (Enemigo enemigo : enemigos) {
            int filaProta = protagonista.getFila();
            int columProta = protagonista.getColumna();
            int filaEnemigo = enemigo.getFila();
            int columEnemigo = enemigo.getColumna();

            boolean cercaDelProta = (filaProta >= filaEnemigo - enemigo.getPercepcion() && filaProta <= filaEnemigo + enemigo.getPercepcion()) &&
            (columProta >= columEnemigo - enemigo.getPercepcion() && columProta <= columEnemigo + enemigo.getPercepcion());

            int nvaFila = filaEnemigo;
            int nvaColum = columEnemigo;

            if (cercaDelProta) {
                 // Mover hacia el protagonista
                if (filaProta < filaEnemigo) {
                    nvaFila --;  // Moverse hacia arriba
                } else if( filaProta > filaEnemigo){
                    nvaFila++;   // Moverse hacia abajo
                }


                if (columProta < columEnemigo) {
                    nvaColum--;  // Moverse a la izquierda
                } else if (columProta > columEnemigo) {
                    nvaColum++;  // Moverse a la derecha
                }

            } else {

                int [][] direcciones = { {1,0}, {-1,0}, {0,1}, {0,-1} };
                int index = rm.nextInt(direcciones.length);  // Seleccionar un índice aleatorio


                int f = filaEnemigo + direcciones[index][0];
                int c = columEnemigo + direcciones[index][1];

                if (f >= 0 && f < escenario.getAlto() &&  c >= 0 && c < escenario.getAncho()) {
                    if (escenario.getCelda(f,c).getTipo() != TipoCelda.PARED) {
                        nvaFila = f;
                        nvaColum = c;
                    }                        
                }
            }        

            enemigo.setPosicion(nvaFila, nvaColum);

        }

    }

}
