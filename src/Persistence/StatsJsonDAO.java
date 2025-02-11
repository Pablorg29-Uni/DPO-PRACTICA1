package Persistence;

import Business.Entities.Team;
import Exceptions.PersistenceException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import Business.Entities.Stats;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StatsJsonDAO {

    private final String path = "./src/Files/stats.json";

    public void verifyJsonStats() throws PersistenceException {
        try {
            FileReader fileReader = new FileReader(this.path);
        } catch (FileNotFoundException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    public void deleteOneStats(String name) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<Stats> stats = getAllStats();
            stats.removeIf(stat -> name.equals(stat.getName())); //cosa rara del intelliJ
            try (FileWriter writer = new FileWriter(this.path)) {
                gson.toJson(stats, writer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public List<Stats> getAllStats() {
        try {
            FileReader reader = new FileReader(this.path);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type statsType = new TypeToken<ArrayList<Stats>>() {
            }.getType();
            return gson.fromJson(reader, statsType);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void createEmptyStats(String teamName) throws PersistenceException {
        List<Stats> stats = getAllStats();
        Stats s = new Stats(teamName, 0, 0, 0, 0);
        stats.add(s);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(this.path)) {
            gson.toJson(stats, writer);
        } catch (IOException e) {
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