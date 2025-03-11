package Persistence.API;

import edu.salle.url.api.ApiHelper;
import edu.salle.url.api.exception.ApiException;

public class ConnectorAPIHelper {
    private static final String BASE_API_URL = "https://balandrau.salle.url.edu/dpoo/";   // URL base de la API.
    private final ApiHelper apiHelper;

    public ConnectorAPIHelper() throws ApiException {
        this.apiHelper = new ApiHelper();
    }

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
