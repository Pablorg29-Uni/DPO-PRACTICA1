package Business.Entities;

public class Character {

    private long  id;
    private String name;
    private int weight;

    public Character(long  id, String name, int weight) {
        this.id = id;
        this.name = name;
        this.weight = weight;
    }

    public long Character() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                '}';
    }
}
