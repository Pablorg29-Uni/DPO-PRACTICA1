package Persistence.Stats;

import Business.Entities.Stats;
import Exceptions.PersistenceException;
import edu.salle.url.api.exception.ApiException;

import java.util.List;

public interface StatsDAO {

    void verifyJsonStats() throws PersistenceException;


    List<Stats> getAllStats() throws PersistenceException, ApiException;


    void deleteOneStats(String name) throws PersistenceException, ApiException;


    void createEmptyStats(String teamName) throws PersistenceException, ApiException;


    Stats getStat(String name) throws PersistenceException, ApiException;


    void writeStatsToFile(List<Stats> stats) throws PersistenceException, ApiException;
}
