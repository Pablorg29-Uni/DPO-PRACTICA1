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
        } catch (FileNotFoundException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    /**
     * Elimina una estadística del archivo JSON basada en el nombre del equipo.
     *
     * @param name Nombre del equipo cuyas estadísticas deben eliminarse.
     */
    public void deleteOneStats(String name) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<Stats> stats = getAllStats();
            stats.removeIf(stat -> name.equals(stat.getName())); // Remueve la estadística por nombre
            try (FileWriter writer = new FileWriter(this.path)) {
                gson.toJson(stats, writer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Obtiene todas las estadísticas almacenadas en el archivo JSON.
     *
     * @return Lista de todas las estadísticas disponibles.
     */
    public List<Stats> getAllStats() {
        try {
            FileReader reader = new FileReader(this.path);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type statsType = new TypeToken<ArrayList<Stats>>() {}.getType();
            return gson.fromJson(reader, statsType);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
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
     * Obtiene las estadísticas de un equipo basado en su nombre.
     *
     * @param name Nombre del equipo.
     * @return Objeto Stats correspondiente al equipo.
     * @throws RuntimeException si no se encuentran estadísticas para el equipo dado.
     */
    public Stats getStat(String name) {
        List<Stats> stats = getAllStats();
        for (Stats stat : stats) {
            if (stat.getName().equals(name)) {
                return stat;
            }
        }
        throw new RuntimeException("No stats found for name " + name);
    }

    public void writeStatsToFile(List<Stats> stats) throws PersistenceException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(this.path)) {
            gson.toJson(stats, writer);
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage());
        }
    }
}