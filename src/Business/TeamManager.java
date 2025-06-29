package Business;

import Business.Entities.Member;
import Business.Entities.Team;
import Exceptions.BusinessException;
import Exceptions.PersistenceException;
import Persistence.API.ConnectorAPIHelper;
import Persistence.Team.TeamApiDAO;
import Persistence.Team.TeamDAO;
import Persistence.Team.TeamJsonDAO;
import edu.salle.url.api.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona la administración de equipos, incluyendo su creación, eliminación,
 * recuperación y verificación en el sistema de persistencia.
 */
public class TeamManager {
    private TeamDAO teamDAO;
    private StatsManager statsManager;

    /**
     * Verifica la integridad de los datos de los equipos en el sistema de persistencia.
     *
     * @throws BusinessException Si ocurre un error en la verificación.
     */
    public void verify(StatsManager statsmanager) throws BusinessException {
        if (!TeamJsonDAO.canConnect()) {
            throw new BusinessException("No connection established");
        } else {
            this.teamDAO = new TeamJsonDAO();
            this.statsManager = statsmanager;
        }
    }

    /**
     * Elimina un equipo del sistema por su nombre.
     *
     * @param name Nombre del equipo a eliminar.
     * @return true si el equipo fue eliminado correctamente, false en caso contrario.
     * @throws BusinessException Si ocurre un error durante la eliminación.
     */
    public boolean eliminateTeam(String name) throws BusinessException {
        try {
            statsManager.deleteStat(name);
            return teamDAO.eliminateTeam(name);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Crea un nuevo equipo con los jugadores especificados.
     *
     * @param name   Nombre del equipo.
     * @param id1    ID del primer jugador.
     * @param id2    ID del segundo jugador.
     * @param id3    ID del tercer jugador.
     * @param id4    ID del cuarto jugador.
     * @param strats Estrategias del equipo, un array de 4 strings.
     * @throws BusinessException Si ocurre un error durante la creación.
     */
    public void createTeam(String name, long id1, long id2, long id3, long id4, String[] strats) throws BusinessException {
        try {
            Team team = new Team(name, id1, id2, id3, id4, strats[0], strats[1], strats[2], strats[3]);
            statsManager.createStat(name);
            teamDAO.saveTeam(team);
        } catch (PersistenceException | ApiException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Recupera la lista de todos los equipos almacenados.
     *
     * @return Lista de equipos.
     * @throws BusinessException Si ocurre un error durante la recuperación.
     */
    public List<Team> showTeams() throws BusinessException {
        try {
            return teamDAO.getAllTeams();
        } catch (PersistenceException | ApiException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Obtiene un equipo específico por su nombre.
     *
     * @param name Nombre del equipo a recuperar.
     * @return El equipo correspondiente al nombre especificado.
     * @throws BusinessException Si ocurre un error durante la búsqueda.
     */
    public Team getTeam(String name) throws BusinessException {
        try {
            return teamDAO.getTeam(name);
        } catch (PersistenceException | ApiException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Obtiene una lista de equipos en los que participa un jugador específico.
     *
     * @param id ID del jugador a buscar en los equipos.
     * @return Lista de equipos en los que el jugador está registrado.
     * @throws BusinessException Si ocurre un error durante la búsqueda.
     */
    public ArrayList<Team> teamsWithPlayer(long id) throws BusinessException {
        ArrayList<Team> matchingTeams = new ArrayList<>();
        List<Team> teams;
        try {
            teams = teamDAO.getAllTeams();
        } catch (PersistenceException | ApiException e) {
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

    /**
     * Establece el helper para conectar con la API externa y el manejador de estadísticas.
     *
     * @param apiHelper    Instancia de {@link ConnectorAPIHelper} para la conexión con la API.
     * @param statsManager Instancia de {@link StatsManager} para manejar estadísticas.
     */
    public void setApiHelper(ConnectorAPIHelper apiHelper, StatsManager statsManager) {
        this.teamDAO = new TeamApiDAO(apiHelper);
        this.statsManager = statsManager;
    }
}