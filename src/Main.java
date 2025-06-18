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

    /**
     * Restaura datos en la API para un grupo dado, eliminando equipos y estadísticas actuales
     * y creando equipos y estadísticas predefinidas.
     *
     * @param groupId Identificador del grupo para la URL de la API.
     * @throws Exception Si ocurre un error en las conexiones o peticiones HTTP.
     */
    public static void restoreBroApiData(String groupId) throws Exception {
        final String API = "https://balandrau.salle.url.edu/dpoo/" + groupId;

        // --- Remove all teams ---
        while (getCount(API + "/teams") > 0) {
            sendDelete(API + "/teams/0");
        }

        // --- Add teams with updated "Salle Fest Bois" strategies ---
        sendPost(API + "/teams", """
                {
                  "name": "Salle Fest Bois",
                  "members": [
                    { "id": 5431512, "strategy": "balanced" },
                    { "id": 467416287, "strategy": "offensive" },
                    { "id": 4264041, "strategy": "defensive" },
                    { "id": 748738, "strategy": "sniper" }
                  ]
                }
                """);
        sendPost(API + "/teams", """
                {
                  "name": "Technova",
                  "members": [
                    { "id": 68053, "strategy": "balanced" },
                    { "id": 68053, "strategy": "balanced" },
                    { "id": 68053, "strategy": "balanced" },
                    { "id": 68053, "strategy": "balanced" }
                  ]
                }
                """);
        sendPost(API + "/teams", """
                {
                  "name": "DPOO All-star",
                  "members": [
                    { "id": 467416287, "strategy": "balanced" },
                    { "id": 4115637091, "strategy": "balanced" },
                    { "id": 73237806, "strategy": "balanced" },
                    { "id": 748738, "strategy": "balanced" }
                  ]
                }
                """);

        // --- Remove all stats ---
        while (getCount(API + "/stats") > 0) {
            sendDelete(API + "/stats/0");
        }

        // --- Add stats ---
        sendPost(API + "/stats", """
                {
                  "name": "Salle Fest Bois",
                  "games_played": 6,
                  "games_won": 4,
                  "KO_done": 19,
                  "KO_received": 17
                }
                """);
        sendPost(API + "/stats", """
                {
                  "name": "Technova",
                  "games_played": 4,
                  "games_won": 1,
                  "KO_done": 7,
                  "KO_received": 14
                }
                """);
        sendPost(API + "/stats", """
                {
                  "name": "DPOO All-star",
                  "games_played": 8,
                  "games_won": 2,
                  "KO_done": 8,
                  "KO_received": 19
                }
                """);

        System.out.println("Restoration done!");
    }

    /**
     * Obtiene la cantidad de objetos (equipos o stats) en la URL dada consultando la API.
     *
     * @param url URL para obtener la cantidad de elementos.
     * @return Número de objetos en la respuesta JSON.
     * @throws Exception Si ocurre un error en la conexión o lectura.
     */
    private static int getCount(String url) throws Exception {
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestMethod("GET");
        int status = con.getResponseCode();
        if (status != 200) return 0;
        String resp = new String(con.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        // Cuenta básica de objetos JSON: divide por "{" y resta 1 (por el [ inicial)
        return resp.split("\\{").length - 1;
    }

    /**
     * Envía una petición DELETE a la URL especificada.
     *
     * @param url URL destino para la petición DELETE.
     * @throws Exception Si ocurre un error en la conexión.
     */
    private static void sendDelete(String url) throws Exception {
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestMethod("DELETE");
        int status = con.getResponseCode();
        System.out.println("DELETE " + url + " HTTP " + status);
    }

    /**
     * Envía una petición POST con contenido JSON a la URL especificada.
     *
     * @param url  URL destino para la petición POST.
     * @param json Contenido JSON que se enviará en el cuerpo de la petición.
     * @throws Exception Si ocurre un error en la conexión o escritura.
     */
    private static void sendPost(String url, String json) throws Exception {
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/json");
        try (OutputStream os = con.getOutputStream()) {
            os.write(json.getBytes(StandardCharsets.UTF_8));
        }
        int status = con.getResponseCode();
        System.out.println("POST " + url + " HTTP " + status);
    }
}