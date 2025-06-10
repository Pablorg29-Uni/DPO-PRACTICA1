package Presentation;

import Business.Entities.Character;
import Business.Entities.Items;
import Business.Entities.Member;
import Business.Entities.Team;
import Business.Entities.Stats;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa la vista para mostrar información en consola.
 */
public class View {

    /**
     * Constructor vacío de la clase View.
     */
    public View() {}

    /**
     * Muestra la lista de personajes con su posición correspondiente.
     *
     * @param characters Lista de personajes.
     * @param posicion Posición inicial.
     */
    public void NombrePJ(List<Character> characters, int posicion) {
        for (Character character : characters) {
            System.out.println("\t" + posicion + ") " + character.getName());
            posicion++;
        }
    }

    /**
     * Muestra la información de un personaje seleccionado y los equipos a los que pertenece.
     *
     * @param selectedCharacter Personaje seleccionado.
     * @param teams Lista de equipos en los que participa.
     */
    public void InfoPJ(Character selectedCharacter, ArrayList<Team> teams) {
        System.out.println("\nID: " + selectedCharacter.getId());
        System.out.println("NAME: " + selectedCharacter.getName());
        System.out.println("WEIGHT: " + selectedCharacter.getWeight() + " kg");
        System.out.println("TEAMS:");
        for (Team team : teams) {
            System.out.println("\t- " + team.getName());
        }
    }

    /**
     * Muestra la lista de equipos con su posición correspondiente.
     *
     * @param teams Lista de equipos.
     * @param posicion Posición inicial.
     */
    public void equipos(List<Team> teams, int posicion) {
        for (Team team : teams) {
            System.out.println("\t" + posicion + ") " + team.getName());
            posicion++;
        }
    }

    /**
     * Muestra la información de un miembro del equipo y su personaje asociado.
     *
     * @param members Lista de miembros del equipo.
     * @param character Personaje del miembro.
     * @param i Índice del miembro en la lista.
     */
    public void equipo(ArrayList<Member> members, Character character, int i) {
        if (character != null) {
            String strategy = members.get(i).getStrategy() != null ? members.get(i).getStrategy() : "Balanced";
            System.out.printf("Character #%d: %-30s (%s)%n", (i + 1), character.getName(), strategy);
        } else {
            System.out.println("Character #" + (i + 1) + ": Unknown Character (ID: " + members.get(i).getCharacter().getId() + ")");
        }
    }

    /**
     * Muestra las estadísticas de un equipo.
     *
     * @param teamStats Estadísticas del equipo.
     */
    public void statsequipo(Stats teamStats) {
        if (teamStats != null) {
            System.out.println("\nCombats played: " + teamStats.getGames_played());
            System.out.println("Combats won: " + teamStats.getGames_won());

            double winRate = teamStats.getGames_played() > 0
                    ? ((double) teamStats.getGames_won() / teamStats.getGames_played()) * 100
                    : 0.0;
            System.out.printf("Win rate: %.0f%%%n", winRate);

            System.out.println("KOs done: " + teamStats.getKO_done());
            System.out.println("KOs received: " + teamStats.getKO_received());
        } else {
            System.out.println("\nNo statistics available for this team.");
        }
    }

    /**
     * Muestra los detalles de un objeto.
     *
     * @param selectedItem Objeto seleccionado.
     */
    public void itemdetalle(Items selectedItem) {
        System.out.println("\n");
        System.out.println("\tID: " + selectedItem.getId());
        System.out.println("\tNAME: " + selectedItem.getName());
        System.out.println("\tCLASS: " + selectedItem.getClasse());
        System.out.println("\tPOWER: " + selectedItem.getPower());
        System.out.println("\tDURABILITY: " + selectedItem.getDurability());
    }

    /**
     * Muestra una lista de ítems rotos.
     *
     * @param brokenItems Lista de ítems rotos.
     */
    public void mostrarItems(ArrayList<String> brokenItems) {
        for (String brokenItem : brokenItems) {
            System.out.println(brokenItem);
        }
    }

    /**
     * Muestra los KOs realizados.
     *
     * @param ko Lista de KOs realizados.
     */
    public void mostrarKO(ArrayList<String> ko) {
        for (String k : ko) {
            System.out.println(k + " flies away! It's a KO!");
        }
    }

    /**
     * Muestra la información de los equipos.
     *
     * @param teamsInfo Lista con la información de los equipos.
     */
    public void mostrarTeam(ArrayList<String> teamsInfo) {
        for (String team : teamsInfo) {
            System.out.println(team);
        }
    }

    /**
     * Muestra la información final del combate.
     *
     * @param finalInfo Lista con la información final del combate.
     */
    public void mostrarFinal(ArrayList<String> finalInfo) {
        for (String s : finalInfo) {
            System.out.println(s);
        }
    }

    /**
     * Muestra los detalles del ataque y del daño recibido.
     *
     * @param attackDetails Detalles del ataque realizado.
     * @param recievedDetails Detalles del daño recibido.
     */
    public void printAttackDetails(String attackDetails, String recievedDetails) {
        System.out.println(attackDetails);
        System.out.println(recievedDetails);
    }
}
