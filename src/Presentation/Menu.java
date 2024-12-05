package Presentation;

import java.util.Scanner;
import Persistence.CharacterJsonDAO;
import Business.Entities.Character;
import Persistence.TeamJsonDAO;
import Business.Entities.Team;
import Persistence.ItemsJsonDAO;
import Business.Entities.Items;

public class Menu {
    private final CharacterJsonDAO characterJsonDAO;
    private final TeamJsonDAO teamJsonDAO;
    private final ItemsJsonDAO itemsJsonDAO;


    public Menu() {
        this.characterJsonDAO = new CharacterJsonDAO();
        this.teamJsonDAO = new TeamJsonDAO();
        this.itemsJsonDAO = new ItemsJsonDAO();
    }


    public void mostrarMenu() {
        System.out.print("Starting program...\n\n ");
        System.out.print("\t1) List Characters\n\t2) Manage Teams\n\t3) List Items\n\t4) Simulate Combat\n\n\t5)Exit\n");

        Scanner scanner = new Scanner(System.in); // Crear el objeto Scanner
        System.out.print("Choose an option: "); // Solicitar un número
        int opcion = scanner.nextInt(); // Leer el número entero

        if (opcion == 1) {

            mostrarNombresDePersonajes();

        }

        else if (opcion == 2) {
            while(opcion != 4) {
                System.out.println("\n1) Create a Team\n2) List Teams\n3) Delete a Team\n\n4)Back");
                opcion = scanner.nextInt();
                if (opcion == 1) {
                    if (opcion == 1) {
                        crearEquipo();
                    }
                    if (opcion == 2) {
                        mostrarEquipos();
                    }
                    if (opcion == 3) {
                        eliminarEquipo();
                    }
                }
            }
        }


        else if (opcion == 3) {
            mostrarItems();
        }

        else if (opcion == 4) {

        }
        if (opcion == 5) {
            System.out.println("\nWe hope to see you again!");
            System.exit(0);
        }

        scanner.close(); // Cerrar el Scanner

    }
    private void mostrarNombresDePersonajes() {
        int posicion= 1;
        System.out.println("");
        for (Character character : characterJsonDAO.getAllCharacters()) { // Uso explícito de getAllCharacters

            System.out.println(posicion + ") " + character.getName());
            posicion++;
        }
        posicion=1;
        System.out.println("\n" + (posicion-1) + ") Back");
        Scanner scanner = new Scanner(System.in); // Crear el objeto Scanner
        posicion = scanner.nextInt();
        if (posicion != 0){
            System.out.println("info de 1 pj");

        }
    }
    private void crearEquipo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the team's name: ");
        String teamName = scanner.nextLine();  // Nombre del equipo

        long[] ids = new long[4];  // Array de IDs de personajes
        for (int i = 0; i < 4; i++) {
            System.out.print("\nPlease enter name or id for character #" + (i + 1) + ": ");
            String input = scanner.nextLine();  // Puede ser un nombre o un ID

            // Verificar si el input es un número o un nombre
            try {
                long id = Long.parseLong(input);  // Intentamos convertir el input a un ID numérico
                Character character = characterJsonDAO.getCharacterById(id);  // Método para obtener personaje por ID
                if (character == null) {
                    System.out.println("Error: Character with ID " + id + " not found. Returning to Manage Teams menu.");
                    return;  // Salir si no se encuentra el personaje por ID
                }
                ids[i] = id;  // Asignar ID al array de personajes
            } catch (NumberFormatException e) {
                // Si el input no es un número, tratamos de buscarlo por nombre
                Character character = characterJsonDAO.getCharacterByName(input);
                if (character == null) {
                    System.out.println("Error: Character with name " + input + " not found. Returning to Manage Teams menu.");
                    return;  // Salir si no se encuentra el personaje por nombre
                }
                ids[i] = character.getId();  // Asignar ID al array de personajes
            }
        }

        // Crear el equipo si todos los personajes son válidos
        if (teamJsonDAO.saveTeam(new Team(teamName, ids[0], ids[1], ids[2], ids[3]))) {
            System.out.println("\nTeam " + teamName + " has been successfully created!");
        } else {
            System.out.println("\nError: Failed to save the team. Please try again.");
        }
    }

    private void mostrarEquipos() {
        int posicion = 1;
        System.out.println("");
        for (Team team : teamJsonDAO.getAllTeams()) { // Uso directo de TeamJsonDAO
            System.out.println(posicion + ") " + team.getName());
            posicion++;
        }
        System.out.println("\n0) Back\n\nChoose an option: ");
        Scanner scanner = new Scanner(System.in); // Crear el objeto Scanner
        posicion = scanner.nextInt();
    }

    private void eliminarEquipo() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nEnter the name of the team to remove: ");
        String teamName = scanner.nextLine();

        Team teamToRemove = teamJsonDAO.getTeam(teamName);
        if (teamToRemove == null) {
            System.out.println("Error: Team not found (" + teamName + "). Returning to Manage Teams menu.");
            return; // Salir si no se encuentra el equipo
        }

        // Confirmar eliminación
        System.out.print("\nAre you sure you want to remove \"" + teamName + "\"? (Yes/No): ");
        String confirmation = scanner.nextLine();
        if (confirmation.equalsIgnoreCase("yes")) {
            if (teamJsonDAO.eliminateTeam(teamName)) {
                System.out.println("\n\"" + teamName + "\" has been removed from the system.");
            } else {
                System.out.println("\nError: Failed to remove the team. Please try again.");
            }
        } else {
            System.out.println("\nTeam deletion cancelled. Returning to Manage Teams menu.");
        }
    }

    private void mostrarItems() {
        int posicion = 1;
        System.out.println("");
        for (Items item : itemsJsonDAO.getAllItems()) { // Uso directo de ItemsJsonDAO
            System.out.println(posicion + ") " + item.getName());
            posicion++;
        }
        posicion=1;
        System.out.println("\n" + (posicion-1) + ") Back");
        Scanner scanner = new Scanner(System.in); // Crear el objeto Scanner
        posicion = scanner.nextInt();
        if (posicion != 0) {
            System.out.println("gestionar item");
        }
    }
}
