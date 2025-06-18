package Persistence.Team;

import Business.Entities.Team;
import Exceptions.PersistenceException;
import edu.salle.url.api.exception.ApiException;
import java.util.List;

/**
 * DAO para la persistencia de equipos.
 * Define los métodos para obtener, guardar y eliminar equipos.
 */
public interface TeamDAO {

    /**
     * Obtiene la lista completa de equipos.
     *
     * @return Lista de equipos.
     * @throws PersistenceException Si ocurre un error en la persistencia.
     * @throws ApiException Si ocurre un error en la llamada a la API.
     */
    List<Team> getAllTeams() throws PersistenceException, ApiException;

    /**
     * Obtiene un equipo por su nombre.
     *
     * @param name Nombre del equipo a buscar.
     * @return Equipo encontrado.
     * @throws PersistenceException Si el equipo no se encuentra o hay un error de persistencia.
     * @throws ApiException Si ocurre un error en la llamada a la API.
     */
    Team getTeam(String name) throws PersistenceException, ApiException;

    /**
     * Guarda o actualiza un equipo.
     *
     * @param team Equipo a guardar.
     * @throws PersistenceException Si ocurre un error al guardar el equipo.
     * @throws ApiException Si ocurre un error en la llamada a la API.
     */
    void saveTeam(Team team) throws PersistenceException, ApiException;

    /**
     * Elimina un equipo por su nombre.
     *
     * @param name Nombre del equipo a eliminar.
     * @return true si el equipo fue eliminado correctamente.
     * @throws PersistenceException Si ocurre un error al eliminar el equipo.
     * @throws ApiException Si ocurre un error en la llamada a la API.
     */
    boolean eliminateTeam(String name) throws PersistenceException, ApiException;
}