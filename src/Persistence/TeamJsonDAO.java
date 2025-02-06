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


    public List<Team> getAllTeams() {
        try (FileReader reader = new FileReader(this.path)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type teamListType = new TypeToken<ArrayList<Team>>() {}.getType();
            List<Team> teams = gson.fromJson(reader, teamListType);
            return teams;
        } catch (IOException e) {
            throw new RuntimeException("Error reading teams: " + e.getMessage(), e);
        }
    }

    public Team getTeam(String name) {
        return getAllTeams().stream()
                .filter(team -> team.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }


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


    public boolean eliminateTeam(String name) {
        try {
            List<Team> teams = getAllTeams();
            teams.removeIf(team -> team.getName().equals(name));
            /*
            Team teamToRemove = getTeam(name);

            if (teamToRemove == null) {
                System.out.println("Error: Team not found (" + name + ").");
                return false;
            }

            teams.remove(teamToRemove);
            writeTeamsToFile(teams);*/
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (FileWriter writer = new FileWriter(this.path)) {
                gson.toJson(teams, writer);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void writeTeamsToFile(List<Team> teams) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(this.path)) {
            gson.toJson(teams, writer);
        }
    }
}