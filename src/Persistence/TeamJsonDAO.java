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

public class TeamJsonDAO {

    private final String path = "./src/Files/teams.json";

    /**
     * Verifica si el archivo JSON de equipos existe. Si no existe, lo crea con un contenido vacío.
     *
     * @throws PersistenceException si ocurre un error al crear el archivo.
     */
    public void verifyJsonTeams() throws PersistenceException {
        File file = new File(this.path);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                try (FileWriter writer = new FileWriter(this.path)) {
                    writer.write("[]");
                }
            } catch (IOException e) {
                throw new PersistenceException(e.getMessage());
            }
        }
    }

    /**
     * Obtiene todos los equipos almacenados en el archivo JSON.
     *
     * @return Lista de equipos.
     */
    public List<Team> getAllTeams() {
        try (FileReader reader = new FileReader(this.path)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type teamListType = new TypeToken<ArrayList<Team>>() {}.getType();
            return gson.fromJson(reader, teamListType);
        } catch (IOException e) {
            throw new RuntimeException("Error reading teams: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene un equipo específico por su nombre.
     *
     * @param name Nombre del equipo a buscar.
     * @return El equipo encontrado o null si no existe.
     */
    public Team getTeam(String name) {
        return getAllTeams().stream()
                .filter(team -> team.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Guarda un equipo en el archivo JSON.
     *
     * @param team Equipo a guardar.
     * @return true si se guardó correctamente, false en caso contrario.
     */
    public boolean saveTeam(Team team) {
        try {
            List<Team> teams = getAllTeams();
            teams.add(team);
            writeTeamsToFile(teams);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Elimina un equipo del archivo JSON por su nombre.
     *
     * @param name Nombre del equipo a eliminar.
     * @return true si se eliminó correctamente, false en caso contrario.
     */
    public boolean eliminateTeam(String name) {
        try {
            List<Team> teams = getAllTeams();
            teams.removeIf(team -> team.getName().equals(name));
            writeTeamsToFile(teams);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Escribe la lista de equipos en el archivo JSON.
     *
     * @param teams Lista de equipos a escribir.
     * @throws IOException si ocurre un error al escribir en el archivo.
     */
    private void writeTeamsToFile(List<Team> teams) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(this.path)) {
            gson.toJson(teams, writer);
        }
    }
}
