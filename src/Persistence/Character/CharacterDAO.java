package Persistence.Character;

import Business.Entities.Character;
import Exceptions.PersistenceException;
import edu.salle.url.api.exception.ApiException;

import java.util.List;

public interface CharacterDAO {

    void verifyJsonCharacter() throws PersistenceException;


    List<Character> getAllCharacters() throws PersistenceException, ApiException;


    Character getCharacterById(long id) throws PersistenceException, ApiException;


    Character getCharacterByName(String name) throws PersistenceException, ApiException;
}
