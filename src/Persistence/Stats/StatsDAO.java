package Persistence.Stats;

import Business.Entities.Stats;
import Exceptions.PersistenceException;
import edu.salle.url.api.exception.ApiException;

import java.util.List;

/**
 * Interfaz para la persistencia de estadísticas.
 */
public interface StatsDAO {

    /**
     * Obtiene todas las estadísticas.
     * @return Lista de estadísticas.
     * @throws PersistenceException Error de persistencia.
     * @throws ApiException Error en la API.
     */
    List<Stats> getAllStats() throws PersistenceException, ApiException;

    /**
     * Elimina una estadística por nombre.
     * @param name Nombre del equipo.
     * @throws PersistenceException Error de persistencia.
     * @throws ApiException Error en la API.
     */
    void deleteOneStats(String name) throws PersistenceException, ApiException;

    /**
     * Crea una estadística vacía para un equipo.
     * @param teamName Nombre del equipo.
     * @throws PersistenceException Error de persistencia.
     * @throws ApiException Error en la API.
     */
    void createEmptyStats(String teamName) throws PersistenceException, ApiException;

    /**
     * Obtiene una estadística por nombre.
     * @param name Nombre del equipo.
     * @return Estadística encontrada.
     * @throws PersistenceException Error de persistencia.
     * @throws ApiException Error en la API.
     */
    Stats getStat(String name) throws PersistenceException, ApiException;

    /**
     * Guarda la lista de estadísticas.
     * @param stats Lista de estadísticas.
     * @throws PersistenceException Error de persistencia.
     * @throws ApiException Error en la API.
     */
    void writeStatsToFile(List<Stats> stats) throws PersistenceException, ApiException;
}