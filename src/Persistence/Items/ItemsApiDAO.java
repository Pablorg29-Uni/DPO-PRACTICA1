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
 * Maneja la persistencia de los ítems a través de la API.
 */
public class ItemsApiDAO implements ItemsDAO {

    private final ConnectorAPIHelper apiHelper;

    public ItemsApiDAO(ConnectorAPIHelper apiHelper) {
        this.apiHelper = apiHelper;
    }

    /**
     * Obtiene la lista de todos los ítems desde la API.
     *
     * @return Lista de ítems disponibles.
     * @throws PersistenceException Si ocurre un error en la conexión con la API.
     */
    @Override
    public List<Items> getAllItems() throws ApiException {
        String itemsJson = apiHelper.getRequest("shared/items");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type itemListType = new TypeToken<ArrayList<Items>>() {}.getType();
        return gson.fromJson(itemsJson, itemListType);
    }
}
