package Business;

import Business.Entities.Character;
import Persistence.CharacterJsonDAO;

import java.util.List;

public class CharacterManager {
    private final CharacterJsonDAO characterJsonDAO;

    public CharacterManager(CharacterJsonDAO characterJsonDAO) {
        this.characterJsonDAO = characterJsonDAO;
    }

    public void checkCharacterFile() {
        this.characterJsonDAO.verifyJsonCharacter();
    }

    public List<Business.Entities.Character> getCharacters() {
        return this.characterJsonDAO.getAllCharacters();
    }

    public Character getCharacter(int id) {
        return this.characterJsonDAO.getCharacter(id);
    }
}
