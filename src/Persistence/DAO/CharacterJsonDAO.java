package Persistence.DAO;

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
/**
 * Maneja la persistencia de personajes en un archivo JSON.
 * Proporciona métodos para leer, guardar y modificar personajes.
 */
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
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    /**
     * Carga todos los personajes desde un archivo JSON y los devuelve en una lista.
     *
     * @return Lista con los personajes cargados desde el archivo.
     * @throws PersistenceException Si el archivo no existe o hay un problema al leerlo.
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
     * Busca un personaje por su id.
     *
     * @param id Identificador del personaje que queremos encontrar.
     * @return El personaje correspondiente si existe, de lo contrario lanza una excepción.
     * @throws PersistenceException Si no se encuentra el personaje o hay un problema con la lectura de datos.
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
     * Busca un personaje por su nombre en la lista de personajes almacenada.
     *
     * @param name Nombre del personaje a buscar (ignorando mayúsculas y minúsculas).
     * @return El personaje encontrado.
     * @throws PersistenceException Si ocurre un error al obtener la lista o el personaje no existe.
     */
    public Character getCharacterByName(String name) throws PersistenceException {
        List<Character> characters = getAllCharacters();
        for (Character character : characters) {
            if (character.getName().equalsIgnoreCase(name)) {
                return character;
            }
        }
        throw new PersistenceException("Error getting character!");
    }
}