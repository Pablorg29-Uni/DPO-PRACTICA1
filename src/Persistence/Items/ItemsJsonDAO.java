package Persistence.Items;

import Exceptions.PersistenceException;
import Persistence.API.ConnectorAPIHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import Business.Entities.Items;
import edu.salle.url.api.exception.ApiException;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Maneja la persistencia de los ítems en formato JSON.
 * Se encarga de leer y escribir la información de los ítems en el archivo correspondiente.
 */
public class ItemsJsonDAO implements ItemsDAO {

    private final String path = "./src/Files/items.json";

    public static boolean canConnect() {
        try {
            new FileReader("./src/Files/items.json");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtiene la lista de todos los ítems almacenados en el archivo JSON.
     *
     * @return Lista de ítems disponibles.
     * @throws PersistenceException Si ocurre un error al leer el archivo o al parsear los datos.
     */
    public List<Items> getAllItems() throws PersistenceException {
        try {
            FileReader reader = new FileReader(this.path);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type itemType = new TypeToken<ArrayList<Items>>() {
            }.getType();
            return gson.fromJson(reader, itemType);
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage());
        }
    }

}
