package Business.Entities;

public class Member {
    private int id;
    private String strategy;

    public Member(int id, String strategy) {
        this.id = id;
        this.strategy = strategy;
    }

    public int getId() {
        return id;
    }

    public String getStrategy() {
        return strategy;
    }

}
