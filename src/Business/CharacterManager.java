package Business;

import Business.Entities.Character;
import Exceptions.BusinessException;
import Exceptions.PersistenceException;
import Persistence.API.ConnectorAPIHelper;
import Persistence.Character.CharacterApiDAO;
import Persistence.Character.CharacterDAO;
import Persistence.Character.CharacterJsonDAO;
import Persistence.Items.ItemsJsonDAO;
import edu.salle.url.api.exception.ApiException;

import java.util.List;
/**
 * Gestiona las operaciones relacionadas con los personajes.
 * Utiliza CharacterJsonDAO para interactuar con el almacenamiento JSON.
 */
public class CharacterManager {
    private CharacterDAO characterDAO;

    /**
     * Constructor de CharacterManager. Inicializa el DAO de personajes.
     */
    public CharacterManager() {
        this.characterDAO = new CharacterJsonDAO();
    }

    /**
     * Obtiene la lista de todos los personajes.
     *
     * @return Lista de personajes.
     * @throws BusinessException Si ocurre un error al recuperar los personajes.
     */
    public List<Business.Entities.Character> getCharacters() throws BusinessException {
        try {
            return this.characterDAO.getAllCharacters();
        } catch (PersistenceException | ApiException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Obtiene un personaje por su ID.
     *
     * @param id Identificador del personaje.
     * @return El personaje correspondiente al ID especificado.
     * @throws BusinessException Si ocurre un error al recuperar el personaje.
     */
    public Character getCharacter(long id) throws BusinessException {
        try {
            return this.characterDAO.getCharacterById(id);
        } catch (PersistenceException | ApiException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Obtiene un personaje por su nombre.
     *
     * @param name Nombre del personaje.
     * @return El personaje correspondiente al nombre especificado.
     * @throws BusinessException Si ocurre un error al recuperar el personaje.
     */
    public Character getCharacter2(String name) throws BusinessException {
        try {
            return this.characterDAO.getCharacterByName(name);
        } catch (PersistenceException | ApiException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Verifica la existencia del archivo JSON de personajes.
     *
     * @throws BusinessException Si ocurre un error al verificar el archivo.
     */
    public void verify() throws BusinessException {
        if (!CharacterJsonDAO.canConnect()) {
            throw new BusinessException("No connection established");
        } else {
            characterDAO = new CharacterJsonDAO();
        }
    }

    public void setApiHelper(ConnectorAPIHelper apiHelper) {
        characterDAO = new CharacterApiDAO(apiHelper);
    }
}