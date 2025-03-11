package Persistence.API;

import edu.salle.url.api.ApiHelper;
import edu.salle.url.api.exception.ApiException;

public class ConnectorAPIHelper {
    private static final String BASE_API_URL = "https://balandrau.salle.url.edu/dpoo/S1_grup_107/";   // URL base de la API.
    private ApiHelper apiHelper;

    public ConnectorAPIHelper() throws ApiException {
        ApiHelper helper = new ApiHelper();
    }

    //Se puede crear una funcion que devuelva la instancia

    public String getRequest(String url) throws ApiException {
        return apiHelper.getFromUrl(BASE_API_URL + url);
    }

    public String postRequest(String url, String body) throws ApiException {
        return apiHelper.postToUrl(BASE_API_URL + url, body);
    }

    public String deleteRequest(String url) throws ApiException {
        return apiHelper.deleteFromUrl(BASE_API_URL + url);
    }

}
