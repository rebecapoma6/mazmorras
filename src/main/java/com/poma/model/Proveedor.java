package com.poma.model;
/**
 * Proveedor es un Singleton que gestiona instancias únicas y compartidas de
 * Protagonista, GestorEnemigo y MotorJuego en la aplicación.
 * 
 * Facilita el acceso global y la gestión centralizada de estos recursos,
 * asegurando que no haya duplicación y promoviendo la consistencia en el juego.
 */

public class Proveedor {
    /** Instancia única de la clase Proveedor (patrón Singleton). */
    private static Proveedor instance;
    /** Instancia compartida del protagonista. */
    private Protagonista protagonista;
    /** Instancia compartida del gestor de enemigos. */
    private GestorEnemigo gestorEnemigo;
    /** Instancia compartida del motor del juego.  */
    private MotorJuego motorJuego;
 
    /**
     * Constructor privado para evitar la creación de múltiples instancias.
     * Inicializa el gestor de enemigos.
     */
    private Proveedor(){
        gestorEnemigo = new GestorEnemigo();
    }
    
    /**
     * Obtiene la instancia única de Proveedor.
     * Si no existe, la crea.
     * @return Instancia única de Proveedor.
     */
    public static Proveedor getInstance(){
        if (instance == null) {
            instance = new Proveedor();
        }
        return instance;
    }

    /**
     * Inicializa el protagonista y el motor del juego en el proveedor.
     * @param protagonista Instancia del protagonista.
     * @param motorJuego Instancia del motor del juego.
     */
    public void inicializar(Protagonista protagonista, MotorJuego motorJuego) {
        this.protagonista = protagonista;
        this.motorJuego = motorJuego;
    }


    /**
    * Establece el Protagonista compartido en la aplicación.
    * Este método debe ser llamado antes de usar getProtagonista().

    * @param protagonista Instancia del protagonista.
    */
    public void setProtagonista(Protagonista protagonista){
        this.protagonista = protagonista;
    }

    /**
     * Obtiene el protagonista compartido.
     * @return Instancia del protagonista.
     * @throws IllegalStateException Si el protagonista no ha sido inicializado.
     */
    public Protagonista getProtagonista() {
        if (protagonista == null) {
            throw new IllegalStateException("El Protagonista no ha sido inicializado.");
        }
        return protagonista;
    }

    /**
     * Obtiene el motor del juego compartido.
     * @return Instancia del motor del juego.
     * @throws IllegalStateException Si el motor del juego no ha sido inicializado.
     */
    public MotorJuego getMotorJuego() {
        if (motorJuego == null) {
            throw new IllegalStateException("El MotorJuego no ha sido inicializado.");
        }
        return motorJuego;
    }

    /**
     * Obtiene el gestor de enemigos compartido.
     * @return Instancia del gestor de enemigos.
     */
    public GestorEnemigo getGestorEnemigo(){
        return gestorEnemigo;
    }

    /**
     *  Establece el motor del juego compartido en la aplicación.
     * @param motorJuego Instancia del motor del juego.
     */
    public void setMotorJuego(MotorJuego motorJuego) {
        this.motorJuego = motorJuego;
    }

   

   
}
