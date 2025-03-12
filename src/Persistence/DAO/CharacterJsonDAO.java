package Persistence.DAO;

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
public class CharacterJsonDAO {

    private final String path = "./src/Files/characters.json";
    private ConnectorAPIHelper apiHelper;

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
    public List<Character> getAllCharacters() throws PersistenceException, ApiException {
        if (this.apiHelper == null) {
            try {
                FileReader reader = new FileReader(this.path);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Type characterListType = new TypeToken<ArrayList<Character>>() {
                }.getType();
                return gson.fromJson(reader, characterListType);
            } catch (FileNotFoundException e) {
                throw new PersistenceException(e.getMessage());
            }
        } else {
            String characters = apiHelper.getRequest("shared/characters");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type characterListType = new TypeToken<ArrayList<Character>>() {
            }.getType();
            return gson.fromJson(characters, characterListType);

        }
    }

    /**
     * Busca un personaje por su id.
     *
     * @param id Identificador del personaje que queremos encontrar.
     * @return El personaje correspondiente si existe, de lo contrario lanza una excepción.
     * @throws PersistenceException Si no se encuentra el personaje o hay un problema con la lectura de datos.
     */
    public Character getCharacterById(long id) throws PersistenceException, ApiException {
        if (this.apiHelper == null) {
            try {
                List<Character> characters = getAllCharacters();
                for (Character character : characters) {
                    if (character.getId() == id) {
                        return character;
                    }
                }
                throw new PersistenceException("Character with id " + id + " not found");
            } catch (PersistenceException | ApiException e) {
                throw new PersistenceException(e.getMessage());
            }
        } else {
            String c = apiHelper.getRequest("shared/characters?id=" + id);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type characterListType = new TypeToken<ArrayList<Character>>() {}.getType();
            List<Character> characters = gson.fromJson(c, characterListType);
            return characters.getFirst();
        }
    }

    /**
     * Busca un personaje por su nombre en la lista de personajes almacenada.
     *
     * @param name Nombre del personaje a buscar (ignorando mayúsculas y minúsculas).
     * @return El personaje encontrado.
     * @throws PersistenceException Si ocurre un error al obtener la lista o el personaje no existe.
     */
    public Character getCharacterByName(String name) throws PersistenceException, ApiException {
        if (this.apiHelper == null) {
            List<Character> characters = getAllCharacters();
            for (Character character : characters) {
                if (character.getName().equalsIgnoreCase(name)) {
                    return character;
                }
            }
            throw new PersistenceException("Error getting character!");
        } else {
            String c = apiHelper.getRequest("shared/characters?name="+name);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type characterListType = new TypeToken<ArrayList<Character>>() {}.getType();
            List<Character> characters = gson.fromJson(c, characterListType);
            return characters.getFirst();
        }
    }

    public void setApiHelper(ConnectorAPIHelper apiHelper) {
        this.apiHelper = apiHelper;
    }
}