package Exceptions;
/**
 * Excepción que se lanza cuando ocurre un error relacionado con la persistencia de datos.
 */
public class PersistenceException extends Exception {
    /**
     * Constructor que recibe un mensaje de error.
     *
     * @param message Mensaje descriptivo del error ocurrido.
     */
    public PersistenceException(String message) {
        super(message);
    }
}
