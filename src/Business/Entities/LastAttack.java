package Business.Entities;

public class LastAttack {
    private float lastAttack;
    private float lastDamage;
    private String lastObjective;
    private boolean weaponBroke;
    private boolean armorBroke;

    /**
     * Constructor de la clase LastAttack.
     *
     * @param lastAttack Tiempo del último ataque.
     * @param lastDamage Daño causado en el último ataque.
     * @param lastObjective Objetivo del último ataque.
     * @param weaponBroke Indica si el arma se rompió.
     * @param armorBroke Indica si la armadura se rompió.
     */
    public LastAttack(float lastAttack, float lastDamage, String lastObjective, boolean weaponBroke, boolean armorBroke) {
        this.lastAttack = lastAttack;
        this.lastDamage = lastDamage;
        this.lastObjective = lastObjective;
        this.weaponBroke = weaponBroke;
        this.armorBroke = armorBroke;
    }

    /**
     * Obtiene el tiempo del último ataque.
     *
     * @return Tiempo del último ataque.
     */
    public float getLastAttack() {
        return lastAttack;
    }

    /**
     * Obtiene el daño del último ataque.
     *
     * @return Daño del último ataque.
     */
    public float getLastDamage() {
        return lastDamage;
    }

    /**
     * Obtiene el objetivo del último ataque.
     *
     * @return Objetivo del último ataque.
     */
    public String getLastObjective() {
        return lastObjective;
    }

    /**
     * Indica si el arma se rompió en el último ataque.
     *
     * @return true si el arma se rompió, false en caso contrario.
     */
    public boolean isWeaponBroke() {
        return weaponBroke;
    }

    /**
     * Indica si la armadura se rompió en el último ataque.
     *
     * @return true si la armadura se rompió, false en caso contrario.
     */
    public boolean isArmorBroke() {
        return armorBroke;
    }
}
