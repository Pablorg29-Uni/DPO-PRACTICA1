package Persistence.Items;

import Business.Entities.Items;
import Exceptions.PersistenceException;
import edu.salle.url.api.exception.ApiException;

import java.util.List;

/**
 * Interfaz para el acceso a datos de ítems.
 * Define el método para obtener todos los ítems.
 */
public interface ItemsDAO {

    /**
     * Obtiene la lista completa de ítems.
     *
     * @return Lista de ítems.
     * @throws PersistenceException en caso de error de persistencia.
     * @throws ApiException en caso de error en la API.
     */
    List<Items> getAllItems() throws PersistenceException, ApiException;
}