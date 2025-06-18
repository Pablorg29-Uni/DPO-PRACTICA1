package Persistence.Character;

import Business.Entities.Character;
import Exceptions.PersistenceException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para manejar personajes persistidos en un archivo JSON.
 * Proporciona métodos para obtener personajes y buscar por ID o nombre.
 */
public class CharacterJsonDAO implements CharacterDAO {

    private final String path = "./src/Files/characters.json";

    /**
     * Verifica si se puede acceder al archivo JSON.
     *
     * @return true si el archivo es accesible, false en caso contrario.
     */
    public static boolean canConnect() {
        try {
            new FileReader("./src/Files/characters.json");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Character> getAllCharacters() throws PersistenceException {
        try (FileReader reader = new FileReader(this.path)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type characterListType = new TypeToken<ArrayList<Character>>() {}.getType();
            return gson.fromJson(reader, characterListType);
        } catch (Exception e) {
            throw new PersistenceException("Error reading characters from JSON: " + e.getMessage());
        }
    }

    @Override
    public Character getCharacterById(long id) throws PersistenceException {
        List<Character> characters = getAllCharacters();
        return characters.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new PersistenceException("Character with ID " + id + " not found"));
    }

    @Override
    public Character getCharacterByName(String name) throws PersistenceException {
        List<Character> characters = getAllCharacters();
        return characters.stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new PersistenceException("Character with name '" + name + "' not found"));
    }
}