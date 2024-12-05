package Business;

import Business.Entities.Team;
import Persistence.TeamJsonDAO;

import java.util.ArrayList;
import java.util.List;

public class TeamManager {
    private final TeamJsonDAO teamJsonDAO;

    public TeamManager() {
        this.teamJsonDAO = new TeamJsonDAO();
    }

    // Elimina un equipo por nombre
    public boolean eliminateTeam(String name) {
        return teamJsonDAO.eliminateTeam(name);
    }

    // Crea un nuevo equipo
    public boolean createTeam(String name, int id1, int id2, int id3, int id4) {
        Team team = new Team(name, id1, id2, id3, id4);
        return teamJsonDAO.saveTeam(team);
    }

    // Muestra todos los equipos
    public List<Team> showTeams() {
        return teamJsonDAO.getAllTeams();
    }
}