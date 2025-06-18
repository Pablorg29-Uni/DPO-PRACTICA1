package Persistence.Character;

import Business.Entities.Character;
import Persistence.API.ConnectorAPIHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import edu.salle.url.api.exception.ApiException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para manejar la persistencia de personajes vía API.
 */
public class CharacterApiDAO implements CharacterDAO {

    private final ConnectorAPIHelper apiHelper;

    public CharacterApiDAO(ConnectorAPIHelper apiHelper) {
        this.apiHelper = apiHelper;
    }

    /**
     * Obtiene todos los personajes desde la API.
     *
     * @return Lista de personajes.
     * @throws ApiException en caso de fallo en la API.
     */
    @Override
    public List<Character> getAllCharacters() throws ApiException {
        String response = apiHelper.getRequest("shared/characters");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type characterListType = new TypeToken<ArrayList<Character>>() {}.getType();
        return gson.fromJson(response, characterListType);
    }

    /**
     * Obtiene un personaje por su ID desde la API.
     *
     * @param id ID del personaje.
     * @return Personaje encontrado.
     * @throws ApiException en caso de fallo en la API.
     */
    @Override
    public Character getCharacterById(long id) throws ApiException {
        String response = apiHelper.getRequest("shared/characters?id=" + id);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type characterListType = new TypeToken<ArrayList<Character>>() {}.getType();
        List<Character> characters = gson.fromJson(response, characterListType);
        return characters.isEmpty() ? null : characters.get(0);
    }

    /**
     * Obtiene un personaje por su nombre desde la API.
     *
     * @param name Nombre del personaje.
     * @return Personaje encontrado.
     * @throws ApiException en caso de fallo en la API.
     */
    @Override
    public Character getCharacterByName(String name) throws ApiException {
        String response = apiHelper.getRequest("shared/characters?name=" + name);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type characterListType = new TypeToken<ArrayList<Character>>() {}.getType();
        List<Character> characters = gson.fromJson(response, characterListType);
        return characters.isEmpty() ? null : characters.get(0);
    }
}