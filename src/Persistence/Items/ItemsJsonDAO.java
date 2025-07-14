package Persistence.Items;

import Business.Entities.Armor;
import Business.Entities.Weapon;
import Exceptions.PersistenceException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import Business.Entities.Items;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * DAO para persistencia de ítems en archivo JSON.
 * Permite leer la lista completa de ítems desde un archivo local.
 */
public class ItemsJsonDAO implements ItemsDAO {

    private final String path = "./src/Files/items.json";

    public static boolean canConnect() {
        try {
            new FileReader("./src/Files/items.json");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Armor> getAllArmors() throws PersistenceException {
        try {
        FileReader reader = new FileReader(this.path);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return StreamSupport.stream(JsonParser.parseReader(reader).getAsJsonArray().spliterator(), false)
                .filter(e -> "Armor".equals(e.getAsJsonObject().get("class").getAsString()))
                .map(e -> gson.fromJson(e, Armor.class))
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    public List<Weapon> getAllWeapons() throws PersistenceException {
        try {
            FileReader reader = new FileReader(this.path);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return StreamSupport.stream(JsonParser.parseReader(reader).getAsJsonArray().spliterator(), false)
                    .filter(e -> "Weapon".equals(e.getAsJsonObject().get("class").getAsString()))
                    .map(e -> gson.fromJson(e, Weapon.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage());
        }
    }

}