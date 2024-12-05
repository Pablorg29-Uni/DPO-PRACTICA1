package Business.Entities;

import java.util.ArrayList;

public class Team {
    private String name;
    private Member[] members;

    public Team(String name, long id1, long id2, long id3, long id4) {
        this.name = name;
        this.members = new Member[4];
        this.members[0] = new Member(id1, "Balanced");
        this.members[1] = new Member(id2, "Balanced");
        this.members[2] = new Member(id3, "Balanced");
        this.members[3] = new Member(id4, "Balanced");
    }

    public Member[] getMembers() {
        return members;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Team team = (Team) obj;

        return name != null ? name.equals(team.name) : team.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

}
