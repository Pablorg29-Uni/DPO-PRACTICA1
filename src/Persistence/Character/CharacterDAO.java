package Persistence.Character;

import Business.Entities.Character;
import Exceptions.PersistenceException;
import edu.salle.url.api.exception.ApiException;

import java.util.List;

/**
 * Interfaz para el acceso a datos de personajes.
 */
public interface CharacterDAO {

    /**
     * Obtiene todos los personajes.
     *
     * @return Lista de personajes.
     * @throws PersistenceException error en persistencia.
     * @throws ApiException error de API.
     */
    List<Character> getAllCharacters() throws PersistenceException, ApiException;

    /**
     * Obtiene un personaje por su ID.
     *
     * @param id Identificador del personaje.
     * @return Personaje encontrado o null si no existe.
     * @throws PersistenceException error en persistencia.
     * @throws ApiException error de API.
     */
    Character getCharacterById(long id) throws PersistenceException, ApiException;

    /**
     * Obtiene un personaje por su nombre.
     *
     * @param name Nombre del personaje.
     * @return Personaje encontrado o null si no existe.
     * @throws PersistenceException error en persistencia.
     * @throws ApiException error de API.
     */
    Character getCharacterByName(String name) throws PersistenceException, ApiException;
}