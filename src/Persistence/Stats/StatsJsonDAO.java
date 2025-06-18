package Persistence.Stats;

import Exceptions.PersistenceException;
import Persistence.API.ConnectorAPIHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import Business.Entities.Stats;
import edu.salle.url.api.exception.ApiException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para persistencia de estadísticas en archivo JSON.
 */
public class StatsJsonDAO implements StatsDAO {

    private final String path = "./src/Files/stats.json";

    /**
     * Verifica si el archivo JSON es accesible.
     * @return true si puede leerse, false si no.
     */
    public static boolean canConnect () {
        try {
            new FileReader("./src/Files/stats.json");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Stats> getAllStats() throws PersistenceException {
        try (FileReader reader = new FileReader(this.path)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type statsType = new TypeToken<ArrayList<Stats>>() {}.getType();
            return gson.fromJson(reader, statsType);
        } catch (Exception e) {
            throw new PersistenceException("Error reading stats file: " + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteOneStats(String name) throws PersistenceException {
        List<Stats> stats = getAllStats();
        stats.removeIf(stat -> name.equals(stat.getName()));
        writeStatsToFile(stats);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createEmptyStats(String teamName) throws PersistenceException {
        List<Stats> stats = getAllStats();
        stats.add(new Stats(teamName, 0, 0, 0, 0));
        writeStatsToFile(stats);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stats getStat(String name) throws PersistenceException {
        List<Stats> stats = getAllStats();
        return stats.stream()
                .filter(stat -> stat.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new PersistenceException("No stats found for team: " + name));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeStatsToFile(List<Stats> stats) throws PersistenceException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(this.path)) {
            gson.toJson(stats, writer);
        } catch (IOException e) {
            throw new PersistenceException("Error writing stats to file: " + e.getMessage());
        }
    }
}