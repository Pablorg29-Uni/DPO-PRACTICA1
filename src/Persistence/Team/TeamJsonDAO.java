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
 * DAO para la persistencia de equipos en un archivo JSON.
 * Implementa los métodos para obtener, guardar y eliminar equipos localmente.
 */
public class TeamJsonDAO implements TeamDAO {

    private final String path = "./src/Files/teams.json";

    /**
     * Comprueba si el archivo de persistencia de equipos está accesible.
     *
     * @return true si se puede acceder al archivo, false en caso contrario.
     */
    public static boolean canConnect() {
        try {
            new FileReader("./src/Files/teams.json");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Team> getAllTeams() throws PersistenceException {
        try (FileReader reader = new FileReader(this.path)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type teamListType = new TypeToken<ArrayList<Team>>() {}.getType();
            return gson.fromJson(reader, teamListType);
        } catch (Exception e) {
            throw new PersistenceException("Error reading teams: " + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * Escribe la lista completa de equipos al archivo JSON.
     *
     * @param teams Lista de equipos a guardar.
     * @throws PersistenceException Si ocurre un error al escribir en el archivo.
     */
    private void writeTeamsToFile(List<Team> teams) throws PersistenceException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(this.path)) {
            gson.toJson(teams, writer);
        } catch (IOException e) {
            throw new PersistenceException(e.getMessage());
        }
    }
}