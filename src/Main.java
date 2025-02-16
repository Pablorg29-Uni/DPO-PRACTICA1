import Exceptions.PresentationException;
import Presentation.Menu;


/**
 * Clase principal que inicia la aplicación.
 *
 * Se encarga de ejecutar el menú principal y manejar posibles errores de inicio.
 */
public class Main {
    /**
     * Punto de entrada principal de la aplicación.
     *
     * Se encarga de inicializar el menú y manejar posibles errores de presentación.
     * Si el archivo `characters.json` no se puede acceder, se muestra un mensaje de error
     * y la aplicación se detiene de manera segura.
     *
     * @param args Argumentos de la línea de comandos (no utilizados en esta aplicación).
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
