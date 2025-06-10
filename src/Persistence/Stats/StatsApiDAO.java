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
    public List<Stats> getAllStats() throws ApiException, PersistenceException {
        String response = apiHelper.getRequest(apiHelper.getId() + "/stats");
        System.out.println(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        List<Stats> stats = new ArrayList<>();

        try {
            // First, try to parse as a List
            Type statsListType = new TypeToken<List<Stats>>() {}.getType();
            stats = gson.fromJson(response, statsListType);
        } catch (JsonSyntaxException e) {
            try {
                // If that fails, try to parse as a single Stats object
                Stats singleStat = gson.fromJson(response, Stats.class);
                if (singleStat != null) {
                    stats.add(singleStat);
                }
            } catch (JsonSyntaxException e2) {
                // If both fail, try the original nested array approach
                try {
                    Type nestedType = new TypeToken<List<List<Stats>>>() {}.getType();
                    List<List<Stats>> nested = gson.fromJson(response, nestedType);
                    if (nested != null && !nested.isEmpty()) {
                        stats = nested.get(0);
                    }
                } catch (Exception e3) {
                    throw new PersistenceException("Unable to parse stats response: " + response);
                }
            }
        }

        if (stats == null || stats.isEmpty()) {
            // Return empty list instead of throwing exception
            return new ArrayList<>();
        }
        return stats;
    }

    @Override
    public void deleteOneStats(String name) throws PersistenceException, ApiException {
        String response = apiHelper.deleteRequest(apiHelper.getId() + "/stats?name=" + name);
        if (!response.equals("{\"result\":\"OK\"}")) {
            throw new PersistenceException("Stat could not be deleted.");
        }
    }

    @Override
    public void createEmptyStats(String teamName) throws PersistenceException, ApiException {
        Stats newStat = new Stats(teamName, 0, 0, 0, 0);
        List<Stats> stats = new ArrayList<>();
        stats.add(newStat);
        writeStatsToFile(stats);
    }

    @Override
    public Stats getStat(String name) throws PersistenceException, ApiException {
        String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
        String response = apiHelper.getRequest(apiHelper.getId() + "/stats?name=" + encodedName);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type statsListType = new TypeToken<ArrayList<Stats>>() {}.getType();
        List<Stats> stats = gson.fromJson(response, statsListType);

        if (stats.isEmpty()) {
            throw new PersistenceException("No stats found for team: " + name);
        }
        return stats.get(0);
    }

    @Override
    public void writeStatsToFile(List<Stats> stats) throws PersistenceException, ApiException {
        Gson gson = new Gson();
        String body;
        if (stats.size() == 1) {
            body = gson.toJson(stats.getFirst());
        } else {
            body = gson.toJson(stats);
        }
        String response = apiHelper.postRequest(apiHelper.getId() + "/stats", body);
        if (!response.equals("{\"result\":\"OK\"}")) {
            throw new PersistenceException("Stats could not be saved.");
        }
    }
}
