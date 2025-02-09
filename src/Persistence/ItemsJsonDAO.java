package Persistence;

import Exceptions.PersistenceException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import Business.Entities.Items;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ItemsJsonDAO {

    private final String path = "./src/Files/items.json";

    public void verifyJsonItem() throws PersistenceException {
        try {
            FileReader fileReader = new FileReader(this.path);
        } catch (FileNotFoundException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    public List<Items> getAllItems() {
        try {
            FileReader reader = new FileReader(this.path);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type itemType = new TypeToken<ArrayList<Items>>() {
            }.getType();
            return gson.fromJson(reader, itemType);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    /*
    public void setDurability(long id, int durability) {
        List<Items> items = getAllItems();
        boolean found = false;

        for (Items item : items) {
            if (item.getId() == id) {
                item.setDurability(durability);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new RuntimeException("No item with id " + id);
        }
        
        try (FileWriter writer = new FileWriter(path)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(items, writer);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to JSON file: " + e.getMessage());
        }
    }
    */
}