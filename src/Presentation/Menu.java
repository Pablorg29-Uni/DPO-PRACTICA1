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
            mostrarEquipos();
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

    private void mostrarEquipos() {
        int posicion = 1;
        System.out.println("");
        for (Team team : teamJsonDAO.getAllTeams()) { // Uso directo de TeamJsonDAO
            System.out.println(posicion + ") " + team.getName());
            posicion++;
        }
        System.out.println("\n" + posicion + ") Back");
        Scanner scanner = new Scanner(System.in); // Crear el objeto Scanner
        posicion = scanner.nextInt();
        if (posicion != 0) {
            System.out.println("gestionar equipos");
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
