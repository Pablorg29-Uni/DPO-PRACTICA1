package Business.Entities;

import java.util.ArrayList;

public class Team {
    private String name;
    private ArrayList<Member> members;

    public Team(String name, long id1, long id2, long id3, long id4) {
        this.name = name;
        members = new ArrayList<>();
        members.add(new Member(id1));
        members.add(new Member(id2));
        members.add(new Member(id3));
        members.add(new Member(id4));
    }

    public ArrayList<Member> getMembers() {
        return members;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
