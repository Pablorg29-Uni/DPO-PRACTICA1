package Persistence;

import Exceptions.PersistenceException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import Business.Entities.Stats;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
/**
 * Maneja la persistencia de las estadísticas en un archivo JSON.
 */

public class StatsJsonDAO {

    private final String path = "./src/Files/stats.json";

    /**
     * Verifica si el archivo JSON de estadísticas existe.
     *
     * @throws PersistenceException si el archivo no se encuentra.
     */
    public void verifyJsonStats() throws PersistenceException {
        try {
            FileReader fileReader = new FileReader(this.path);
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    /**
     * Elimina una estadística del archivo JSON según el nombre del equipo.
     *
     * Busca y borra la estadística correspondiente, luego guarda la lista actualizada.
     * Lanza una excepción si ocurre un error al acceder o modificar el archivo.
     *
     * @param name Nombre del equipo cuya estadística será eliminada.
     * @throws PersistenceException Si hay un problema con el archivo JSON.
     */
    public void deleteOneStats(String name) throws PersistenceException {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<Stats> stats = getAllStats();
            stats.removeIf(stat -> name.equals(stat.getName())); // Remueve la estadística por nombre
            try (FileWriter writer = new FileWriter(this.path)) {
                gson.toJson(stats, writer);
            }
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    /**
     * Carga todas las estadísticas desde el archivo JSON.
     *
     * @return Lista de estadísticas almacenadas.
     * @throws PersistenceException Si ocurre un error al leer el archivo.
     */
    public List<Stats> getAllStats() throws PersistenceException {
        try {
            FileReader reader = new FileReader(this.path);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type statsType = new TypeToken<ArrayList<Stats>>() {}.getType();
            return gson.fromJson(reader, statsType);
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    /**
     * Crea una nueva estadística vacía para un equipo y la guarda en el archivo JSON.
     *
     * @param teamName Nombre del equipo para el que se crea la estadística.
     * @throws PersistenceException si hay problemas de persistencia.
     */
    public void createEmptyStats(String teamName) throws PersistenceException {
        List<Stats> stats = getAllStats();
        Stats s = new Stats(teamName, 0, 0, 0, 0);
        stats.add(s);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(this.path)) {
            gson.toJson(stats, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Busca las estadísticas de un equipo por nombre.
     *
     * @param name Nombre del equipo.
     * @return Estadísticas del equipo.
     * @throws PersistenceException Si no se encuentran datos.
     */
    public Stats getStat(String name) throws PersistenceException {
        List<Stats> stats = getAllStats();
        for (Stats stat : stats) {
            if (stat.getName().equals(name)) {
                return stat;
            }
        }
        throw new PersistenceException("No stats found for name " + name);
    }

    /**
     * Escribe la lista de estadísticas en un archivo JSON.
     *
     * @param stats Lista de objetos Stats que se guardarán en el archivo.
     * @throws PersistenceException Si ocurre un error al escribir en el archivo.
     */
    public void writeStatsToFile(List<Stats> stats) throws PersistenceException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(this.path)) {
            gson.toJson(stats, writer);
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage());
        }
    }
}