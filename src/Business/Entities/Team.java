package Business.Entities;

import java.util.ArrayList;
/**
 * Representa un equipo dentro del juego.
 */
public class Team {
    private String name;
    private ArrayList<Member> members;

    /**
     * Constructor de la clase Team.
     *
     * @param name   Nombre del equipo.
     * @param id1    ID del primer miembro.
     * @param id2    ID del segundo miembro.
     * @param id3    ID del tercer miembro.
     * @param id4    ID del cuarto miembro.
     */
    public Team(String name, long id1, long id2, long id3, long id4, String s1, String s2, String s3, String s4) {
        this.name = name;
        members = new ArrayList<>();
        members.add(new Member(id1, s1));
        members.add(new Member(id2, s2));
        members.add(new Member(id3, s3));
        members.add(new Member(id4, s4));
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
