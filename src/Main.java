import Exceptions.PresentationException;
import Presentation.Menu;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * Clase principal que inicia la aplicación ejecutando el menú principal.
 * Maneja errores críticos de inicio, como la inaccesibilidad del archivo characters.json.
 */
public class Main {

    /**
     * Punto de entrada de la aplicación.
     * Inicializa y ejecuta el menú; en caso de error muestra mensaje y termina la ejecución.
     *
     * @param args Argumentos de línea de comandos (no usados).
     */
    public static void main(String[] args) {
        Menu menu = new Menu();
        try {
            menu.mostrarMenu();
        } catch (PresentationException e) {
            System.out.println("Error: The characters.json file can’t be accessed.");
            System.out.println("Shutting down...");
        }
    }
}