package Business;

import Business.Entities.Stats;
import Business.Entities.Team;
import Exceptions.BusinessException;
import Exceptions.PersistenceException;
import Persistence.StatsJsonDAO;
import java.util.List;

public class StatsManager {
    private final StatsJsonDAO statsJsonDAO;

    public StatsManager() {
        this.statsJsonDAO = new StatsJsonDAO();
    }

   public void deleteStat (String name) throws BusinessException {
        try {
            statsJsonDAO.deleteOneStats(name);
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public Stats getStat(String name) throws BusinessException {
        try {
            return statsJsonDAO.getStat(name);
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public void verify() throws BusinessException {
        try {
            statsJsonDAO.verifyJsonStats();
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public void createStat(String name) throws BusinessException{
        try {
            statsJsonDAO.createEmptyStats(name);
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public void actualitzarFinalJoc(Team team1, Team team2, int victoria1, int victoria2, int ko1, int ko2) throws BusinessException {
        List<Stats> stats;
        try {
            stats = statsJsonDAO.getAllStats();
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
        for (Stats stat : stats) {
            if (stat.getName().equals(team1.getName())) {
                stat.setGames_played(stat.getGames_played() + 1);
                stat.setGames_won(stat.getGames_won() + victoria1);
                stat.setKO_done(stat.getKO_done() + ko2);
                stat.setKO_received(stat.getKO_received() + ko1);
            }
            if (stat.getName().equals(team2.getName())) {
                stat.setGames_played(stat.getGames_played() + 1);
                stat.setGames_won(stat.getGames_won() + victoria2);
                stat.setKO_done(stat.getKO_done() + ko1);
                stat.setKO_received(stat.getKO_received() + ko2);
            }
        }
        try {
            statsJsonDAO.writeStatsToFile(stats);
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
