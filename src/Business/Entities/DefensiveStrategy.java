package Business.Entities;

import Business.CombatManager;
import Exceptions.BusinessException;

public class DefensiveStrategy implements AttackStrategy {
    @Override
    public void executeAttack(Member member, Team teamDefensor, int moreHPmember, CombatManager manager) throws BusinessException {
        if (member.getArmadura() != null) {
            if (member.getMalRebut() <= 1.0) {
                manager.defensarSeguentAtac(member);
            } else {
                manager.atacar(member, teamDefensor, -1);
            }
        } else {
            manager.atacar(member, teamDefensor, -1);
        }
    }
} 