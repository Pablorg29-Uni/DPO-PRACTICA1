package Persistence.Character;

import Business.Entities.Character;
import Exceptions.PersistenceException;
import Persistence.API.ConnectorAPIHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import edu.salle.url.api.exception.ApiException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Maneja la persistencia de personajes en un archivo JSON.
 * Proporciona métodos para leer, guardar y modificar personajes.
 */
public class CharacterJsonDAO implements CharacterDAO {

    private final String path = "./src/Files/characters.json";
    private ConnectorAPIHelper apiHelper;

    public static boolean canConnect () {
        try {
            new FileReader("./src/Files/characters.json");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtiene todos los personajes almacenados en el archivo JSON.
     *
     * @return Lista de personajes.
     * @throws PersistenceException Si ocurre un error al leer el archivo.
     */
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

    /**
     * Busca un personaje por su ID en el archivo JSON.
     *
     * @param id Identificador del personaje.
     * @return Personaje encontrado.
     * @throws PersistenceException Si el personaje no existe.
     */
    @Override
    public Character getCharacterById(long id) throws PersistenceException {
        List<Character> characters = getAllCharacters();
        return characters.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new PersistenceException("Character with ID " + id + " not found"));
    }

    /**
     * Busca un personaje por su nombre en el archivo JSON.
     *
     * @param name Nombre del personaje.
     * @return Personaje encontrado.
     * @throws PersistenceException Si el personaje no existe.
     */
    @Override
    public Character getCharacterByName(String name) throws PersistenceException {
        List<Character> characters = getAllCharacters();
        return characters.stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new PersistenceException("Character with name '" + name + "' not found"));
    }
}