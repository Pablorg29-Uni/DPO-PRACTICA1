package Business.Entities;

public class LastAttack {
    private float lastAttack;
    private float lastDamage;
    private String lastObjective;
    private boolean weaponBroke;
    private boolean armorBroke;
    private boolean gotKO;

    public LastAttack(float lastAttack, float lastDamage, String lastObjective, boolean weaponBroke, boolean armorBroke, boolean gotKO) {
        this.lastAttack = lastAttack;
        this.lastDamage = lastDamage;
        this.lastObjective = lastObjective;
        this.weaponBroke = weaponBroke;
        this.armorBroke = armorBroke;
        this.gotKO = gotKO;
    }

    public float getLastAttack() {
        return lastAttack;
    }

    public void setLastAttack(float lastAttack) {
        this.lastAttack = lastAttack;
    }

    public float getLastDamage() {
        return lastDamage;
    }

    public void setLastDamage(float lastDamage) {
        this.lastDamage = lastDamage;
    }

    public String getLastObjective() {
        return lastObjective;
    }

    public void setLastObjective(String lastObjective) {
        this.lastObjective = lastObjective;
    }

    public boolean isWeaponBroke() {
        return weaponBroke;
    }

    public void setWeaponBroke(boolean weaponBroke) {
        this.weaponBroke = weaponBroke;
    }

    public boolean isGotKO() {
        return gotKO;
    }

    public void setGotKO(boolean gotKO) {
        this.gotKO = gotKO;
    }
}
