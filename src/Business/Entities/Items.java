package Business.Entities;

public class    Items {
    private long id;
    private String name;
    private int power;
    private int durability;
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

    public String getClasse() {
        return classe;
    }

}
