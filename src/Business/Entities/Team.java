package Business.Entities;

import java.util.ArrayList;

public class Team {
    private String name;
    private ArrayList<Member> members;

    /**
     * Constructor de la clase Team.
     *
     * @param name Nombre del equipo.
     * @param id1 ID del primer miembro.
     * @param id2 ID del segundo miembro.
     * @param id3 ID del tercer miembro.
     * @param id4 ID del cuarto miembro.
     */
    public Team(String name, long id1, long id2, long id3, long id4) {
        this.name = name;
        members = new ArrayList<>();
        members.add(new Member(id1));
        members.add(new Member(id2));
        members.add(new Member(id3));
        members.add(new Member(id4));
    }

    /**
     * Obtiene la lista de miembros del equipo.
     *
     * @return Lista de miembros del equipo.
     */
    public ArrayList<Member> getMembers() {
        return members;
    }

    /**
     * Obtiene el nombre del equipo.
     *
     * @return Nombre del equipo.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece un nuevo nombre para el equipo.
     *
     * @param name Nuevo nombre del equipo.
     */
    public void setName(String name) {
        this.name = name;
    }
}
