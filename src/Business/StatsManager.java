package Business;

import Business.Entities.Stats;
import Exceptions.BusinessException;
import Exceptions.PersistenceException;
import Persistence.StatsJsonDAO;
import java.util.ArrayList;

public class StatsManager {
    private final StatsJsonDAO statsJsonDAO;

    public StatsManager() {
        this.statsJsonDAO = new StatsJsonDAO();
    }

    public ArrayList<Stats> getStats() {
        return (ArrayList<Stats>) statsJsonDAO.getAllStats();
    }

    public void deleteStat (String name) {
        statsJsonDAO.deleteOneStats(name);
    }

    public Stats getStat(String name) {
        return statsJsonDAO.getStat(name);
    }

    public void verify() throws BusinessException {
        try {
            statsJsonDAO.verifyJsonStats();
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public void createStat(String name) {
        try {
            statsJsonDAO.createEmptyStats(name);
        } catch (PersistenceException e) {
            throw new RuntimeException(e);
        }
    }
}
