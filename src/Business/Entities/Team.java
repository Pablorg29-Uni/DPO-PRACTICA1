package Business.Entities;

import java.util.ArrayList;

public class Team {
    private String name;
    private ArrayList<Member> members;

    public Team() {}
    public Team(String name, long id1, long id2, long id3, long id4) {
        this.name = name;
        members = new ArrayList<>();
        //members.get(0).setId = id1;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    /*public boolean containsCharacter(long characterId) {
        if (members[0] != null || members[1] != null || members[2] != null || members[3] != null ) {
            if (members[0].getCharacter().getId() == characterId || members[1].getCharacter().getId() == characterId || members[2].getCharacter().getId() == characterId || members[3].getCharacter().getId() == characterId) {
                return true;
            }
        }
        return false;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMembers(ArrayList<Member> members) {
        this.members = members;
    }
}
