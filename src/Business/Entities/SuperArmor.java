package Business.Entities;

import Business.Entities.Armor;
import Business.Entities.Member;

public class SuperArmor extends Armor {
    public SuperArmor(String name, int power, int durability) {
        super(name, power, durability);
    }

    @Override
    public float getFinalDmg(Member member, float attack) {
        float part1 = (200 * (1 - member.getMalRebut())) / member.getCharacter().getWeight();
        float part2 = member.getArmadura().getPower()*member.getCharacter().getWeight() / 20.0f;
        float finalDmg = attack - ((part1 + part2) * 1.4f);
        return (finalDmg / 100);
    }
} 