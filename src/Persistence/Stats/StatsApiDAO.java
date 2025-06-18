package Persistence.Stats;

import Business.Entities.Stats;
import Exceptions.PersistenceException;
import Persistence.API.ConnectorAPIHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import edu.salle.url.api.exception.ApiException;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para persistencia de estadísticas vía API.
 * Proporciona métodos para obtener, crear, borrar y actualizar estadísticas.
 */
public class StatsApiDAO implements StatsDAO {

    private final ConnectorAPIHelper apiHelper;

    public StatsApiDAO(ConnectorAPIHelper apiHelper) {
        this.apiHelper = apiHelper;
    }

    @Override
    public List<Stats> getAllStats() throws ApiException {
        String response = apiHelper.getRequest(apiHelper.getId() + "/stats");
        Type statsListType = new TypeToken<ArrayList<Stats>>() {}.getType();
        return new GsonBuilder().setPrettyPrinting().create().fromJson(response, statsListType);
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
        Type statsListType = new TypeToken<ArrayList<Stats>>() {}.getType();
        List<Stats> stats = new GsonBuilder().setPrettyPrinting().create().fromJson(response, statsListType);
        if (stats.isEmpty()) throw new PersistenceException("No stats found for team: " + name);
        return stats.get(0);
    }

    @Override
    public void writeStatsToFile(List<Stats> stats) throws PersistenceException, ApiException {
        Gson gson = new Gson();
        List<Stats> existingStats = getAllStats();
        for (Stats stat : existingStats) deleteOneStats(stat.getName());
        for (Stats stat : stats) {
            String body = gson.toJson(stat);
            String response = apiHelper.postRequest(apiHelper.getId() + "/stats", body);
            if (!response.equals("{\"result\":\"OK\"}"))
                throw new PersistenceException("Stats could not be saved for: " + stat.getName());
        }
    }
}