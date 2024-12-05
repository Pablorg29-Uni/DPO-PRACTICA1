package Persistence;

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

    // Verifica si el archivo JSON existe, y lo crea si no existe
    public void verifyJsonTeams() {
        File file = new File(this.path);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs(); // Crea directorios si no existen
                file.createNewFile();
                try (FileWriter writer = new FileWriter(this.path)) {
                    writer.write("[]"); // Inicializa un JSON vacío
                }
            } catch (IOException e) {
                throw new RuntimeException("Error creating the JSON file: " + e.getMessage(), e);
            }
        }
    }

    // Obtiene todos los equipos desde el archivo JSON
    public List<Team> getAllTeams() {
        verifyJsonTeams(); // Asegura que el archivo exista
        try (FileReader reader = new FileReader(this.path)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type teamListType = new TypeToken<ArrayList<Team>>() {}.getType();
            return gson.fromJson(reader, teamListType);
        } catch (IOException e) {
            throw new RuntimeException("Error reading teams: " + e.getMessage(), e);
        }
    }

    // Busca un equipo por nombre
    public Team getTeam(String name) {
        return getAllTeams().stream()
                .filter(team -> team.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    // Guarda un nuevo equipo
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

    // Elimina un equipo por nombre
    public boolean eliminateTeam(String name) {
        try {
            List<Team> teams = getAllTeams();
            Team teamToRemove = getTeam(name);

            if (teamToRemove == null) {
                System.out.println("Error: Team not found (" + name + ").");
                return false;
            }

            teams.remove(teamToRemove);
            writeTeamsToFile(teams);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método auxiliar para escribir los equipos en el archivo JSON
    private void writeTeamsToFile(List<Team> teams) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(this.path)) {
            gson.toJson(teams, writer);
        }
    }
}