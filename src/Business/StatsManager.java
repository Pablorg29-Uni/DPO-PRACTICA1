package Business;

import Business.Entities.Stats;
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

    public Stats getStat(String name) {
        return statsJsonDAO.getStat(name);
    }
}
