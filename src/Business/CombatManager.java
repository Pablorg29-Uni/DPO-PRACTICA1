package Business;

import Business.Entities.Member;
import Business.Entities.Team;

public class CombatManager {
    private Team team1;
    private Team team2;
    private boolean torn;
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
        }
        for (Member member : t2.getMembers()) {
            member.setArma(itemsManager.obtenirArmaRandom());
            member.setArmadura(itemsManager.obtenirArmaduraRandom());
            member.setMalRebut(0);
        }
        omplirTeams(t1);
        omplirTeams(t2);
        return new Team[]{team1, team2};
    }

    public void omplirTeams (Team t) {
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

        part1 = (float) (200 * (1-member.getMalRebut())) / (float) member.getCharacter().getWeight();
        if (member.getArmadura() != null) {
            part2 = (float) (member.getArmadura().getPower() / 20.0);
        }

        finalDmg = (float) (attack - ((part1 + part2)*1.4));
        return finalDmg / 100;
    }

}
