package Business;

import Business.Entities.Stats;
import Business.Entities.Team;
import Exceptions.BusinessException;
import Exceptions.PersistenceException;
import Persistence.StatsJsonDAO;
import java.util.List;

public class StatsManager {
    private final StatsJsonDAO statsJsonDAO;

    /**
     * Constructor de StatsManager. Inicializa el acceso a los datos de estadísticas.
     */
    public StatsManager() {
        this.statsJsonDAO = new StatsJsonDAO();
    }

    /**
     * Elimina las estadísticas de un equipo por su nombre.
     *
     * @param name Nombre del equipo cuyas estadísticas se eliminarán.
     * @throws BusinessException Si ocurre un error durante la eliminación.
     */
    public void deleteStat(String name) throws BusinessException {
        try {
            statsJsonDAO.deleteOneStats(name);
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Recupera las estadísticas de un equipo por su nombre.
     *
     * @param name Nombre del equipo cuyas estadísticas se desean obtener.
     * @return Objeto Stats con las estadísticas del equipo.
     * @throws BusinessException Si ocurre un error al recuperar los datos.
     */
    public Stats getStat(String name) throws BusinessException {
        try {
            return statsJsonDAO.getStat(name);
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Verifica la integridad del archivo de estadísticas.
     *
     * @throws BusinessException Si ocurre un error en la verificación.
     */
    public void verify() throws BusinessException {
        try {
            statsJsonDAO.verifyJsonStats();
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Crea una nueva entrada de estadísticas para un equipo con valores inicializados en cero.
     *
     * @param name Nombre del equipo para el cual se crearán las estadísticas.
     * @throws BusinessException Si ocurre un error en la creación.
     */
    public void createStat(String name) throws BusinessException {
        try {
            statsJsonDAO.createEmptyStats(name);
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Actualiza las estadísticas de los equipos después de un combate.
     *
     * @param team1 Equipo 1 participante en el combate.
     * @param team2 Equipo 2 participante en el combate.
     * @param victoria1 Número de victorias obtenidas por el equipo 1 en este combate.
     * @param victoria2 Número de victorias obtenidas por el equipo 2 en este combate.
     * @param ko1 Número de KOs recibidos por el equipo 1.
     * @param ko2 Número de KOs recibidos por el equipo 2.
     * @throws BusinessException Si ocurre un error al actualizar los datos.
     */
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
