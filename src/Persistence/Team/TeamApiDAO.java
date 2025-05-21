package Persistence.Team;

import Business.Entities.Team;
import Persistence.API.ConnectorAPIHelper;
import Exceptions.PersistenceException;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import edu.salle.url.api.exception.ApiException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class TeamApiDAO implements TeamDAO {

    private final ConnectorAPIHelper apiHelper;

    public TeamApiDAO(ConnectorAPIHelper apiHelper) {
        this.apiHelper = apiHelper;
    }

    @Override
    public List<Team> getAllTeams() throws ApiException {
        String response = apiHelper.getRequest(apiHelper.getId() + "/teams");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonArray = JsonParser.parseString(response).getAsJsonArray();

        List<Team> teams = new ArrayList<>();
        for (JsonElement elt : jsonArray) {
            if (elt.isJsonArray()) {
                for (JsonElement inner : elt.getAsJsonArray()) {
                    teams.add(gson.fromJson(inner, Team.class));
                }
            } else if (elt.isJsonObject()) {
                teams.add(gson.fromJson(elt, Team.class));
            } else {
                System.out.println("Skipping unexpected element: " + elt);
            }
        }
        return teams;
    }

    @Override
    public Team getTeam(String name) throws ApiException {
        String response = apiHelper.getRequest(apiHelper.getId() + "/teams?name=" + name);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type teamListType = new TypeToken<ArrayList<Team>>() {
        }.getType();
        List<Team> teams = gson.fromJson(response, teamListType);
        return teams.get(0);
    }

    @Override
    public void saveTeam(Team team) throws PersistenceException, ApiException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String body = gson.toJson(team);
        String response = apiHelper.postRequest(apiHelper.getId() + "/teams", body);
        if (!response.equals("{\"result\":\"OK\"}")) {
            throw new PersistenceException("Team could not be saved");
        }
    }

    @Override
    public boolean eliminateTeam(String name) throws PersistenceException, ApiException {
        String response = apiHelper.deleteRequest(apiHelper.getId() + "/teams?name=" + name);
        if (response.equals("{\"result\":\"OK\"}")) {
            return true;
        } else {
            throw new PersistenceException("Team could not be eliminated");
        }
    }
}
