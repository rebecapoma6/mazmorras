package com.poma.model;
/**
 * El Proveedor podria ser un Singleton q establece una única instancia de Protagonista,gestorEnemigo
asegurando acceso global en la aplicación,
facilitando la gestión de recursos sin duplicación,
promoviendo consistencia y simplificando la interacción,
ideal para casos donde un objeto único es la solución.
 */
public class Proveedor {
    
    private static Proveedor instance;
    private Protagonista protagonista;
    private GestorEnemigo gestorEnemigo;
    private MotorJuego motorJuego;

    private Proveedor(){
        gestorEnemigo = new GestorEnemigo();
    }
    

    public static Proveedor getInstance(){
        if (instance == null) {
            instance = new Proveedor();
        }
        return instance;
    }

    public void inicializar(Protagonista protagonista, MotorJuego motorJuego) {
        this.protagonista = protagonista;
        this.motorJuego = motorJuego;
    }


    /**
    * Establece el Protagonista compartido en la aplicación.
    * Este método debe ser llamado antes de usar getProtagonista().
     
     * @param protagonista
     */

    public void setProtagonista(Protagonista protagonista){
        this.protagonista = protagonista;
    }

    public Protagonista getProtagonista() {
        if (protagonista == null) {
            throw new IllegalStateException("El Protagonista no ha sido inicializado.");
        }
        return protagonista;
    }

    public MotorJuego getMotorJuego() {
        if (motorJuego == null) {
            throw new IllegalStateException("El MotorJuego no ha sido inicializado.");
        }
        return motorJuego;
    }


    public GestorEnemigo getGestorEnemigo(){
        return gestorEnemigo;
    }

    public void setMotorJuego(MotorJuego motorJuego) {
        this.motorJuego = motorJuego;
    }

   

   
}
