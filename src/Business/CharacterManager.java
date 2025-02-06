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

    public List<Business.Entities.Character> getCharacters() {
        return this.characterJsonDAO.getAllCharacters();
    }

    public Character getCharacter(long id) {
        return this.characterJsonDAO.getCharacterById(id);
    }

    public Character getCharacter2(String name) {
        return this.characterJsonDAO.getCharacterByName(name);
    }

    public void verify() throws BusinessException {
        try {
            characterJsonDAO.verifyJsonCharacter();
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
