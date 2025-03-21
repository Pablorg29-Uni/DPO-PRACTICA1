package Persistence.Team;


import Business.Entities.Team;
import Exceptions.PersistenceException;
import edu.salle.url.api.exception.ApiException;
import java.util.List;

public interface TeamDAO {

    List<Team> getAllTeams() throws PersistenceException, ApiException;


    Team getTeam(String name) throws PersistenceException, ApiException;


    void saveTeam(Team team) throws PersistenceException, ApiException;


    boolean eliminateTeam(String name) throws PersistenceException, ApiException;

}
