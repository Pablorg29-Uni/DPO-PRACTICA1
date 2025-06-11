package Persistence.Team;

import Business.Entities.Team;
import Persistence.API.ConnectorAPIHelper;
import Exceptions.PersistenceException;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import edu.salle.url.api.exception.ApiException;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class TeamApiDAO implements TeamDAO {

    private final ConnectorAPIHelper apiHelper;

    public TeamApiDAO(ConnectorAPIHelper apiHelper) {
        this.apiHelper = apiHelper;
    }

    @Override
    public List<Team> getAllTeams() throws ApiException {
        String response = apiHelper.getRequest(apiHelper.getId() + "/teams");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement jsonElement = JsonParser.parseString(response);

        List<Team> teams = new ArrayList<>();

        if (jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();

            for (JsonElement element : jsonArray) {
                if (element.isJsonObject()) {
                    // It's a team object
                    Team team = gson.fromJson(element, Team.class);
                    if (team != null && team.getName() != null && !team.getName().isEmpty()) {
                        teams.add(team);
                    }
                } else if (element.isJsonArray()) {
                    // It's a nested array - process its contents
                    JsonArray nestedArray = element.getAsJsonArray();
                    for (JsonElement nestedElement : nestedArray) {
                        if (nestedElement.isJsonObject()) {
                            Team team = gson.fromJson(nestedElement, Team.class);
                            if (team != null && team.getName() != null && !team.getName().isEmpty()) {
                                teams.add(team);
                            }
                        }
                    }
                }
            }
        }

        // Remove duplicates based on team name
        return teams.stream()
                .collect(Collectors.toMap(
                        Team::getName,
                        t -> t,
                        (existing, replacement) -> existing
                ))
                .values()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public Team getTeam(String name) throws ApiException, PersistenceException {
        String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
        String response = apiHelper.getRequest(apiHelper.getId() + "/teams?name=" + encodedName);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            // Try parsing as array first
            Type teamListType = new TypeToken<ArrayList<Team>>() {
            }.getType();
            List<Team> teams = gson.fromJson(response, teamListType);

            if (teams != null && !teams.isEmpty()) {
                Team team = teams.get(0);
                return team;
            }
        } catch (JsonSyntaxException e) {
            // Try parsing as single object
            try {
                Team team = gson.fromJson(response, Team.class);
                if (team != null) {
                    return team;
                }
            } catch (JsonSyntaxException e2) {
                throw new PersistenceException(e2.getMessage());
            }
        }

        return null;
    }

    @Override
    public void saveTeam(Team team) throws PersistenceException, ApiException {
        // Try sending just the new team
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String body = gson.toJson(team); // Just the single team, not an array
        String pattern = ",\\s*\"malRebut\": 0\\.0,\\s*\"isKO\": false,\\s*\"damageReduction\": 0\\.0";
        body = body.replaceAll(pattern, "");

        String response = apiHelper.postRequest(apiHelper.getId() + "/teams", body);

        if (!response.equals("{\"result\":\"OK\"}")) {
            throw new PersistenceException("Team could not be saved");
        }
    }


    @Override
    public boolean eliminateTeam(String name) throws PersistenceException, ApiException {
        String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
        String response = apiHelper.deleteRequest(apiHelper.getId() + "/teams?name=" + encodedName);
        if (response.equals("{\"result\":\"OK\"}")) {
            return true;
        } else {
            throw new PersistenceException("Team could not be eliminated");
        }
    }
}
