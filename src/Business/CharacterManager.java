package Business;

import Business.Entities.Character;
import Exceptions.BusinessException;
import Exceptions.PersistenceException;
import Persistence.CharacterJsonDAO;

import java.util.List;

public class    CharacterManager {
    private final CharacterJsonDAO characterJsonDAO;

    public CharacterManager() {
        this.characterJsonDAO = new CharacterJsonDAO();
    }

    public void checkCharacterFile() throws BusinessException {
        try {
            this.characterJsonDAO.verifyJsonCharacter();
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public List<Business.Entities.Character> getCharacters() throws BusinessException {
        try {
            return this.characterJsonDAO.getAllCharacters();
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public Character getCharacter(long id) throws BusinessException {
        try {
            return this.characterJsonDAO.getCharacterById(id);
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public Character getCharacter2(String name) throws BusinessException {
        try {
            return this.characterJsonDAO.getCharacterByName(name);
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public void verify() throws BusinessException {
        try {
            characterJsonDAO.verifyJsonCharacter();
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
