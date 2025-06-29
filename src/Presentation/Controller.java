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
import Persistence.API.ConnectorAPIHelper;
import edu.salle.url.api.exception.ApiException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Controlador principal del programa.
 * Se encarga de coordinar la lógica del programa y la interacción con los datos.
 */
public class Controller {
    private final CombatManager combatManager;
    private final View view;
    private final TeamManager teammanager;
    private final StatsManager statsmanager;
    private final ItemsManager itemmanager;
    private final CharacterManager charactermanager;

    /**
     * Constructor que inicializa los gestores de negocio y la vista.
     */
    public Controller() {
        this.statsmanager = new StatsManager();
        this.itemmanager = new ItemsManager();
        this.charactermanager = new CharacterManager();
        this.teammanager = new TeamManager();
        this.combatManager = new CombatManager(itemmanager, charactermanager);
        this.view = new View();
    }

    /**
     * Muestra los nombres de los personajes disponibles y permite seleccionar uno
     * para ver su información y los equipos en los que participa.
     *
     * @throws PresentationException si ocurre un error al obtener los personajes.
     */
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
            opcion = characters.size() + 1;
            scanner.nextLine();
        }

        if (opcion == 0) {
            System.out.println("Returning to the previous menu...");
            return;
        }

        if (opcion > 0 && opcion <= characters.size()) {
            try {
                Character selectedCharacter = characters.get(opcion - 1);
                ArrayList<Team> teams = teammanager.teamsWithPlayer(selectedCharacter.getId());
                view.InfoPJ(selectedCharacter, teams);
            } catch (BusinessException e) {
                throw new PresentationException(e.getMessage());
            }

            System.out.println("\n<Press any key to continue...>");
            scanner.nextLine();
            scanner.nextLine();
        } else {
            throw new PresentationException("");
        }
    }

    /**
     * Permite crear un equipo solicitando al usuario el nombre y los personajes
     * que lo conformarán.
     *
     * @throws PresentationException si ocurre un error durante la creación del equipo.
     */
    public void crearEquipo() throws PresentationException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the team's name: ");
        String teamName = scanner.nextLine();

        long[] ids = new long[4];
        String[] strats = new String[4];
        for (int i = 0; i < 4; i++) {
            System.out.print("\nPlease enter name or id for character #" + (i + 1) + ": ");
            String input = scanner.nextLine();
            try {
                long id = Long.parseLong(input);
                charactermanager.getCharacter(id);
                ids[i] = id;
            } catch (NumberFormatException | BusinessException e) {
                try {
                    Character character = charactermanager.getCharacter2(input);
                    ids[i] = character.getId();
                } catch (BusinessException ex) {
                    throw new PresentationException(ex.getMessage());
                }
            }
            System.out.print("|Please enter the strategy for character #" + (i + 1) + " (balanced | offensive | defensive | sniper): ");
            strats[i] = scanner.nextLine();
        }
        try {
            teammanager.createTeam(teamName, ids[0], ids[1], ids[2], ids[3], strats);
            System.out.println("\nTeam " + teamName + " has been successfully created!");
        } catch (BusinessException e) {
            throw new PresentationException(e.getMessage());
        }
    }

    /**
     * Muestra la lista de equipos disponibles y permite al usuario seleccionar uno
     * para ver sus detalles, incluyendo sus miembros y estadísticas.
     *
     * @throws PresentationException Si ocurre un error al obtener los equipos, miembros o estadísticas.
     */
    public void mostrarEquipos() throws PresentationException {
        Scanner scanner = new Scanner(System.in);

        List<Team> teams;
        try {
            teams = teammanager.showTeams();
        } catch (BusinessException e) {
            throw new PresentationException(e.getMessage());
        }

        if (teams.isEmpty()) {
            System.out.println("\nNo teams available.");
            return;
        }

        int posicion = 1;
        view.equipos(teams, posicion);
        System.out.println("\n\t0) Back\n\nChoose an option: ");

        int opcion;
        try {
            opcion = scanner.nextInt();
        } catch (InputMismatchException e) {
            opcion = teams.size() + 1;
            scanner.nextLine();
        }

        if (opcion == 0) {
            System.out.println("Returning to the previous menu...");
            return;
        }

        if (opcion > 0 && opcion <= teams.size()) {
            Team selectedTeam = teams.get(opcion - 1);

            System.out.println("\nTeam name: " + selectedTeam.getName() + "\n");

            try {
                ArrayList<Member> members = teammanager.getTeam(selectedTeam.getName()).getMembers();
                for (int i = 0; i < members.size(); i++) {
                    Character character = charactermanager.getCharacter(members.get(i).getId());
                    view.equipo(members, character, i);
                }
                Stats teamStats = statsmanager.getStat(selectedTeam.getName());
                view.statsequipo(teamStats);
            } catch (BusinessException e) {
                throw new PresentationException(e.getMessage());
            }
            System.out.println("\n<Press any key to continue...>");
            scanner.nextLine();
            scanner.nextLine();
        } else {
            System.out.println("\nInvalid option. Returning to the previous menu...");
        }
    }

    /**
     * Permite eliminar un equipo por su nombre. Solicita confirmación antes de proceder con la eliminación.
     *
     * @throws PresentationException Si ocurre un error al obtener o eliminar el equipo.
     */
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
                System.out.println("\nTeam \"" + teamName + "\" has been successfully removed.");
            } catch (BusinessException e) {
                throw new PresentationException(e.getMessage());
            }
        } else {
            System.out.println("\nTeam deletion cancelled.");
        }
    }

    /**
     * Muestra la lista de ítems disponibles y permite al usuario seleccionar uno para ver sus detalles.
     * Si no hay ítems disponibles, informa al usuario.
     *
     * @throws PresentationException Si ocurre un error al obtener la lista de ítems.
     */
    public void mostrarItems() throws PresentationException {
        Scanner scanner = new Scanner(System.in);

        List<Items> items;
        try {
            items = itemmanager.showItems();
        } catch (BusinessException e) {
            throw new PresentationException(e.getMessage());
        }

        if (items.isEmpty()) {
            System.out.println("\nNo items available.");
            return;
        }

        int posicion = 1;
        System.out.println();
        for (Items item : items) {
            System.out.println("\t" + posicion + ") " + item.getName());
            posicion++;
        }
        System.out.println("\n\t0) Back\n\nChoose an option: ");

        int opcion;
        try {
            opcion = scanner.nextInt();
        } catch (InputMismatchException e) {
            opcion = items.size() + 1;
            scanner.nextLine();
        }

        while (opcion != 0) {
            if (opcion > 0 && opcion <= items.size()) {
                Items selectedItem = items.get(opcion - 1);
                view.itemdetalle(selectedItem);
                System.out.println("\n<Press any key to continue...>");
                scanner.nextLine();
                scanner.nextLine();
            } else {
                System.out.println("\nInvalid option. Please choose again.");
            }

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

        System.out.println("Returning to the previous menu...");
    }

    /**
     * Simula un combate entre dos equipos seleccionados por el usuario.
     * Se presentan los equipos disponibles, se eligen dos equipos, y luego se ejecutan rondas de combate
     * hasta que haya un ganador o se declare un empate.
     *
     * @throws PresentationException Si ocurre un error en la selección de equipos, la inicialización del combate
     *                               o la actualización de estadísticas.
     */
    public void simulateCombat() throws PresentationException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nStarting simulator...\nLooking for available teams...\n");

        List<Team> teams;
        int posicion = 1;
        try {
            teams = teammanager.showTeams();
        } catch (BusinessException e) {
            throw new PresentationException(e.getMessage());
        }

        view.equipos(teams, posicion);
        int opcion;

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

        if (selectedTeam1.equals(selectedTeam2)) {
            long id0 = selectedTeam2.getMembers().get(0).getId();
            long id1 = selectedTeam2.getMembers().get(1).getId();
            long id2 = selectedTeam2.getMembers().get(2).getId();
            long id3 = selectedTeam2.getMembers().get(3).getId();
            String s1 = selectedTeam1.getMembers().get(0).getStrategy();
            String s2 = selectedTeam2.getMembers().get(1).getStrategy();
            String s3 = selectedTeam2.getMembers().get(2).getStrategy();
            String s4 = selectedTeam2.getMembers().get(3).getStrategy();
            selectedTeam2 = new Team(selectedTeam2.getName(), id0, id1, id2, id3, s1, s2, s3, s4);
        }

        try {
            System.out.println("Team #1 – " + selectedTeam1.getName());
            mostrarTeamMembers(selectedTeam1);

            System.out.println("\nTeam #2 – " + selectedTeam2.getName());
            mostrarTeamMembers(selectedTeam2);
        } catch (BusinessException e) {
            throw new PresentationException(e.getMessage());
        }


        System.out.println("\nCombat ready!");
        System.out.println("\n<Press any key to continue...>");
        scanner.nextLine();

        Team team1;
        Team team2;

        try {
            Team[] combatTeams = combatManager.initCombat(selectedTeam1, selectedTeam2);
            team1 = combatTeams[0];
            team2 = combatTeams[1];
        } catch (BusinessException e) {
            throw new PresentationException("Error initializing the combat teams!");
        }


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


            try {
                combatManager.executarCombat();
            } catch (BusinessException e) {
                throw new PresentationException(e.getMessage());
            }


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
                    round++;
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

    /**
     * Muestra los miembros de un equipo, asignándoles un personaje, un arma y una armadura aleatoria.
     *
     * @param team El equipo cuyos miembros se van a mostrar.
     * @throws BusinessException Si ocurre un error al obtener los datos de los miembros.
     */
    private void mostrarTeamMembers(Team team) throws BusinessException {
        for (Member member : team.getMembers()) {
            Character character = charactermanager.getCharacter(member.getId());
            member.setCharacter(character);


            member.setArma(itemmanager.obtenirArmaRandom());
            member.setArmadura(itemmanager.obtenirArmaduraRandom());


            System.out.println("- " + member.getCharacter().getName());
            System.out.println("\tWeapon: " + (member.getArma() != null ? member.getArma().getName() : "None"));
            System.out.println("\tArmor: " + (member.getArmadura() != null ? member.getArmadura().getName() : "None"));
        }
    }

    /**
     * Actualiza las estadísticas del combate tras su finalización.
     *
     * @param team1  El primer equipo.
     * @param team2  El segundo equipo.
     * @param result El resultado del combate (1 = gana team1, 2 = gana team2, 3 = empate).
     * @throws PresentationException Si ocurre un error al actualizar las estadísticas.
     */
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

    /**
     * Muestra el resumen de los ataques realizados por un equipo en la ronda actual.
     *
     * @param team El equipo cuyos ataques se van a mostrar.
     */
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
                view.printAttackDetails(attackDetails, recievedDetails);
            }
        }
    }

    /**
     * Muestra los objetos rotos (armas y armaduras) de los miembros de un equipo tras la ronda actual.
     *
     * @param team El equipo cuyos objetos rotos se van a mostrar.
     */
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

    /**
     * Muestra los miembros que han quedado fuera de combate (KO) en ambos equipos.
     *
     * @param team1 El primer equipo.
     * @param team2 El segundo equipo.
     */
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

    /**
     * Muestra la información del equipo, incluyendo el estado de cada miembro,
     * el porcentaje de daño recibido, el arma y la armadura equipada.
     *
     * @param team El equipo cuya información se mostrará.
     * @throws PresentationException Si ocurre un error al obtener los datos de los personajes.
     */
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

    /**
     * Muestra la información final del equipo después del combate,
     * incluyendo el estado de cada miembro y el porcentaje de daño recibido.
     *
     * @param team El equipo cuya información final se mostrará.
     * @throws PresentationException Si ocurre un error al obtener los datos de los personajes.
     */
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

    /**
     * Verifica la integridad de los archivos o datos relacionados con personajes,
     * ítems, estadísticas y equipos, asegurando que todo esté en orden antes del combate.
     *
     * @throws PresentationException Si ocurre un error en la verificación de los datos.
     */
    public void verificarFiles() throws PresentationException {
        try {
            charactermanager.verify();
            itemmanager.verify();
            statsmanager.verify();
            teammanager.verify(statsmanager);
        } catch (BusinessException e) {
            throw new PresentationException(e.getMessage());
        }
    }

    /**
     * Inicializa y configura el helper para conectar con la API en los gestores relacionados.
     *
     * <p>Este método crea una instancia de {@code ConnectorAPIHelper} y la asigna
     * a los gestores de personajes, ítems, estadísticas y equipos para que puedan
     * interactuar con la API externa.</p>
     *
     * @throws ApiException Si ocurre un error al configurar la conexión con la API.
     */
    public void verificarAPI() throws ApiException {
        ConnectorAPIHelper apiHelper = new ConnectorAPIHelper();
        charactermanager.setApiHelper(apiHelper);
        itemmanager.setApiHelper(apiHelper);
        statsmanager.setApiHelper(apiHelper);
        teammanager.setApiHelper(apiHelper, statsmanager);
    }
}