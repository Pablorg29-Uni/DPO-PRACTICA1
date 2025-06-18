package Business.Entities;

import com.google.gson.annotations.SerializedName;

/**
 * Modelo que representa un ítem del juego con sus propiedades básicas.
 */
public class Items {
    private long id;
    private String name;
    private int power;
    private int durability;
    @SerializedName("class")
    private String classe;

    /**
     * Constructor de la clase Items.
     *
     * @param name Nombre del ítem.
     */
    public Items(String name) {
        this.name = name;
    }

    /**
     * Obtiene el identificador del ítem.
     *
     * @return Identificador del ítem.
     */
    public long getId() {
        return id;
    }

    /**
     * Obtiene el nombre del ítem.
     *
     * @return Nombre del ítem.
     */
    public String getName() {
        return name;
    }

    /**
     * Obtiene el poder del ítem.
     *
     * @return Poder del ítem.
     */
    public int getPower() {
        return power;
    }

    /**
     * Obtiene la durabilidad del ítem.
     *
     * @return Durabilidad del ítem.
     */
    public int getDurability() {
        return durability;
    }

    /**
     * Establece la durabilidad del ítem.
     *
     * @param durability Nueva durabilidad del ítem.
     */
    public void setDurability(int durability) {
        this.durability = durability;
    }

    /**
     * Obtiene la clase del ítem.
     *
     * @return Clase del ítem.
     */
    public String getClasse() {
        return classe;
    }
}