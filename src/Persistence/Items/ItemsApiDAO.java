package Persistence.Items;

import Business.Entities.Items;
import Exceptions.PersistenceException;
import Persistence.API.ConnectorAPIHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import edu.salle.url.api.exception.ApiException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para manejar la persistencia de ítems mediante la API.
 * Proporciona método para obtener la lista completa de ítems.
 */
public class ItemsApiDAO implements ItemsDAO {

    private final ConnectorAPIHelper apiHelper;

    public ItemsApiDAO(ConnectorAPIHelper apiHelper) {
        this.apiHelper = apiHelper;
    }

    @Override
    public List<Items> getAllItems() throws ApiException {
        String itemsJson = apiHelper.getRequest("shared/items");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type itemListType = new TypeToken<ArrayList<Items>>() {}.getType();
        return gson.fromJson(itemsJson, itemListType);
    }
}