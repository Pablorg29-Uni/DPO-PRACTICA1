package Business;

import java.util.ArrayList;

public class Team {
    private String name;
    private ArrayList<String> members;
    private String[] strategy;

    public Team(String name) {
        this.name = name;
        this.members = new ArrayList<>(); // Inicializa un array vacío
        this.strategy = new String[0]; // Inicializa un array vacío
    }

    // Getter para 'name'
    public String getName() {
        return name;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    // Getter para 'strategy'
    public String[] getStrategies() {
        return strategy;
    }
}
