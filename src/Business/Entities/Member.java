package Business.Entities;

import Business.CharacterManager;

public class Member {
    private long id;
    private Character character;
    private Items armadura;
    private Items arma;
    private String strategy;
    private float malRebut;
    private String role;
    private boolean isKO;
    private float damageReduction;
    private LastAttack lastAttack;
    private String nameArma;
    public String nameArmadura;

    public Member() {
        this.character = null;
        this.armadura = null;
        this.arma = null;
        this.strategy = null;
        this.role = null;
        this.lastAttack = null;
    }

    public Member(long id) {
        this();
        this.id = id;
    }

    /*public Member(long id, String strategy) {
        CharacterManager characterManager = new CharacterManager();
        this.character = characterManager.getCharacter(id);
        this.strategy = strategy;
    }*/

    public float getMalRebut() {
        return malRebut;
    }

    public void setMalRebut(float malRebut) {
        this.malRebut = malRebut;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Items getArmadura() {
        return armadura;
    }

    public void setArmadura(Items armadura) {
        if (armadura != null) {
            this.nameArmadura = armadura.getName();
        }
        this.armadura = armadura;
    }

    public Items getArma() {
        return arma;
    }

    public void setArma(Items arma) {
        if (arma != null) {
            this.nameArma = arma.getName();
        }
        this.arma = arma;
    }

    public String getStrategy() {
        return strategy;
    }

    public String getRole() {
        return role;
    }

    public boolean isKO() {
        return isKO;
    }

    public void setKO(boolean KO) {
        isKO = KO;
    }

    public float getDamageReduction() {
        return damageReduction;
    }

    public void setDamageReduction(float damageReduction) {
        this.damageReduction = damageReduction;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LastAttack getLastAttack() {
        return lastAttack;
    }

    public void setLastAttack(LastAttack lastAttack) {
        this.lastAttack = lastAttack;
    }

    public String getNameArma() {
        return nameArma;
    }

    public String getNameArmadura() {
        return nameArmadura;
    }
}
