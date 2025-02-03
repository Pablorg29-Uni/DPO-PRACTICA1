package Business;

import Business.Entities.Member;
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
    public boolean createTeam(String name, long id1, long id2, long id3, long id4) {
        Team team = new Team(name, id1, id2, id3, id4);
        return teamJsonDAO.saveTeam(team);
    }

    // Muestra todos los equipos
    public List<Team> showTeams() {
        return teamJsonDAO.getAllTeams();
    }

    public Team getTeam(String name) {
        return teamJsonDAO.getTeam(name);
    }

    public ArrayList<Team> teamsWithPlayer(long id) {
        ArrayList<Team> matchingTeams = new ArrayList<>();
        List<Team> teams = teamJsonDAO.getAllTeams();
        for (Team team : teams) {
            for (Member member : team.getMembers()) {
                if (member.getId() == id) {
                    matchingTeams.add(team);
                    break;
                }
            }
        }
        return matchingTeams;
    }
}