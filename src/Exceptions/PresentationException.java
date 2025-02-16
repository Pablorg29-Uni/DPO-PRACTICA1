package Exceptions;
/**
 * Excepción para errores relacionados con la presentación de datos en la aplicación.
 */
public class PresentationException extends Exception {
    /**
     * Constructor que recibe un mensaje descriptivo del error ocurrido.
     *
     * @param message Mensaje detallado sobre la causa de la excepción.
     */
    public PresentationException(String message) {
        super(message);
    }
}
