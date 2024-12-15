package Persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import Business.Entities.Items;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ItemsJsonDAO {

    private final String path = "./src/Files/items.json";

    public void verifyJsonItem() {
        try {
            FileReader fileReader = new FileReader(this.path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
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

    public Items getItem(long id) {
        List<Items> items = getAllItems();
        for (Items item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        throw new RuntimeException("No item with id " + id);
    }
}