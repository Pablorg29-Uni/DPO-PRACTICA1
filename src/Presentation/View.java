package Presentation;

import Business.Entities.Character;
import Business.Entities.Items;
import Business.Entities.Member;
import Business.Entities.Team;
import Business.Entities.Stats;


import java.util.ArrayList; // Ensure this import is included
import java.util.List;

public class View {

    public View() {}

    public void NombrePJ(List<Character> characters,int posicion ) {
        for (Character character : characters) {
            System.out.println("\t" + posicion + ") " + character.getName());
            posicion++;
        }
    }
    public void InfoPJ(Character selectedCharacter,ArrayList<Team> teams) {
        System.out.println("\nID: " + selectedCharacter.getId());
        System.out.println("NAME: " + selectedCharacter.getName());
        System.out.println("WEIGHT: " + selectedCharacter.getWeight() + " kg");
        System.out.println("TEAMS:");
        for (Team team : teams) {
            System.out.println("\t- " + team.getName());
        }
    }
    public void equipos(List<Team> teams, int posicion){
        System.out.println("\nAvailable Teams:");
        for (Team team : teams) {
            System.out.println("\t" + posicion + ") " + team.getName());
            posicion++;
        }
    }
    public void equipo(ArrayList<Member> members, Character character, int i ){
        if (character != null) {
            String role = members.get(i).getRole() != null ? members.get(i).getRole() : "Balanced";
            System.out.printf("Character #%d: %-30s (%s)%n", (i + 1), character.getName(), role);
        } else {
            System.out.println("Character #" + (i + 1) + ": Unknown Character (ID: " + members.get(i).getCharacter().getId() + ")");
        }
    }
    public void statsequipo(Stats teamStats){
        if (teamStats != null) {
            //System.out.println("\nTeam Statistics:");
            System.out.println("\nCombats played: " + teamStats.getGames_played());
            System.out.println("Combats won: " + teamStats.getGames_won());

            // Calcular y mostrar la tasa de victorias
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
    public void itemdetalle(Items selectedItem){
        System.out.println("");
        System.out.println("\tID: " + selectedItem.getId());
        System.out.println("\tNAME: " + selectedItem.getName());
        System.out.println("\tCLASS: " + selectedItem.getClasse());
        System.out.println("\tPOWER: " + selectedItem.getPower());
        System.out.println("\tDURABILITY: " + selectedItem.getDurability());
    }

    public void combatPrincipio(String teamName1, String teamName2, ArrayList<Member> members1, ArrayList<Member> members2) {
        //Aqui imprimes
    }
}
