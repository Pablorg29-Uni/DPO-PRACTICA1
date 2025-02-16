package Business;

import Business.Entities.Member;
import Business.Entities.Team;
import Exceptions.BusinessException;
import Exceptions.PersistenceException;
import Persistence.TeamJsonDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeamManager {
    private final TeamJsonDAO teamJsonDAO;

    public TeamManager() {
        this.teamJsonDAO = new TeamJsonDAO();
    }

    public void verify () throws BusinessException {
        try {
            teamJsonDAO.verifyJsonTeams();
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    // Elimina un equipo por nombre
    public boolean eliminateTeam(String name) throws BusinessException {
        try {
            StatsManager statsManager = new StatsManager();
            statsManager.deleteStat(name);
            return teamJsonDAO.eliminateTeam(name);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    // Crea un nuevo equipo
    public void createTeam(String name, long id1, long id2, long id3, long id4) throws BusinessException {
        try {
            Team team = new Team(name, id1, id2, id3, id4);
            StatsManager statsManager = new StatsManager();
            statsManager.createStat(name);
            teamJsonDAO.saveTeam(team);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    // Muestra todos los equipos
    public List<Team> showTeams() throws BusinessException {
        try {
            return teamJsonDAO.getAllTeams();
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public Team getTeam(String name) throws BusinessException {
        try {
            return teamJsonDAO.getTeam(name);
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public ArrayList<Team> teamsWithPlayer(long id) throws BusinessException {
        ArrayList<Team> matchingTeams = new ArrayList<>();
        List<Team> teams;
        try {
            teams = teamJsonDAO.getAllTeams();
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
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