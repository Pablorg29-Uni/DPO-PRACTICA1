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

    public Member() {
        this.character = null;
        this.armadura = null;
        this.arma = null;
        this.strategy = null;
        this.role = null;
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
        this.armadura = armadura;
    }

    public Items getArma() {
        return arma;
    }

    public void setArma(Items arma) {
        this.arma = arma;
    }

    public String getStrategy() {
        return strategy;
    }

    public String getRole() {
        return role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
