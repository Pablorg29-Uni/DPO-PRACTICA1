package Business.Entities;

import java.util.ArrayList;

public class Team {
    private String name;
    private Member[] members;

    public Team(String name, int id1, int id2, int id3, int id4) {
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



}
