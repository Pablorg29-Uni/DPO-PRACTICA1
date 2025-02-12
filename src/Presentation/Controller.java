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


    public void mostrarNombresDePersonajes() {
        Scanner scanner = new Scanner(System.in);
        int posicion = 1;
        System.out.println();
        List<Character> characters = charactermanager.getCharacters();
        //for (Character character : characters) {
        //  System.out.println("\t" + posicion + ") " + character.getName());
        //posicion++;
        //}
        view.NombrePJ(characters, posicion);
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

            //System.out.println("\nID: " + selectedCharacter.getId());
            //System.out.println("NAME: " + selectedCharacter.getName());
            //System.out.println("WEIGHT: " + selectedCharacter.getWeight() + " kg");
            // Obtener los equipos a los que pertenece este personaje
            //List<Team> teams = teammanager.showTeams();
            //System.out.println("TEAMS:");
            ArrayList<Team> teams = teammanager.teamsWithPlayer(selectedCharacter.getId());
            //for (Team team : teams) {
            //  System.out.println("\t- " + team.getName());
            //}
            view.InfoPJ(selectedCharacter, teams);

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
        //System.out.println("\nAvailable Teams:");
        //for (Team team : teams) {
        //   System.out.println("\t" + posicion + ") " + team.getName());
        //    posicion++;
        //}
        view.equipos(teams, posicion);
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
            ArrayList<Member> members = teammanager.getTeam(selectedTeam.getName()).getMembers();
            //ArrayList<Member> members = selectedTeam.getMembers();
            for (int i = 0; i < members.size(); i++) {
                Character character = charactermanager.getCharacter(members.get(i).getId());

                //if (character != null) {
                // Establecer el rol (por defecto "Balanced")
                //  String role = members.get(i).getRole() != null ? members.get(i).getRole() : "Balanced";

                // Alinear la salida con el nombre y el rol
                //System.out.printf("Character #%d: %-30s (%s)%n", (i + 1), character.getName(), role);
                //} else {
                //   System.out.println("Character #" + (i + 1) + ": Unknown Character (ID: " + members.get(i).getCharacter().getId() + ")");
                //}
                view.equipo(members, character, i);
            }

            // Mostrar estadísticas del equipo
            Stats teamStats = statsmanager.getStat(selectedTeam.getName());
            //if (teamStats != null) {
            //System.out.println("\nTeam Statistics:");
            //System.out.println("\nCombats played: " + teamStats.getGames_played());
            //System.out.println("Combats won: " + teamStats.getGames_won());

            // Calcular y mostrar la tasa de victorias
            //double winRate = teamStats.getGames_played() > 0
            //      ? ((double) teamStats.getGames_won() / teamStats.getGames_played()) * 100
            //     : 0.0;
            //System.out.printf("Win rate: %.0f%%%n", winRate);

            //System.out.println("KOs done: " + teamStats.getKO_done());
            //System.out.println("KOs received: " + teamStats.getKO_received());
            //} else {
            //  System.out.println("\nNo statistics available for this team.");
            //}
            view.statsequipo(teamStats);
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
        System.out.println();
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
                //System.out.println("");
                //System.out.println("\tID: " + selectedItem.getId());
                //System.out.println("\tNAME: " + selectedItem.getName());
                //System.out.println("\tCLASS: " + selectedItem.getClasse());
                //System.out.println("\tPOWER: " + selectedItem.getPower());
                //System.out.println("\tDURABILITY: " + selectedItem.getDurability());
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
            opcion = scanner.nextInt();  // Leer la opción seleccionada
        }

        // Regresar al menú anterior si la opción es 0
        System.out.println("Returning to the previous menu...");
    }

    public void simulateCombat() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nStarting simulator...\nLooking for available teams...\n");

        // Obtener lista de equipos
        List<Team> teams = teammanager.showTeams();
        int posicion = 1;

        // Mostrar equipos disponibles
        view.equipos(teams, posicion);

        // Seleccionar el primer equipo
        System.out.println("\nChoose team #1: ");
        int opcion1 = scanner.nextInt();
        Team selectedTeam1 = teams.get(opcion1 - 1);

        // Seleccionar el segundo equipo
        System.out.println("\nChoose team #2: ");
        int opcion2 = scanner.nextInt();
        Team selectedTeam2 = teams.get(opcion2 - 1);
        System.out.println("\nInitializing teams...\n");

        // Mostrar los detalles de los equipos seleccionados
        System.out.println("Team #1 – " + selectedTeam1.getName());
        for (Member member : selectedTeam1.getMembers()) {
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

        System.out.println("\nTeam #2 – " + selectedTeam2.getName());
        for (Member member : selectedTeam2.getMembers()) {
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

        // Mensaje final indicando que los equipos están listos
        System.out.println("\nCombat ready!");
        System.out.println("\n<Press any key to continue...>");
        scanner.nextLine(); // Esperar entrada del usuario

        // Inicializar combate con los equipos seleccionados
        Team[] combatTeams = combatManager.initCombat(selectedTeam1, selectedTeam2);
        Team team1 = combatTeams[0];
        Team team2 = combatTeams[1];

        // Iniciar las rondas de combate
        int round = 1;
        boolean combatFinished = false;

        while (!combatFinished) {
            System.out.println("\n--- ROUND " + round + " ---");
            System.out.println("Team #1 – " + team1.getName());
            printTeamInfo(team1);
            System.out.println("\nTeam #2 – " + team2.getName());
            printTeamInfo(team2);
            System.out.println();

            // Realizar ataques y mostrar la información del ataque
            combatManager.executarCombat();  // Ejecuta los ataques de ambos equipos

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
            int result = combatManager.comprovarEstatCombat();
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
        System.out.println("\n<Press any key to continue...>");
        scanner.nextLine();
        System.out.println();
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


    private void printTeamInfo(Team team) {
        ArrayList<String> teamInfo = new ArrayList<>();
        for (Member member : team.getMembers()) {
            Character character = charactermanager.getCharacter(member.getId());
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

    private void printFinalInfo(Team team) {
        ArrayList<String> finalInfo = new ArrayList<>();
        for (Member member : team.getMembers()) {
            Character character = charactermanager.getCharacter(member.getId());
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