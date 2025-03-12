package Persistence.DAO;

import Business.Entities.Character;
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
public class TeamJsonDAO {

    private final String path = "./src/Files/teams.json";
    private ConnectorAPIHelper apiHelper;

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
    public List<Team> getAllTeams() throws PersistenceException, ApiException {
        if (apiHelper == null) {
            try (FileReader reader = new FileReader(this.path)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Type teamListType = new TypeToken<ArrayList<Team>>() {
                }.getType();
                return gson.fromJson(reader, teamListType);
            } catch (Exception e) {
                throw new PersistenceException("Error reading teams: " + e.getMessage());
            }
        } else {
            String t = apiHelper.getRequest(apiHelper.getId() + "/teams");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type teamListType = new TypeToken<ArrayList<Team>>() {
            }.getType();
            return gson.fromJson(t, teamListType);
        }
    }

    /**
     * Busca un equipo por su nombre.
     *
     * @param name Nombre del equipo.
     * @return El equipo si existe.
     * @throws PersistenceException Si el equipo no se encuentra.
     */
    public Team getTeam(String name) throws PersistenceException, ApiException {
        if (apiHelper == null) {
            List<Team> teams = getAllTeams();
            for (Team team : teams) {
                if (team.getName().equalsIgnoreCase(name)) {
                    return team;
                }
            }
            throw new PersistenceException("No team found for name " + name);
        } else {
            String t = apiHelper.getRequest(apiHelper.getId() + "/teams?name=" + name);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type teamListType = new TypeToken<ArrayList<Team>>() {
            }.getType();
            List<Team> teams = gson.fromJson(t, teamListType);
            return teams.getFirst();
        }
    }


    /**
     * Guarda un equipo en el archivo JSON.
     *
     * @param team Equipo a agregar.
     * @throws PersistenceException Si ocurre un error al guardar los datos.
     */
    public void saveTeam(Team team) throws PersistenceException, ApiException {
        if (apiHelper == null) {
            try {
                List<Team> teams = getAllTeams();
                teams.add(team);
                writeTeamsToFile(teams);
            } catch (Exception e) {
                throw new PersistenceException(e.getMessage());
            }
        } else {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject teamJson = new JsonObject();
            List<Team> teams = getAllTeams();
            teams.add(team);

            for (Team t : teams) {
                teamJson.addProperty("name", t.getName());

                JsonArray membersArray = new JsonArray();
                for (Member member : t.getMembers()) {
                    JsonObject memberJson = new JsonObject();
                    memberJson.addProperty("id", member.getId());
                    memberJson.addProperty("strategy", member.getStrategy());
                    membersArray.add(memberJson);
                }
                teamJson.add("members", membersArray);
            }

            String body = gson.toJson(teamJson);
            String a = apiHelper.postRequest(apiHelper.getId() + "/teams", body);
            if (!a.equals("{\"result\":\"OK\"}")) {
                throw new PersistenceException("Team could not be saved");
            }
        }
    }

    /**
     * Elimina un equipo del archivo JSON por su nombre.
     *
     * @param name Nombre del equipo a eliminar.
     * @return true si se eliminó correctamente.
     * @throws PersistenceException Si ocurre un error al eliminar el equipo.
     */
    public boolean eliminateTeam(String name) throws PersistenceException, ApiException {
        if (apiHelper == null) {
            try {
                List<Team> teams = getAllTeams();
                teams.removeIf(team -> team.getName().equals(name));
                writeTeamsToFile(teams);
                return true;
            } catch (Exception e) {
                throw new PersistenceException(e.getMessage());
            }
        } else {
            String a = apiHelper.deleteRequest(apiHelper.getId() + "/teams=name" + name);
            if (a.equals("{\"result\":\"OK\"}")) {
                return true;
            } else {
                throw new PersistenceException("Team could not be eliminated");
            }
        }
    }

    /**
     * Escribe la lista de equipos en el archivo JSON.
     *
     * @param teams Lista de equipos a escribir.
     * @throws IOException si ocurre un error al escribir en el archivo.
     */
    private void writeTeamsToFile(List<Team> teams) throws PersistenceException, ApiException {
        if (apiHelper == null) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (FileWriter writer = new FileWriter(this.path)) {
                gson.toJson(teams, writer);
            } catch (IOException e) {
                throw new PersistenceException(e.getMessage());
            }
        } else {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject teamJson = new JsonObject();

            for (Team t : teams) {
                teamJson.addProperty("name", t.getName());

                JsonArray membersArray = new JsonArray();
                for (Member member : t.getMembers()) {
                    JsonObject memberJson = new JsonObject();
                    memberJson.addProperty("id", member.getId());
                    memberJson.addProperty("strategy", member.getStrategy());
                    membersArray.add(memberJson);
                }
                teamJson.add("members", membersArray);
            }
            String body = gson.toJson(teamJson);
            String a = apiHelper.postRequest(apiHelper.getId() + "/teams", body);
            if (!a.equals("{\"result\":\"OK\"}")) {
                throw new PersistenceException("Teams could not be saved");
            }
        }
    }

    public void setApiHelper(ConnectorAPIHelper apiHelper) {
        this.apiHelper = apiHelper;
    }
}
