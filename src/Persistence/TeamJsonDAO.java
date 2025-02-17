package Persistence;

import Exceptions.PersistenceException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import Business.Entities.Team;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
/**
 * Maneja la persistencia de los equipos en un archivo JSON.
 */
public class TeamJsonDAO {

    private final String path = "./src/Files/teams.json";

    /**
     * Verifica si el archivo JSON de equipos existe. Si no existe, lo crea con un contenido vacío.
     *
     * @throws PersistenceException si ocurre un error al crear el archivo.
     */
    public void verifyJsonTeams() throws PersistenceException {
        try {
            FileReader fileReader = new FileReader(this.path);
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    /**
     * Carga todos los equipos desde el archivo JSON.
     *
     * @return Lista de equipos.
     * @throws PersistenceException Si ocurre un error al leer el archivo.
     */
    public List<Team> getAllTeams() throws PersistenceException {
        try (FileReader reader = new FileReader(this.path)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type teamListType = new TypeToken<ArrayList<Team>>() {
            }.getType();
            return gson.fromJson(reader, teamListType);
        } catch (Exception e) {
            throw new PersistenceException("Error reading teams: " + e.getMessage());
        }
    }

    /**
     * Busca un equipo por su nombre.
     *
     * @param name Nombre del equipo.
     * @return El equipo si existe.
     * @throws PersistenceException Si el equipo no se encuentra.
     */
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
     * Guarda un equipo en el archivo JSON.
     *
     * @param team Equipo a agregar.
     * @throws PersistenceException Si ocurre un error al guardar los datos.
     */
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
     * Elimina un equipo del archivo JSON por su nombre.
     *
     * @param name Nombre del equipo a eliminar.
     * @throws PersistenceException Si ocurre un error al eliminar el equipo.
     * @return true si se eliminó correctamente.
     */
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
     * Escribe la lista de equipos en el archivo JSON.
     *
     * @param teams Lista de equipos a escribir.
     * @throws IOException si ocurre un error al escribir en el archivo.
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
