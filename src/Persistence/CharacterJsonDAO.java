package Persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import Business.Entities.Character;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CharacterJsonDAO {

    private final String path = "./src/Files/characters.json";


    public void verifyJsonCharacter() {
        try {
            FileReader FileReader = new FileReader(this.path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Character> getAllCharacters() {
        try {
            FileReader reader = new FileReader(this.path);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type c = new TypeToken<ArrayList<Character>>() {
            }.getType();
            return gson.fromJson(reader, c);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Character getCharacter(int id) {
        List<Character> characters = getAllCharacters();
        for (Character c : characters) {
            if (c.Character() == id) {
                return c;
            }
        }
        throw new RuntimeException("No character with id " + id);
    }
}
