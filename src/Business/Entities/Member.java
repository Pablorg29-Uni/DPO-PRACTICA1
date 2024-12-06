package Business.Entities;

public class Member {
    private long id;
    private String strategy;
    private String role;

    public String getRole() {
        return role;
    }




    public Member(long id, String strategy) {
        this.id = id;
        this.strategy = strategy;
    }

    public long getId() {
        return id;
    }

    public String getStrategy() {
        return strategy;
    }

}
