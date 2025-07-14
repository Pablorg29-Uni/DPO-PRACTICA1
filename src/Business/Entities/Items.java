package Business.Entities;

import com.google.gson.annotations.SerializedName;

/**
 * Modelo que representa un ítem del juego con sus propiedades básicas.
 */
public class Items {
    private Weapon weapon;
    private Armor armor;
    private String weaponName;
    private String armorName;

    public Items(Weapon weapon, Armor armor) {
        this.weapon = weapon;
        this.armor = armor;
        this.weaponName = weapon.getName();
        this.armorName = armor.getName();
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
        if (weapon !=null) {
            this.weaponName = weapon.getName();
        } else {
            this.weaponName = "No weapon";
        }
    }

    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
        if (armor != null) {
            this.armorName = armor.getName();
        } else {
            this.armorName = "No armor";
        }
    }

    public String getWeaponName() {
        return weaponName;
    }

    public String getArmorName() {
        return armorName;
    }
}