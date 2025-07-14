package Persistence.Items;

import Business.Entities.Armor;
import Business.Entities.Item;
import Business.Entities.Weapon;
import Persistence.API.ConnectorAPIHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import edu.salle.url.api.exception.ApiException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * DAO para manejar la persistencia de ítems mediante la API.
 * Proporciona método para obtener la lista completa de ítems.
 */
public class ItemsApiDAO implements ItemsDAO {

    private final ConnectorAPIHelper apiHelper;

    public ItemsApiDAO(ConnectorAPIHelper apiHelper) {
        this.apiHelper = apiHelper;
    }

    public List<Armor> getAllArmors() throws ApiException {
        String json = apiHelper.getRequest("shared/items");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return StreamSupport.stream(JsonParser.parseString(json).getAsJsonArray().spliterator(), false)
                .filter(e -> "Armor".equals(e.getAsJsonObject().get("class").getAsString()))
                .map(e -> gson.fromJson(e, Armor.class))
                .collect(Collectors.toList());
    }

    public List<Weapon> getAllWeapons() throws ApiException {
        String json = apiHelper.getRequest("shared/items");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return StreamSupport.stream(JsonParser.parseString(json).getAsJsonArray().spliterator(), false)
                .filter(e -> "Weapon".equals(e.getAsJsonObject().get("class").getAsString()))
                .map(e -> gson.fromJson(e, Weapon.class))
                .collect(Collectors.toList());
    }
}