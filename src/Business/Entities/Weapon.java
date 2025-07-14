package Business.Entities;

import Business.Entities.Item;
import Business.Entities.Member;

public class Weapon extends Item {
    public Weapon(String name, int power, int durability) {
        super(name, power, durability);
    }

    public float getAttackDmg(Member member) {
        float part1 = (member.getCharacter().getWeight() * (1 - member.getMalRebut()) / 10);
        float part2 = member.getArma().getPower() / 20.0f;
        return Math.max(part1 + part2 + 18, 0);
    }
} 