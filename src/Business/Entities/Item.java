package Business.Entities;

public abstract class Item {
    protected String name;
    protected int power;
    protected int durability;

    public Item(String name, int power, int durability) {
        this.name = name;
        this.power = power;
        this.durability = durability;
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

    public void setDurability(int i) {
        durability = i;
    }
}