package Business.Entities;

import com.google.gson.annotations.SerializedName;

public class    Items {
    private long id;
    private String name;
    private int power;
    private int durability;
    @SerializedName("class")
    private String classe;



    public Items(long id, String name, int power, int durability, String classe) {
        this.id = id;
        this.name = name;
        this.power = power;
        this.durability = durability;
        this.classe = classe;
    }

    // Métodos getters
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public String getClasse() {
        return classe;
    }

}
