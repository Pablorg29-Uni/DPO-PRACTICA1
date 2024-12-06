package Presentation;

import Business.Entities.Character;
import Business.Entities.Items;
import Business.Entities.Team;
import Persistence.CharacterJsonDAO;
import Persistence.ItemsJsonDAO;
import Persistence.TeamJsonDAO;

import java.util.List;
import java.util.Scanner;

public class Controller {
    private final CharacterJsonDAO characterJsonDAO;
    private final TeamJsonDAO teamJsonDAO;
    private final ItemsJsonDAO itemsJsonDAO;

    public Controller() {
        this.characterJsonDAO = new CharacterJsonDAO();
        this.teamJsonDAO = new TeamJsonDAO();
        this.itemsJsonDAO = new ItemsJsonDAO();
    }

    public void mostrarNombresDePersonajes() {
        Scanner scanner = new Scanner(System.in);
        int posicion = 1;
        System.out.println("");
        List<Character> characters = characterJsonDAO.getAllCharacters();
        for (Character character : characters) {
            System.out.println("\t" + posicion + ") " + character.getName());
            posicion++;
        }
        System.out.println("\n\t0) Back\n\nChoose an option: ");
        int opcion = scanner.nextInt();

        // Validar la selección
        if (opcion == 0) {
            System.out.println("Returning to the previous menu...");
            return; // Regresa al menú anterior
        }

        if (opcion > 0 && opcion <= characters.size()) {
            Character selectedCharacter = characters.get(opcion - 1);

            // Mostrar información del personaje seleccionado
            System.out.println("\nID: " + selectedCharacter.getId());
            System.out.println("NAME: " + selectedCharacter.getName());
            System.out.println("WEIGHT: " + selectedCharacter.getWeight() + " kg");

            // Obtener los equipos a los que pertenece este personaje
            List<Team> teams = teamJsonDAO.getAllTeams();
            System.out.println("TEAMS:");
            for (Team team : teams) {
                if (team.containsCharacter(selectedCharacter.getId())) {
                    System.out.println("\t- " + team.getName());
                }
            }

            // Esperar a que el usuario continúe
            System.out.println("\n<Press any key to continue...>");
            scanner.nextLine(); // Consumir el salto de línea
            scanner.nextLine(); // Esperar la entrada del usuario
        }
        }



    public void crearEquipo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the team's name: ");
        String teamName = scanner.nextLine();

        long[] ids = new long[4];
        for (int i = 0; i < 4; i++) {
            System.out.print("\nPlease enter name or id for character #" + (i + 1) + ": ");
            String input = scanner.nextLine();
            try {
                long id = Long.parseLong(input);
                Character character = characterJsonDAO.getCharacterById(id);
                if (character == null) {
                    System.out.println("Error: Character with ID " + id + " not found.");
                    return;
                }
                ids[i] = id;
            } catch (NumberFormatException e) {
                Character character = characterJsonDAO.getCharacterByName(input);
                if (character == null) {
                    System.out.println("Error: Character with name " + input + " not found.");
                    return;
                }
                ids[i] = character.getId();
            }
        }

        if (teamJsonDAO.saveTeam(new Team(teamName, ids[0], ids[1], ids[2], ids[3]))) {
            System.out.println("\nTeam " + teamName + " has been successfully created!");
        } else {
            System.out.println("\nError: Failed to save the team. Please try again.");
        }
    }

    public void mostrarEquipos() {
        int posicion = 1;
        System.out.println("");

        List<Team> teams = teamJsonDAO.getAllTeams();
        for (Team team : teams) {
            System.out.println("\t" + posicion + ") " + team.getName());
            posicion++;
        }
        posicion = 1;
        System.out.println("\n\t" + (posicion - 1) + ") Back\n\nChoose an option: ");
        Scanner scanner = new Scanner(System.in);
        posicion = scanner.nextInt();
        while (posicion != 0) {
            System.out.println("info de 1 team");
            posicion = scanner.nextInt();
        }
    }
    public void eliminarEquipo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter the name of the team to remove: ");
        String teamName = scanner.nextLine();

        if (teamJsonDAO.getTeam(teamName) == null) {
            System.out.println("Error: Team not found.");
            return;
        }

        System.out.print("Are you sure you want to remove \"" + teamName + "\"? (Yes/No): ");
        String confirmation = scanner.nextLine();
        if (confirmation.equalsIgnoreCase("yes")) {
            if (teamJsonDAO.eliminateTeam(teamName)) {
                System.out.println("\nTeam \"" + teamName + "\" has been removed.");
            } else {
                System.out.println("\nError: Failed to remove the team.");
            }
        } else {
            System.out.println("\nTeam deletion cancelled.");
        }
    }

    public void mostrarItems() {
        int posicion = 1;
        System.out.println("");
        List<Items> items = itemsJsonDAO.getAllItems();
        for (Items item : items) {
            System.out.println("\t" + posicion + ") " + item.getName());
            posicion++;
        }
        posicion = 1;
        System.out.println("\n\t" + (posicion - 1) + ") Back\n\nChoose an option: ");
        Scanner scanner = new Scanner(System.in);
        posicion = scanner.nextInt();
        while (posicion != 0) {
            System.out.println("info de 1 team");
            posicion = scanner.nextInt();
        }
    }
}