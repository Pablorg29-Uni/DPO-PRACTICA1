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

    public void verifyJsonTeams() {
        try {
            new FileReader(this.path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Team> getAllTeams() {
        try (FileReader reader = new FileReader(this.path)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type teamListType = new TypeToken<ArrayList<Team>>() {
            }.getType();
            return gson.fromJson(reader, teamListType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Team getTeam(String name) {
        List<Team> teams = getAllTeams();
        for (Team t : teams) {
            if (t.getName().equals(name)) {
                return t;
            }
        }
        return null;
    }

    public void saveTeam(Team team) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<Team> teams = getAllTeams();
            teams.add(team);
            try (FileWriter writer = new FileWriter(this.path)) {
                gson.toJson(teams, writer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void eliminateTeam(String name) {
        try {
            List<Team> teams = getAllTeams();
            boolean done = teams.remove(getTeam(name));
            if (!done) {
                throw new RuntimeException("Team not found");
            }
            FileWriter writer = new FileWriter(this.path);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(teams, writer);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}