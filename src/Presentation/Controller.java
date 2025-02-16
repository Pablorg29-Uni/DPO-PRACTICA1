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
import Business.CombatManager;
import Exceptions.BusinessException;
import Exceptions.PresentationException;


import java.util.ArrayList; // Ensure this import is included
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Controller {
    private final CombatManager combatManager;
    private final View view;
    private final TeamManager teammanager;
    private final StatsManager statsmanager;
    private final ItemsManager itemmanager;
    private final CharacterManager charactermanager;


    public Controller() {
        this.combatManager = new CombatManager();
        this.statsmanager = new StatsManager();
        this.itemmanager = new ItemsManager();
        this.charactermanager = new CharacterManager();
        this.teammanager = new TeamManager();
        this.view = new View();
    }


    public void mostrarNombresDePersonajes() throws PresentationException {
        Scanner scanner = new Scanner(System.in);
        int posicion = 1;
        System.out.println();
        List<Character> characters;
        try {
            characters = charactermanager.getCharacters();
        } catch (BusinessException e) {
            throw new PresentationException(e.getMessage());
        }
        view.NombrePJ(characters, posicion);
        System.out.println("\n\t0) Back\n\nChoose an option: ");
        int opcion;
        try {
            opcion = scanner.nextInt();
        } catch (InputMismatchException e) {
            opcion = characters.size()+1;
            scanner.nextLine();
        }

        // Validar la selección
        if (opcion == 0) {
            System.out.println("Returning to the previous menu...");
            return; // Regresa al menú anterior
        }

        if (opcion > 0 && opcion <= characters.size()) {
            try {
                Character selectedCharacter = characters.get(opcion - 1);
                ArrayList<Team> teams = teammanager.teamsWithPlayer(selectedCharacter.getId());
                view.InfoPJ(selectedCharacter, teams);
            } catch (BusinessException e) {
                throw new PresentationException(e.getMessage());
            }

            // Esperar a que el usuario continúe
            System.out.println("\n<Press any key to continue...>");
            scanner.nextLine(); // Consumir el salto de línea
            scanner.nextLine(); // Esperar la entrada del usuario
        } else {
            throw new PresentationException("");
        }
    }


    public void crearEquipo() throws PresentationException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the team's name: ");
        String teamName = scanner.nextLine();

        long[] ids = new long[4];
        for (int i = 0; i < 4; i++) {
            System.out.print("\nPlease enter name or id for character #" + (i + 1) + ": ");
            String input = scanner.nextLine();
            try {
                long id = Long.parseLong(input);
                charactermanager.getCharacter(id); //Per veure si dona erorr al obtenir el character
                ids[i] = id;
            } catch (NumberFormatException | BusinessException e) {
                try {
                    Character character = charactermanager.getCharacter2(input);
                    ids[i] = character.getId();
                } catch (BusinessException ex) {
                    throw new PresentationException(ex.getMessage());
                }
            }
        }
        try {
            teammanager.createTeam(teamName, ids[0], ids[1], ids[2], ids[3]);
            System.out.println("\nTeam " + teamName + " has been successfully created!");
        } catch (BusinessException e) {
            throw new PresentationException(e.getMessage());
        }

    }

    public void mostrarEquipos() throws PresentationException {
        Scanner scanner = new Scanner(System.in);

        // Obtener la lista de equipos
        List<Team> teams = null;
        try {
            teams = teammanager.showTeams();
        } catch (BusinessException e) {
            throw new PresentationException(e.getMessage());
        }

        if (teams.isEmpty()) {
            System.out.println("\nNo teams available.");
            return;
        }

        // Mostrar la lista de equipos
        int posicion = 1;
        view.equipos(teams, posicion);
        System.out.println("\n\t0) Back\n\nChoose an option: ");

        // Leer opción seleccionada
        int opcion;
        try {
            opcion = scanner.nextInt();
        } catch (InputMismatchException e) {
            opcion = teams.size() + 1;
            scanner.nextLine();
        }


        // Validar la selección
        if (opcion == 0) {
            System.out.println("Returning to the previous menu...");
            return;
        }

        if (opcion > 0 && opcion <= teams.size()) {
            Team selectedTeam = teams.get(opcion - 1);

            // Mostrar detalles del equipo
            System.out.println("\nTeam name: " + selectedTeam.getName() + "\n");

            try {
                // Mostrar miembros del equipo
                ArrayList<Member> members = teammanager.getTeam(selectedTeam.getName()).getMembers();
                for (int i = 0; i < members.size(); i++) {
                    Character character = charactermanager.getCharacter(members.get(i).getId());
                    view.equipo(members, character, i);
                }
                // Mostrar estadísticas del equipo
                Stats teamStats = statsmanager.getStat(selectedTeam.getName());
                view.statsequipo(teamStats);
            } catch (BusinessException e) {
                throw new PresentationException(e.getMessage());
            }
            // Esperar que el usuario presione una tecla para continuar
            System.out.println("\n<Press any key to continue...>");
            scanner.nextLine(); // Consumir el salto de línea pendiente
            scanner.nextLine(); // Esperar entrada del usuario
        } else {
            System.out.println("\nInvalid option. Returning to the previous menu...");
        }
    }

    public void eliminarEquipo() throws PresentationException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter the name of the team to remove: ");
        String teamName = scanner.nextLine();

        try {
            teammanager.getTeam(teamName);
        } catch (BusinessException e) {
            throw new PresentationException(e.getMessage());
        }

        System.out.print("Are you sure you want to remove \"" + teamName + "\"? (Yes/No): ");
        String confirmation = scanner.nextLine();
        if (confirmation.equalsIgnoreCase("yes")) {
            try {
                teammanager.eliminateTeam(teamName);
            } catch (BusinessException e) {
                throw new PresentationException(e.getMessage());
            }
        } else {
            System.out.println("\nTeam deletion cancelled.");
        }
    }

    public void mostrarItems() throws PresentationException {
        Scanner scanner = new Scanner(System.in);

        // Obtener la lista de items
        List<Items> items;
        try {
            items = itemmanager.showItems();
        } catch (BusinessException e) {
            throw new PresentationException(e.getMessage());
        }

        // Si no hay items disponibles
        if (items.isEmpty()) {
            System.out.println("\nNo items available.");
            return;
        }

        // Mostrar la lista de items
        int posicion = 1;
        System.out.println();
        for (Items item : items) {
            System.out.println("\t" + posicion + ") " + item.getName());
            posicion++;
        }
        System.out.println("\n\t0) Back\n\nChoose an option: ");

        // Leer la opción seleccionada
        int opcion;
        try {
            opcion = scanner.nextInt();
        } catch (InputMismatchException e) {
            opcion = items.size() + 1;
            scanner.nextLine();
        }
        // Validar la selección
        while (opcion != 0) {
            if (opcion > 0 && opcion <= items.size()) {
                // Obtener el item seleccionado
                Items selectedItem = items.get(opcion - 1);
                view.itemdetalle(selectedItem);
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
            try {
                opcion = scanner.nextInt();
            } catch (InputMismatchException e) {
                opcion = items.size() + 1;
                scanner.nextLine();
            }
        }

        // Regresar al menú anterior si la opción es 0
        System.out.println("Returning to the previous menu...");
    }

    public void simulateCombat() throws PresentationException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nStarting simulator...\nLooking for available teams...\n");

        // Obtener lista de equipos
        List<Team> teams;
        int posicion = 1;
        try {
            teams = teammanager.showTeams();
        } catch (BusinessException e) {
            throw new PresentationException(e.getMessage());
        }

        // Mostrar equipos disponibles
        view.equipos(teams, posicion);
        int opcion;
        // Seleccionar el primer equipo
        System.out.println("\nChoose team #1: ");
        Team selectedTeam1;
        while (true) {
            try {
                opcion = scanner.nextInt();
            } catch (InputMismatchException e) {
                opcion = teams.size() + 1;
                scanner.nextLine();
            }
            if (opcion > 0 && opcion <= teams.size()) {
                selectedTeam1 = teams.get(opcion - 1);
                break;
            } else {
                System.out.println("\nInvalid option. Please choose again.");
            }
        }

        // Seleccionar el segundo equipo
        System.out.println("\nChoose team #2: ");
        Team selectedTeam2;
        while (true) {
            try {
                opcion = scanner.nextInt();
            } catch (InputMismatchException e) {
                opcion = teams.size() + 1;
                scanner.nextLine();
            }

            if (opcion > 0 && opcion <= teams.size()) {
                selectedTeam2 = teams.get(opcion - 1);
                break;
            } else {
                System.out.println("\nInvalid option. Please choose again.");
            }
        }
        System.out.println("\nInitializing teams...\n");

        try {
            // Mostrar los detalles de los equipos seleccionados
            System.out.println("Team #1 – " + selectedTeam1.getName());
            mostrarTeamMembers(selectedTeam1);

            System.out.println("\nTeam #2 – " + selectedTeam2.getName());
            mostrarTeamMembers(selectedTeam2);
        } catch (BusinessException e) {
            throw new PresentationException(e.getMessage());
        }

        // Mensaje final indicando que los equipos están listos
        System.out.println("\nCombat ready!");
        System.out.println("\n<Press any key to continue...>");
        scanner.nextLine(); // Esperar entrada del usuario

        // Inicializar combate con los equipos seleccionados
        Team team1;
        Team team2;

        try {
            Team[] combatTeams = combatManager.initCombat(selectedTeam1, selectedTeam2);
            team1 = combatTeams[0];
            team2 = combatTeams[1];
        } catch (BusinessException e) {
            throw new PresentationException("Error initializing the combat teams!");
        }

        // Iniciar las rondas de combate
        int round = 1;
        boolean combatFinished = false;
        int result = 0;

        while (!combatFinished) {
            System.out.println("\n--- ROUND " + round + " ---");
            System.out.println("Team #1 – " + team1.getName());
            printTeamInfo(team1);
            System.out.println("\nTeam #2 – " + team2.getName());
            printTeamInfo(team2);
            System.out.println();

            // Realizar ataques y mostrar la información del ataque
            try {
                combatManager.executarCombat();  // Ejecuta los ataques de ambos equipos
            } catch (BusinessException e) {
                throw new PresentationException(e.getMessage());
            }

            // Imprimir los resultados de cada ataque
            roundStats(team1);
            System.out.println();
            roundStats(team2);
            System.out.println();

            printBrokenItems(team1);
            System.out.println();
            printBrokenItems(team2);
            System.out.println();

            printKo(team1, team2);
            System.out.println();

            // Verificar si el combate ha terminado
            result = combatManager.comprovarEstatCombat();
            switch (result) {
                case 1:
                    System.out.println("\nTeam #1 wins!\n");
                    combatFinished = true;
                    break;
                case 2:
                    System.out.println("\nTeam #2 wins!\n");
                    combatFinished = true;
                    break;
                case 3:
                    System.out.println("\nIt's a tie!\n");
                    combatFinished = true;
                    break;
                default:
                    round++;  // Incrementa la ronda
                    break;
            }
        }
        printFinalInfo(team1);
        System.out.println();
        printFinalInfo(team2);
        try {
            actualitzarStats(team1, team2, result);
        } catch (PresentationException e) {
            System.out.println("The stats can't be updated!");
        }
        System.out.println("\n<Press any key to continue...>");
        scanner.nextLine();
        System.out.println();
    }

    private void mostrarTeamMembers(Team team) throws BusinessException {
        for (Member member : team.getMembers()) {
            Character character = charactermanager.getCharacter(member.getId());
            member.setCharacter(character);  // Asignar personaje al miembro

            // Asignar arma y armadura aleatoria
            member.setArma(itemmanager.obtenirArmaRandom());
            member.setArmadura(itemmanager.obtenirArmaduraRandom());

            // Mostrar los detalles del miembro con su arma y armadura
            System.out.println("- " + member.getCharacter().getName());
            System.out.println("\tWeapon: " + (member.getArma() != null ? member.getArma().getName() : "None"));
            System.out.println("\tArmor: " + (member.getArmadura() != null ? member.getArmadura().getName() : "None"));
        }
    }

    private void actualitzarStats(Team team1, Team team2, int result) throws PresentationException {

        int ko1 = 0;
        int ko2 = 0;

        for (Member member1 : team1.getMembers()) {
            if (member1.isKO()) {
                ko1++;
            }
        }
        for (Member member : team2.getMembers()) {
            if (member.isKO()) {
                ko2++;
            }
        }
        try {
            if (result == 1) {
                statsmanager.actualitzarFinalJoc(team1, team2, 1, 0, ko1, ko2);
            } else if (result == 2) {
                statsmanager.actualitzarFinalJoc(team1, team2, 0, 1, ko1, ko2);
            } else if (result == 3) {
                statsmanager.actualitzarFinalJoc(team1, team2, 0, 0, ko1, ko2);
            }
        } catch (BusinessException e) {
            throw new PresentationException(e.getMessage());
        }

    }

    private void roundStats(Team team) {
        for (Member member : team.getMembers()) {
            if (member.getLastAttack() != null) {
                Items a = member.getArma();
                if (a == null) {
                    a = new Items("");
                }
                String attackDetails = member.getCharacter().getName() + " ATTACKS " + member.getLastAttack().getLastObjective() +
                        " WITH " + a.getName() + " FOR " + String.format("%.2f", member.getLastAttack().getLastAttack()) + " DAMAGE!";
                String recievedDetails = "\t" + member.getLastAttack().getLastObjective() + " RECEIVES " + String.format("%.2f", member.getLastAttack().getLastDamage()) + " DAMAGE.";
                view.printAttackDetails(attackDetails, recievedDetails);  // Imprime los detalles de los ataques
            }
        }
    }

    private void printBrokenItems(Team team) {
        ArrayList<String> brokenItems = new ArrayList<>();
        for (Member teamMember : team.getMembers()) {
            if (teamMember.getLastAttack() != null) {
                if (teamMember.getLastAttack().isWeaponBroke()) {
                    brokenItems.add("Oh no! " + teamMember.getCharacter().getName() + "'s " + teamMember.getNameArma() + " breaks! (Weapon)");
                }
                if (teamMember.getLastAttack().isArmorBroke()) {
                    brokenItems.add("Oh no! " + teamMember.getCharacter().getName() + "'s " + teamMember.getNameArmadura() + " breaks! (Armor)");
                }
            }
        }
        view.mostrarItems(brokenItems);
    }

    private void printKo(Team team1, Team team2) {
        ArrayList<String> mostrarKO = new ArrayList<>();
        for (Member team1Member : team1.getMembers()) {
            if (team1Member.getLastAttack() != null && team1Member.isKO()) {
                mostrarKO.add(team1Member.getCharacter().getName());
            }
        }
        for (Member team2Member : team2.getMembers()) {
            if (team2Member.getLastAttack() != null && team2Member.isKO()) {
                mostrarKO.add(team2Member.getCharacter().getName());
            }
        }
        view.mostrarKO(mostrarKO);
    }


    private void printTeamInfo(Team team) throws PresentationException {
        ArrayList<String> teamInfo = new ArrayList<>();
        for (Member member : team.getMembers()) {
            Character character;
            try {
                character = charactermanager.getCharacter(member.getId());
            } catch (BusinessException e) {
                throw new PresentationException(e.getMessage());
            }
            Items weapon = member.getArma() != null ? member.getArma() : new Items("None");
            Items armor = member.getArmadura() != null ? member.getArmadura() : new Items("None");
            if (member.isKO()) {
                teamInfo.add("\t- " + character.getName() + " (KO)");
            } else {
                teamInfo.add("\t- " + character.getName() + " (" + String.format("%.2f", member.getMalRebut() * 100) + " %) "
                        + weapon.getName() + " - " + armor.getName());
            }
        }
        view.mostrarTeam(teamInfo);
    }

    private void printFinalInfo(Team team) throws PresentationException {
        ArrayList<String> finalInfo = new ArrayList<>();
        for (Member member : team.getMembers()) {
            Character character;
            try {
                character = charactermanager.getCharacter(member.getId());
            } catch (BusinessException e) {
                throw new PresentationException(e.getMessage());
            }
            if (member.isKO()) {
                finalInfo.add("\t- " + character.getName() + " (KO)");
            } else {
                finalInfo.add("\t- " + character.getName() + " (" + String.format("%.2f", member.getMalRebut() * 100) + " %) ");
            }
        }
        view.mostrarFinal(finalInfo);
    }

    public void verificarFiles() throws PresentationException {
        try {
            charactermanager.verify();
            itemmanager.verify();
            statsmanager.verify();
            teammanager.verify();
        } catch (BusinessException e) {
            throw new PresentationException(e.getMessage());
        }
    }
}