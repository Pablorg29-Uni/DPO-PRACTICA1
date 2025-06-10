package Persistence.Team;

import Business.Entities.Member;
import Exceptions.PersistenceException;
import Persistence.API.ConnectorAPIHelper;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import Business.Entities.Team;
import edu.salle.url.api.exception.ApiException;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Maneja la persistencia de los equipos en un archivo JSON.
 */
public class TeamJsonDAO implements TeamDAO {

    private final String path = "./src/Files/teams.json";

    public static boolean canConnect () {
        try {
            new FileReader("./src/Files/teams.json");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Team> getAllTeams() throws PersistenceException {
        try (FileReader reader = new FileReader(this.path)) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            Type teamListType = new TypeToken<ArrayList<Team>>() {}.getType();
            return gson.fromJson(reader, teamListType);
        } catch (Exception e) {
            throw new PersistenceException("Error reading teams: " + e.getMessage());
        }
    }

    @Override
    public Team getTeam(String name) throws PersistenceException {
        List<Team> teams = getAllTeams();
        for (Team team : teams) {
            if (team.getName().equalsIgnoreCase(name)) {
                return team;
            }
        }
        throw new PersistenceException("No team found for name " + name);
    }

    @Override
    public void saveTeam(Team team) throws PersistenceException {
        try {
            List<Team> teams = getAllTeams();
            teams.add(team);
            writeTeamsToFile(teams);
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public boolean eliminateTeam(String name) throws PersistenceException {
        try {
            List<Team> teams = getAllTeams();
            teams.removeIf(team -> team.getName().equals(name));
            writeTeamsToFile(teams);
            return true;
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    private void writeTeamsToFile(List<Team> teams) throws PersistenceException {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(this.path)) {
            gson.toJson(teams, writer);
        } catch (IOException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

}
