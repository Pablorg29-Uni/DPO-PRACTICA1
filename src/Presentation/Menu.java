package Presentation;

import java.util.Scanner;

public class Menu {
    public void mostrarMenu() {
        System.out.print("Starting program...\n\n ");
        System.out.print("\t1) List Characters\n\t2) Manage Teams\n\t3) List Items\n\t4) Simulate Combat\n\n\t5)Exit\n");

        Scanner scanner = new Scanner(System.in); // Crear el objeto Scanner
        System.out.print("Choose an option: "); // Solicitar un número
        int opcion = scanner.nextInt(); // Leer el número entero

        if (opcion == 1) {

        }

        else if (opcion == 2) {

        }

        else if (opcion == 3) {

        }

        else if (opcion == 4) {

        }
        if (opcion == 5) {
            System.out.println("\nWe hope to see you again!");
            System.exit(0);
        }

        scanner.close(); // Cerrar el Scanner


    }
}
