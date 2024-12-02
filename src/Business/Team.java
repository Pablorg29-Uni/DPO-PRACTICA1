package Business;

public class Team {
    private String name;
    private String[] members;
    private String[] strategy;

    public Team(String name) {
        this.name = name;
        this.members = new String[0]; // Inicializa un array vacío
        this.strategy = new String[0]; // Inicializa un array vacío
    }

    // Getter para 'name'
    public String getName() {
        return name;
    }

    // Getter para 'members'
    public String[] getMembers() {
        return members;
    }

    // Getter para 'strategy'
    public String[] getStrategies() {
        return strategy;
    }
}
