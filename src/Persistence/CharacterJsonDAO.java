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

    /**
     * Verifica si el archivo JSON de personajes existe.
     *
     * @throws PersistenceException Si el archivo no se encuentra.
     */
    public void verifyJsonCharacter() throws PersistenceException {
        try {
            new FileReader(this.path);
        } catch (FileNotFoundException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    /**
     * Obtiene la lista de todos los personajes desde el archivo JSON.
     *
     * @return Lista de personajes.
     */
    public List<Character> getAllCharacters() throws PersistenceException {
        try {
            FileReader reader = new FileReader(this.path);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type characterListType = new TypeToken<ArrayList<Character>>() {
            }.getType();
            return gson.fromJson(reader, characterListType);
        } catch (FileNotFoundException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    /**
     * Busca un personaje por su identificador.
     *
     * @param id Identificador del personaje.
     * @return El personaje si se encuentra, de lo contrario, null.
     */
    public Character getCharacterById(long id) throws PersistenceException {
        try {
            List<Character> characters = getAllCharacters();
            for (Character character : characters) {
                if (character.getId() == id) {
                    return character;
                }
            }
            throw new PersistenceException("Character with id " + id + " not found");
        } catch (PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    /**
     * Busca un personaje por su nombre.
     *
     * @param name Nombre del personaje.
     * @return El personaje si se encuentra, de lo contrario, null.
     */
    public Character getCharacterByName(String name) throws PersistenceException {
        List<Character> characters = getAllCharacters();
        for (Character character : characters) {
            if (character.getName().equalsIgnoreCase(name)) {
                return character;
            }
        }
        return null;
    }
}