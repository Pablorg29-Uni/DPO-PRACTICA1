package Persistence;

import Business.Entities.Character;
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

    // Verificar si el archivo JSON de personajes existe
    public void verifyJsonCharacter() {
        try {
            new FileReader(this.path);  // Corrección: no es necesario renombrar la variable FileReader
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Obtener todos los personajes desde el archivo JSON
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

    // Buscar un personaje por su id
    public Character getCharacterById(long id) {
        List<Character> characters = getAllCharacters();
        for (Character character : characters) {
            if (character.getId() == id) {
                return character;
            }
        }
        return null;  // Si no se encuentra el personaje, se devuelve null
    }

    // Buscar un personaje por su nombre
    public Character getCharacterByName(String name) {
        List<Character> characters = getAllCharacters();
        for (Character character : characters) {
            if (character.getName().equalsIgnoreCase(name)) {
                return character;
            }
        }
        return null;  // Si no se encuentra el personaje, se devuelve null
    }

    // Método para obtener un personaje por id, el id es un long en lugar de int
    public Character getCharacter(int id) {
        return getCharacterById((long) id);  // Llamamos a getCharacterById pasando el id como long
    }
}