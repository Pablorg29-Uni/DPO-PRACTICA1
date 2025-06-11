package Persistence.Stats;

import Business.Entities.Stats;
import Exceptions.PersistenceException;
import Persistence.API.ConnectorAPIHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import edu.salle.url.api.exception.ApiException;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Maneja la persistencia de estadísticas a través de la API.
 */
public class StatsApiDAO implements StatsDAO {

    private final ConnectorAPIHelper apiHelper;

    public StatsApiDAO(ConnectorAPIHelper apiHelper) {
        this.apiHelper = apiHelper;
    }

    @Override
    public List<Stats> getAllStats() throws ApiException {
        String response = apiHelper.getRequest(apiHelper.getId() + "/stats");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type statsListType = new TypeToken<ArrayList<Stats>>() {
        }.getType();
        return gson.fromJson(response, statsListType);
    }

    @Override
    public void deleteOneStats(String name) throws PersistenceException, ApiException {
        String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
        String response = apiHelper.deleteRequest(apiHelper.getId() + "/stats?name=" + encodedName);
        if (!response.equals("{\"result\":\"OK\"}")) {
            throw new PersistenceException("Stat could not be deleted.");
        }
    }


    @Override
    public void createEmptyStats(String teamName) throws PersistenceException, ApiException {
        Stats newStat = new Stats(teamName, 0, 0, 0, 0);
        List<Stats> stats = getAllStats();
        stats.add(newStat);
        writeStatsToFile(stats);
    }

    @Override
    public Stats getStat(String name) throws PersistenceException, ApiException {
        String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
        String response = apiHelper.getRequest(apiHelper.getId() + "/stats?name=" + encodedName);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type statsListType = new TypeToken<ArrayList<Stats>>() {
        }.getType();
        List<Stats> stats = gson.fromJson(response, statsListType);

        if (stats.isEmpty()) {
            throw new PersistenceException("No stats found for team: " + name);
        }
        return stats.get(0);
    }

    @Override
    public void writeStatsToFile(List<Stats> stats) throws PersistenceException, ApiException {
        Gson gson = new Gson();

        List<Stats> existingStats = getAllStats();
        for (Stats stat : existingStats) {
            deleteOneStats(stat.getName());
        }

        for (Stats stat : stats) {
            String body = gson.toJson(stat);  // Individual stat object
            String response = apiHelper.postRequest(apiHelper.getId() + "/stats", body);
            if (!response.equals("{\"result\":\"OK\"}")) {
                throw new PersistenceException("Stats could not be saved for: " + stat.getName());
            }
        }
    }
}
