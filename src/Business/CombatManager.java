package Business;

import Business.Entities.LastAttack;
import Business.Entities.Member;
import Business.Entities.Team;
import Exceptions.BusinessException;

import java.util.ArrayList;
import java.util.Random;


public class CombatManager {
    private Team team1;
    private Team team2;
    private final ItemsManager itemsManager;
    private final CharacterManager characterManager;

    /**
     * Constructor de CombatManager. Inicializa los gestores de ítems y personajes.
     */
    public CombatManager() {
        this.itemsManager = new ItemsManager();
        this.characterManager = new CharacterManager();
    }

    /**
     * Inicializa un combate entre dos equipos, asignando armas y armaduras aleatorias a sus miembros.
     *
     * @param t1 Primer equipo.
     * @param t2 Segundo equipo.
     * @return Array con los dos equipos inicializados.
     * @throws BusinessException Si ocurre un error al asignar ítems o verificar equipos.
     */
    public Team[] initCombat(Team t1, Team t2) throws BusinessException {
        this.team1 = t1;
        this.team2 = t2;
        for (Member member : t1.getMembers()) {
            try {
                member.setArma(itemsManager.obtenirArmaRandom());
                member.setArmadura(itemsManager.obtenirArmaduraRandom());
                member.setMalRebut(0);
                member.setKO(false);
            } catch (BusinessException e) {
                throw new BusinessException(e.getMessage());
            }
        }
        for (Member member : t2.getMembers()) {
            try {
                member.setArma(itemsManager.obtenirArmaRandom());
                member.setArmadura(itemsManager.obtenirArmaduraRandom());
                member.setMalRebut(0);
                member.setKO(false);
            } catch (BusinessException e) {
                throw new BusinessException(e.getMessage());
            }
        }
        try {
            omplirTeams(t1);
            omplirTeams(t2);
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage());
        }
        return new Team[]{team1, team2};
    }

    /**
     * Asigna un personaje a cada miembro de un equipo.
     *
     * @param t Equipo cuyos miembros recibirán un personaje.
     * @throws BusinessException Si ocurre un error al recuperar los personajes.
     */
    public void omplirTeams(Team t) throws BusinessException {
        try {
            for (Member member : t.getMembers()) {
                member.setCharacter(characterManager.getCharacter(member.getId()));
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Calcula el valor de ataque de un miembro en función de su peso, daño recibido y arma equipada.
     *
     * @param member Miembro que realiza el ataque.
     * @return Valor de ataque calculado.
     */
    public float calcularAttack(Member member) {
        float part1 = (member.getCharacter().getWeight() * (1 - member.getMalRebut()) / 10);
        float part2 = (member.getArma() != null) ? (member.getArma().getPower() / 20.0f) : 0;
        return Math.max(part1 + part2 + 18, 0);
    }

    /**
     * Calcula el daño final recibido por un miembro del equipo defensor tras aplicar la reducción de daño.
     *
     * @param member Miembro que recibe el daño.
     * @param attack Valor de ataque recibido.
     * @return Daño final calculado, ajustado por la armadura y el peso del personaje.
     */
    public float calcularFinalDamage(Member member, float attack) {
        float part1 = (200 * (1 - member.getMalRebut())) / member.getCharacter().getWeight();
        float part2 = (member.getArmadura() != null) ? (member.getArmadura().getPower() / 20.0f) : 0;
        float finalDmg = attack - ((part1 + part2) * 1.4f);
        return finalDmg / 100;
    }

    /**
     * Comprueba el estado actual del combate y determina si hay un equipo ganador.
     *
     * @return Código indicando el estado del combate:
     *         0 - El combate sigue en curso.
     *         1 - El equipo 1 ha ganado.
     *         2 - El equipo 2 ha ganado.
     *         3 - Empate (ambos equipos han sido eliminados).
     *        -1 - Estado inválido (no debería ocurrir).
     */
    public int comprovarEstatCombat() {
        boolean team1KO = true;
        boolean team2KO = true;

        for (Member member : team1.getMembers()) {
            if (!member.isKO()) {
                team1KO = false;
                break;
            }
        }

        for (Member member : team2.getMembers()) {
            if (!member.isKO()) {
                team2KO = false;
                break;
            }
        }

        if (!team1KO && !team2KO) return 0;
        if (!team1KO && team2KO) return 1;
        if (team1KO && !team2KO) return 2;
        if (team1KO && team2KO) return 3;

        return -1;
    }

    /**
     * Ejecuta un turno de combate, en el que cada miembro de ambos equipos realiza un ataque si no está KO.
     *
     * @throws BusinessException Si ocurre un error durante la ejecución del combate.
     */
    public void executarCombat() throws BusinessException {
        ArrayList<Boolean> team2isKo = new ArrayList<>();

        // Registra el estado KO de los miembros del equipo 2 antes de iniciar los ataques
        for (Member member : team2.getMembers()) {
            team2isKo.add(member.isKO());
        }

        // Turno de ataque del equipo 1 contra el equipo 2
        for (Member member : team1.getMembers()) {
            if (!member.isKO()) {
                try {
                    realitzarAtack(member, team2);
                } catch (BusinessException e) {
                    throw new BusinessException(e.getMessage());
                }
            } else {
                member.setLastAttack(null);
            }
        }

        // Turno de ataque del equipo 2 contra el equipo 1
        for (int i = 0; i < team2.getMembers().size(); i++) {
            Member member = team2.getMembers().get(i);
            if (!team2isKo.get(i)) {
                try {
                    realitzarAtack(member, team1);
                } catch (BusinessException e) {
                    throw new BusinessException(e.getMessage());
                }
            } else {
                member.setLastAttack(null);
            }
        }
    }
    /**
     * Realiza un ataque balanceado contra un miembro aleatorio del equipo defensor.
     * Se calcula el daño basado en el ataque del atacante y la reducción de daño del defensor.
     * Además, se reduce la durabilidad del arma y la armadura involucradas en el combate.
     *
     * @param member Atacante que ejecuta el ataque.
     * @param teamDefensor Equipo defensor que recibe el ataque.
     */
    public void atacarBalanced(Member member, Team teamDefensor) {
        Random rand = new Random();
        int r;
        boolean allKO = true;
        for (Member teamDefensorMember : teamDefensor.getMembers()) {
            if (!teamDefensorMember.isKO()) {
                allKO = false;
                break;
            }
        }
        if (allKO) {
            return;
        }
        do {
            r = rand.nextInt(teamDefensor.getMembers().size());
        } while (teamDefensor.getMembers().get(r).isKO());

        float attack = calcularAttack(member);
        float damageReduction = teamDefensor.getMembers().get(r).getDamageReduction();
        float calculatedDamage = calcularFinalDamage(teamDefensor.getMembers().get(r), attack);
        float finalDamage;
        boolean arma = false;
        boolean armadura = false;

        if (calculatedDamage > damageReduction) {
            finalDamage = calculatedDamage - damageReduction;
            teamDefensor.getMembers().get(r).setDamageReduction(0);
        } else {
            finalDamage = 0;
        }

        if (damageReduction != 0) {
            teamDefensor.getMembers().get(r).getArmadura().setDurability(
                    teamDefensor.getMembers().get(r).getArmadura().getDurability() - 1
            );
            if (teamDefensor.getMembers().get(r).getArmadura().getDurability() == 0) {
                armadura = true;
            }
        }

        teamDefensor.getMembers().get(r).setMalRebut(
                teamDefensor.getMembers().get(r).getMalRebut() + finalDamage
        );

        String nomObjectiu = teamDefensor.getMembers().get(r).getCharacter().getName();

        if (member.getArma() != null) {
            member.getArma().setDurability(member.getArma().getDurability() - 1);
            if (member.getArma().getDurability() == 0) {
                member.setArma(null);
                arma = true;
            }
        }

        // Determinar si el defensor queda KO
        int k = rand.nextInt(200) + 1; //(int) (Math.random()*200)+1;
        float ko = (float) (k / 100.0);
        /*
        Aqui com diu la practica seria ko < teamDefensor.getMembers().get(r).getMalRebut()
        Pero aixo no te sentit degut a que seguint aquest metode, els character tindrien mes posibilitats
        de ko quan menys mal tinguin rebut (canviar en cas incorrecte)
         */
        if (ko < teamDefensor.getMembers().get(r).getMalRebut()) {
            teamDefensor.getMembers().get(r).setKO(true);
        }

        member.setLastAttack(new LastAttack(attack, finalDamage, nomObjectiu, arma, armadura));
    }

    /**
     * Prepara al miembro para defender el siguiente ataque reduciendo el daño recibido.
     *
     * @param member Miembro que se prepara para la defensa.
     */
    public void defensarSeguentAtac(Member member) {
        member.setLastAttack(null);
        member.setDamageReduction((float) member.getCharacter().getWeight() / 400);
    }

    /**
     * Ejecuta un ataque según la estrategia del miembro. Actualmente solo soporta la estrategia "balanced".
     * Si el miembro no tiene arma, intentará obtener una nueva. Si tiene una armadura y ha recibido mucho daño,
     * se defenderá en lugar de atacar. De lo contrario, realizará un ataque balanceado.
     *
     * @param member Miembro que realiza el ataque.
     * @param teamDefensor Equipo que recibe el ataque.
     * @throws BusinessException Si hay un error al obtener un arma nueva.
     */
    public void realitzarAtack(Member member, Team teamDefensor) throws BusinessException {
        if (member.getStrategy().equals("balanced")) {
            if (member.getArma() == null) {
                member.setLastAttack(null);
                try {
                    member.setArma(itemsManager.obtenirArmaRandom()); // Obtener arma nueva
                } catch (BusinessException e) {
                    throw new BusinessException(e.getMessage());
                }
            } else {
                if (member.getArmadura() != null) {
                    if (member.getMalRebut() >= 0.5 && member.getMalRebut() <= 1.0) {
                        defensarSeguentAtac(member);
                    } else {
                        atacarBalanced(member, teamDefensor);
                    }
                } else {
                    atacarBalanced(member, teamDefensor);
                }
            }
        } else {
            System.out.println("Estratègia encara no implementada");
        }
    }
}
