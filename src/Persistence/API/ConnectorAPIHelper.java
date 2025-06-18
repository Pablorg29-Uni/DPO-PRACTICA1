package Persistence.API;

import edu.salle.url.api.ApiHelper;
import edu.salle.url.api.exception.ApiException;

/**
 * Helper para conectar con la API REST usando ApiHelper.
 * Facilita GET, POST y DELETE con una URL base fija y manejo de excepciones ApiException.
 */
public class ConnectorAPIHelper {
    private static final String BASE_API_URL = "https://balandrau.salle.url.edu/dpoo/";   // URL base de la API.
    private final ApiHelper apiHelper;

    /**
     * Constructor que inicializa el ApiHelper.
     *
     * @throws ApiException Si hay error al crear ApiHelper.
     */
    public ConnectorAPIHelper() throws ApiException {
        this.apiHelper = new ApiHelper();
    }

    /**
     * Realiza una petición GET a la API usando la URL relativa.
     *
     * @param url URL relativa (sin la base).
     * @return Respuesta en formato String.
     * @throws ApiException Si la petición falla.
     */
    public String getRequest(String url) throws ApiException {
        return apiHelper.getFromUrl(BASE_API_URL + url);
    }

    /**
     * Realiza una petición POST a la API con un cuerpo JSON.
     *
     * @param url  URL relativa.
     * @param body Cuerpo de la petición en JSON.
     * @return Respuesta en formato String.
     * @throws ApiException Si la petición falla.
     */
    public String postRequest(String url, String body) throws ApiException {
        return apiHelper.postToUrl(BASE_API_URL + url, body);
    }

    /**
     * Realiza una petición DELETE a la API usando la URL relativa.
     *
     * @param url URL relativa.
     * @return Respuesta en formato String.
     * @throws ApiException Si la petición falla.
     */
    public String deleteRequest(String url) throws ApiException {
        return apiHelper.deleteFromUrl(BASE_API_URL + url);
    }

    /**
     * Devuelve el identificador del grupo para la API.
     *
     * @return ID del grupo.
     */
    public String getId() {
        return "S1-Project_29";
    }

}