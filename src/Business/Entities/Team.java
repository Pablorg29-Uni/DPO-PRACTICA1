package Business.Entities;

import java.util.ArrayList;

/**
 * Representa un equipo dentro del juego.
 */
public class Team {
    private String name;
    private ArrayList<Member> members;

    public Team() {
        this.members = new ArrayList<>();
    }

    /**
     * Constructor de la clase Team.
     *
     * @param name Nombre del equipo.
     * @param id1 ID del primer miembro.
     * @param id2 ID del segundo miembro.
     * @param id3 ID del tercer miembro.
     * @param id4 ID del cuarto miembro.
     * @param s1 Estrategia del primer miembro.
     * @param s2 Estrategia del segundo miembro.
     * @param s3 Estrategia del tercer miembro.
     * @param s4 Estrategia del cuarto miembro.
     */
    public Team(String name, long id1, long id2, long id3, long id4, String s1, String s2, String s3, String s4) {
        this.name = name;
        members = new ArrayList<>();
        Member m1 = new Member(id1, s1);
        Member m2 = new Member(id2, s2);
        Member m3 = new Member(id3, s3);
        Member m4 = new Member(id4, s4);
        members.add(m1);
        members.add(m2);
        members.add(m3);
        members.add(m4);
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

    public void postDeserialize() {
        for (Member member : members) {
            member.postDeserialize();
        }
    }
}
