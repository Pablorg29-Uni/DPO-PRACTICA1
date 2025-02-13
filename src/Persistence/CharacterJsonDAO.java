package Persistence;

import Business.Entities.Character;
import Exceptions.PersistenceException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CharacterJsonDAO {

    private final String path = "./src/Files/characters.json";

    public void verifyJsonCharacter() throws PersistenceException {
        try {
            new FileReader(this.path);
        } catch (FileNotFoundException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    public List<Character> getAllCharacters() {
        try {
            FileReader reader = new FileReader(this.path);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type characterListType = new TypeToken<ArrayList<Character>>() {}.getType();
            return gson.fromJson(reader, characterListType);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Character getCharacterById(long id) {
        List<Character> characters = getAllCharacters();
        for (Character character : characters) {
            if (character.getId() == id) {
                return character;
            }
        }
        return null;
    }

    public Character getCharacterByName(String name) {
        List<Character> characters = getAllCharacters();
        for (Character character : characters) {
            if (character.getName().equalsIgnoreCase(name)) {
                return character;
            }
        }
        return null;
    }

}