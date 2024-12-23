package Business.Entities;

public class Character {

    private long id;
    private String name;
    private int weight;

    // Constructor
    public Character(long id, String name, int weight) {
        this.id = id;
        this.name = name;
        this.weight = weight;
    }

    // Getter para id
    public long getId() {
        return id;
    }

    // Getter para name
    public String getName() {
        return name;
    }

    // Getter para weight
    public int getWeight() {
        return weight;
    }

}