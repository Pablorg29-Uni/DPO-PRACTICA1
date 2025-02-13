package Business.Entities;

public class Character {

    private long id;
    private String name;
    private int weight;

    /**
     * Constructor de la clase Character.
     *
     * @param id Identificador único del personaje.
     * @param name Nombre del personaje.
     * @param weight Peso del personaje en unidades arbitrarias.
     */
    public Character(long id, String name, int weight) {
        this.id = id;
        this.name = name;
        this.weight = weight;
    }

    /**
     * Obtiene el identificador del personaje.
     *
     * @return Identificador del personaje.
     */
    public long getId() {
        return id;
    }

    /**
     * Obtiene el nombre del personaje.
     *
     * @return Nombre del personaje.
     */
    public String getName() {
        return name;
    }

    /**
     * Obtiene el peso del personaje.
     *
     * @return Peso del personaje.
     */
    public int getWeight() {
        return weight;
    }
}