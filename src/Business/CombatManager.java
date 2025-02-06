package Business;

import Business.Entities.Member;
import Business.Entities.Team;

import java.util.Random;

public class CombatManager {
    private Team team1;
    private Team team2;
    private ItemsManager itemsManager;
    private CharacterManager characterManager;


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

        part1 = member.getCharacter().getWeight() * (1 - member.getMalRebut());

        if (member.getArma() != null) {
            part2 = (float) (member.getArma().getPower() / 20.0);
        }
        return part1 + part2 + 18;
    }

    public float calcularFinalDamage(Member member, float attack) {
        float part1;
        float part2 = 0;
        float finalDmg;

        part1 = (float) (200 * (1 - member.getMalRebut())) / (float) member.getCharacter().getWeight();
        if (member.getArmadura() != null) {
            part2 = (float) (member.getArmadura().getPower() / 20.0);
        }

        finalDmg = (float) (attack - ((part1 + part2) * 1.4));
        return finalDmg / 100;
    }

    public void executarCombat() {
        for (Member member : team1.getMembers()) {
            realitzarAtack(member);
        }
    }

    //SOLO SIRVE CUANDO ATACA TEAM1 (CAMBIAR)
    public void atacarBalanced(Member member) {
        Random rand = new Random();
        int r = rand.nextInt(team2.getMembers().size());
        float attack = calcularAttack(member);
        float finalDamage = calcularFinalDamage(team2.getMembers().get(r), attack);
        team2.getMembers().get(r).setMalRebut(team2.getMembers().get(r).getMalRebut() + finalDamage);
        member.getArma().setDurability(member.getArma().getDurability() - 1);
        team2.getMembers().get(r).getArmadura().setDurability(team2.getMembers().get(r).getArmadura().getDurability() - 1);
        if (member.getArma().getDurability() == 0) {
            member.setArma(null);
        }
        if (team2.getMembers().get(r).getArma().getDurability() == 0) {
            team2.getMembers().get(r).setArma(null);
        }
        //CALCULAR KO
        int ko = rand.nextInt(1200) / 100;
        if (ko>team2.getMembers().get(r).getMalRebut()) {
            team2.getMembers().get(r).setKO(true);
        }
    }

    public boolean realitzarAtack(Member member) {
        if (member.getStrategy().equals("balanced")) {
            if (member.getArma() != null) {
                member.setArma(itemsManager.obtenirArmaRandom()); //Demanar arma
            } else {
                if (member.getArmadura() != null) {
                    if (member.getMalRebut() >= 0.5 && member.getMalRebut() <= 1.0) {
                        //Defensar
                    } else {
                        //Atacar
                    }
                } else {
                    atacarBalanced(member);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }
}
