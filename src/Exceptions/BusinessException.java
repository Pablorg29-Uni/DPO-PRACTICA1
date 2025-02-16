package Exceptions;
/**
 * Excepción personalizada para manejar errores de lógica de negocio.
 */
public class BusinessException extends Exception {
    /**
     * Construye una nueva excepción de negocio con un mensaje específico.
     *
     * @param message Descripción del error.
     */
    public BusinessException(String message) {
        super(message);
    }
}
