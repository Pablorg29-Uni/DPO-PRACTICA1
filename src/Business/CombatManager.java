package Business;

import Business.Entities.LastAttack;
import Business.Entities.Member;
import Business.Entities.Team;

import java.util.ArrayList;
import java.util.Random;

public class CombatManager {
    private Team team1;
    private Team team2;
    private final ItemsManager itemsManager;
    private final CharacterManager characterManager;


    public CombatManager() {
        this.itemsManager = new ItemsManager();
        this.characterManager = new CharacterManager();
    }

    public Team[] initCombat(Team t1, Team t2) {
        this.team1 = t1;
        this.team2 = t2;
        for (Member member : t1.getMembers()) {
            member.setArma(itemsManager.obtenirArmaRandom());
            member.setArmadura(itemsManager.obtenirArmaduraRandom());
            member.setMalRebut(0);
            member.setKO(false);
        }
        for (Member member : t2.getMembers()) {
            member.setArma(itemsManager.obtenirArmaRandom());
            member.setArmadura(itemsManager.obtenirArmaduraRandom());
            member.setMalRebut(0);
            member.setKO(false);
        }
        omplirTeams(t1);
        omplirTeams(t2);
        return new Team[]{team1, team2};
    }

    public void omplirTeams(Team t) {
        for (Member member : t.getMembers()) {
            member.setCharacter(characterManager.getCharacter(member.getId()));
        }
    }

    public float calcularAttack(Member member) {
        float part1;
        float part2 = 0;

        part1 = (member.getCharacter().getWeight() * (1 - member.getMalRebut()) / 10);

        if (member.getArma() != null) {
            part2 = (float) ((float) member.getArma().getPower() / 20.0);
        } else {
            return 0;
        }
        float f = part1 + part2 + 18;
        if (f > 0) {
            return f;
        } else {
            return 0;
        }
    }

    public float calcularFinalDamage(Member member, float attack) {
        float part1;
        float part2 = 0;
        float finalDmg;

        part1 = (200 * (1 - member.getMalRebut())) / (float) member.getCharacter().getWeight();
        if (member.getArmadura() != null) {
            part2 = (float) (member.getArmadura().getPower() / 20.0);
        }

        finalDmg = (float) (attack - ((part1 + part2) * 1.4));
        return finalDmg / 100;
    }

    public int comprovarEstatCombat() {
        //0 --> Encara no ha acabat
        //1 --> Ha guanyat el equip 1
        //2 --> Ha guanyat el equip 2
        //3 --> Han empatat
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
        if (!team1KO && !team2KO) {
            return 0;
        } else if (!team1KO && team2KO) {
            return 1;
        } else if (team1KO && !team2KO) {
            return 2;
        } else if (team2KO && team1KO) {
            return 3;
        } else {
            return -1;
        }
    }

    public void executarCombat() {
        ArrayList<Boolean> team2isKo = new ArrayList<>();
        for (Member member : team2.getMembers()) {
            if (!member.isKO()) {
                team2isKo.add(false);
            } else {
                team2isKo.add(true);
            }
        } //Para poder realizar el turno del equipo 2 sin que afecte si estan KO o no

        for (Member member : team1.getMembers()) {
            if (!member.isKO()) {
                realitzarAtack(member, team2);
            } else {
                member.setLastAttack(null);
            }
        }
        int i = 0;
        for (Member member : team2.getMembers()) {
            if (!team2isKo.get(i)) {
                realitzarAtack(member, team1);
            } else {
                member.setLastAttack(null);
            }
            i++;
        }
    }


    public void atacarBalanced(Member member, Team teamDefensor) {
        //System.out.println(member.getCharacter().getName()+ " is attacking...");
        Random rand = new Random();
        int r;
        do {
            r = rand.nextInt(teamDefensor.getMembers().size());
        } while (teamDefensor.getMembers().get(r).isKO());
        float attack = calcularAttack(member);
        float damageReduction = teamDefensor.getMembers().get(r).getDamageReduction();
        //Cambiar esto de manera que el final damage no te pueda sumar vida
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
            teamDefensor.getMembers().get(r).getArmadura().setDurability(teamDefensor.getMembers().get(r).getArmadura().getDurability()-1);
            if (teamDefensor.getMembers().get(r).getArmadura().getDurability() == 0) {
                armadura = true;
            }
        }
        teamDefensor.getMembers().get(r).setMalRebut(teamDefensor.getMembers().get(r).getMalRebut() + finalDamage);
        String nomObjectiu = teamDefensor.getMembers().get(r).getCharacter().getName();

        if (member.getArma() != null) {
            member.getArma().setDurability(member.getArma().getDurability() - 1);
            if (member.getArma().getDurability() == 0) {
                member.setArma(null);
                arma = true;
            }
        }

        //CALCULAR KO
        int k = rand.nextInt(200) + 1;
        float ko = (float) (k / 100.0);
        if (ko > teamDefensor.getMembers().get(r).getMalRebut()) {
            teamDefensor.getMembers().get(r).setKO(true);
            //System.out.println(teamDefensor.getMembers().get(r).getCharacter().getName()+ " is KOOOOOOOOOOOOOOOOOOOOOOOOO!");
        }
        member.setLastAttack(new LastAttack(attack, finalDamage, nomObjectiu, arma, armadura));
    }

    public void defensarSeguentAtac(Member member) {
        member.setLastAttack(null);
        member.setDamageReduction((float) member.getCharacter().getWeight() / 400);
    }

    public void realitzarAtack(Member member, Team teamDefensor) {
        if (member.getStrategy().equals("balanced")) {
            if (member.getArma() == null) {
                member.setLastAttack(null);
                member.setArma(itemsManager.obtenirArmaRandom()); //Demanar arma
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
