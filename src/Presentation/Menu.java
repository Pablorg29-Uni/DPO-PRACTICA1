package Presentation;

import Exceptions.PresentationException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private final Controller controller;

    public Menu() {
        this.controller = new Controller();
    }

    public void mostrarMenu() throws PresentationException {
        mostrarTitol();
        System.out.println("Verifying local files...");
        try {
            controller.verificarFiles();
            System.out.println("Files OK.");
        } catch (PresentationException e) {
            System.out.println("Error: The json files can't be accessed");
            System.out.println("Shutting down...");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        int opcion;
        System.out.println("\nStarting program...\n");
        do {
            System.out.println("1) List Characters");
            System.out.println("2) Manage Teams");
            System.out.println("3) List Items");
            System.out.println("4) Simulate Combat");
            System.out.println("\n5) Exit\n");
            System.out.print("Choose an option: ");
            try {
                opcion = scanner.nextInt();
            } catch (InputMismatchException e) {
                opcion = 6;
                scanner.nextLine();
            }
            switch (opcion) {
                case 1 -> controller.mostrarNombresDePersonajes();
                case 2 -> manageTeamsMenu(scanner);
                case 3 -> controller.mostrarItems();
                case 4 -> controller.simulateCombat();
                case 5 -> System.out.println("\nWe hope to see you again!");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (opcion != 5);
        scanner.close();
    }

    private void manageTeamsMenu(Scanner scanner) {
        int opcion;
        do {
            System.out.println("\nTeam management.");
            System.out.println("\t1) Create a Team");
            System.out.println("\t2) List Teams");
            System.out.println("\t3) Delete a Team");
            System.out.println("\n\t4) Back");
            System.out.print("\nChoose an option: ");
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1 -> controller.crearEquipo();
                case 2 -> controller.mostrarEquipos();
                case 3 -> controller.eliminarEquipo();
                case 4 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (opcion != 4);
    }

    private void mostrarTitol() {
        System.out.println(" ___ _    ___  ___   _ ");
        System.out.println("/ __|_ _ _ __ ___ _ _ | |  / __| | _ )_ _ ___| |");
        System.out.println("\\__ \\ || | '_ \\/ -_) '_| | |__\\__ \\_ | _ \\ '_/ _ \\_|");
        System.out.println("|___/\\_,_| .__/\\___|_| |____|___( ) |___/_| \\___(_)");
        System.out.println("        |_|                  |/");
    }
}