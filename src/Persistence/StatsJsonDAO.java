package Persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import Business.Entities.Stats;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StatsJsonDAO {

    private final String path = "./src/Files/stats.json";

    public void verifyJsonStats() {
        try {
            FileReader fileReader = new FileReader(this.path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Stats> getAllStats() {
        try {
            FileReader reader = new FileReader(this.path);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type statsType = new TypeToken<ArrayList<Stats>>() {}.getType();
            return gson.fromJson(reader, statsType);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Stats getStat(String name) {
        List<Stats> stats = getAllStats();
        for (Stats stat : stats) {
            if (stat.getName().equals(name)) {
                return stat;
            }
        }
        throw new RuntimeException("No stats found for name " + name);
    }
}