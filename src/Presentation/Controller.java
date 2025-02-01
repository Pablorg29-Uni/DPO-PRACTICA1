package Presentation;

import Business.Entities.Character;
import Business.CharacterManager;
import Business.Entities.Items;
import Business.ItemsManager;
import Business.Entities.Member;
import Business.Entities.Team;
import Business.TeamManager;
import Business.Entities.Stats;
import Business.StatsManager;


import java.util.ArrayList; // Ensure this import is included
import java.util.List;
import java.util.Scanner;

public class Controller {
    private final TeamManager teammanager;
    private final StatsManager statsmanager;
    private final ItemsManager itemmanager;
    private final CharacterManager charactermanager;


    public Controller() {
        this.statsmanager = new StatsManager();
        this.itemmanager = new ItemsManager();
        this.charactermanager = new CharacterManager();
        this.teammanager = new TeamManager();

    }


    public void mostrarNombresDePersonajes() {
        Scanner scanner = new Scanner(System.in);
        int posicion = 1;
        System.out.println("");
        List<Character> characters = charactermanager.getCharacters();
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
            List<Team> teams = teammanager.showTeams();
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
                Character character = charactermanager.getCharacter(id);
                if (character == null) {
                    System.out.println("Error: Character with ID " + id + " not found.");
                    return;
                }
                ids[i] = id;
            } catch (NumberFormatException e) {
                Character character = charactermanager.getCharacter2(input);
                if (character == null) {
                    System.out.println("Error: Character with name " + input + " not found.");
                    return;
                }
                ids[i] = character.getId();
            }
        }

        if (teammanager.createTeam(teamName, ids[0], ids[1], ids[2], ids[3])) {
            System.out.println("\nTeam " + teamName + " has been successfully created!");
        } else {
            System.out.println("\nError: Failed to save the team. Please try again.");
        }
    }

    public void mostrarEquipos() {
        Scanner scanner = new Scanner(System.in);

        // Obtener la lista de equipos
        List<Team> teams = teammanager.showTeams();

        if (teams.isEmpty()) {
            System.out.println("\nNo teams available.");
            return;
        }

        // Mostrar la lista de equipos
        int posicion = 1;
        System.out.println("\nAvailable Teams:");
        for (Team team : teams) {
            System.out.println("\t" + posicion + ") " + team.getName());
            posicion++;
        }
        System.out.println("\n\t0) Back\n\nChoose an option: ");

        // Leer opción seleccionada
        int opcion = scanner.nextInt();

        // Validar la selección
        if (opcion == 0) {
            System.out.println("Returning to the previous menu...");
            return;
        }

        if (opcion > 0 && opcion <= teams.size()) {
            Team selectedTeam = teams.get(opcion - 1);

            // Mostrar detalles del equipo
            System.out.println("\nTeam name: " + selectedTeam.getName() + "\n");

            // Mostrar miembros del equipo
            Member[] members = selectedTeam.getMembers();
            for (int i = 0; i < members.length; i++) {
                Member member = members[i];
                Character character = charactermanager.getCharacter(member.getCharacter().getId());

                if (character != null) {
                    // Establecer el rol (por defecto "Balanced")
                    String role = member.getRole() != null ? member.getRole() : "Balanced";

                    // Alinear la salida con el nombre y el rol
                    System.out.printf("Character #%d: %-30s (%s)%n", (i + 1), character.getName(), role);
                } else {
                    System.out.println("Character #" + (i + 1) + ": Unknown Character (ID: " + member.getCharacter().getId() + ")");
                }
            }

            // Mostrar estadísticas del equipo
            Stats teamStats = statsmanager.getStat(selectedTeam.getName());
            if (teamStats != null) {
                System.out.println("\nTeam Statistics:");
                System.out.println("Combats played: " + teamStats.getGamesPlayed());
                System.out.println("Combats won: " + teamStats.getGamesWon());

                // Calcular y mostrar la tasa de victorias
                double winRate = teamStats.getGamesPlayed() > 0
                        ? ((double) teamStats.getGamesWon() / teamStats.getGamesPlayed()) * 100
                        : 0.0;
                System.out.printf("Win rate: %.0f%%%n", winRate);

                System.out.println("KOs done: " + teamStats.getKoDone());
                System.out.println("KOs received: " + teamStats.getKoRecieved());
            } else {
                System.out.println("\nNo statistics available for this team.");
            }

            // Esperar que el usuario presione una tecla para continuar
            System.out.println("\n<Press any key to continue...>");
            scanner.nextLine(); // Consumir el salto de línea pendiente
            scanner.nextLine(); // Esperar entrada del usuario
        } else {
            System.out.println("\nInvalid option. Returning to the previous menu...");
        }
    }
    public void eliminarEquipo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter the name of the team to remove: ");
        String teamName = scanner.nextLine();

        if (teammanager.getTeam(teamName) == null) {
            System.out.println("Error: Team not found.");
            return;
        }

        System.out.print("Are you sure you want to remove \"" + teamName + "\"? (Yes/No): ");
        String confirmation = scanner.nextLine();
        if (confirmation.equalsIgnoreCase("yes")) {
            if (teammanager.eliminateTeam(teamName)) {
                System.out.println("\nTeam \"" + teamName + "\" has been removed.");
            } else {
                System.out.println("\nError: Failed to remove the team.");
            }
        } else {
            System.out.println("\nTeam deletion cancelled.");
        }
    }

    public void mostrarItems() {
        Scanner scanner = new Scanner(System.in);

        // Obtener la lista de items
        List<Items> items = itemmanager.showItems();

        // Si no hay items disponibles
        if (items.isEmpty()) {
            System.out.println("\nNo items available.");
            return;
        }

        // Mostrar la lista de items
        int posicion = 1;
        System.out.println("");
        for (Items item : items) {
            System.out.println("\t" + posicion + ") " + item.getName());
            posicion++;
        }
        System.out.println("\n\t0) Back\n\nChoose an option: ");

        // Leer la opción seleccionada
        int opcion = scanner.nextInt();

        // Validar la selección
        while (opcion != 0) {
            if (opcion > 0 && opcion <= items.size()) {
                // Obtener el item seleccionado
                Items selectedItem = items.get(opcion - 1);

                // Mostrar detalles del item
                System.out.println("");
                System.out.println("\tID: " + selectedItem.getId());
                System.out.println("\tNAME: " + selectedItem.getName());
                System.out.println("\tCLASS: " + selectedItem.getClasse());
                System.out.println("\tPOWER: " + selectedItem.getPower());
                System.out.println("\tDURABILITY: " + selectedItem.getDurability());

                // Esperar que el usuario presione una tecla para continuar
                System.out.println("\n<Press any key to continue...>");
                scanner.nextLine(); // Consumir el salto de línea pendiente
                scanner.nextLine(); // Esperar la entrada del usuario
            } else {
                System.out.println("\nInvalid option. Please choose again.");
            }

            // Mostrar nuevamente la lista de items y permitir la selección
            System.out.println("\nAvailable Items:");
            posicion = 1;
            for (Items item : items) {
                System.out.println("\t" + posicion + ") " + item.getName());
                posicion++;
            }
            System.out.println("\n\t0) Back\n\nChoose an option: ");
            opcion = scanner.nextInt();  // Leer la opción seleccionada
        }

        // Regresar al menú anterior si la opción es 0
        System.out.println("Returning to the previous menu...");
    }
}