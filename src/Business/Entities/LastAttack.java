package Business.Entities;

public class LastAttack {
    private float lastAttack;
    private float lastDamage;
    private String lastObjective;
    private boolean weaponBroke;
    private boolean armorBroke;

    public LastAttack(float lastAttack, float lastDamage, String lastObjective, boolean weaponBroke, boolean armorBroke) {
        this.lastAttack = lastAttack;
        this.lastDamage = lastDamage;
        this.lastObjective = lastObjective;
        this.weaponBroke = weaponBroke;
        this.armorBroke = armorBroke;
    }

    public float getLastAttack() {
        return lastAttack;
    }

    public float getLastDamage() {
        return lastDamage;
    }

    public String getLastObjective() {
        return lastObjective;
    }

    public boolean isWeaponBroke() {
        return weaponBroke;
    }

    public boolean isArmorBroke() {
        return armorBroke;
    }

}
