package Business.Entities;

import Business.CombatManager;
import Exceptions.BusinessException;

public class SniperStrategy implements AttackStrategy {
    @Override
    public void executeAttack(Member member, Team teamDefensor, int moreHPmember, CombatManager manager) throws BusinessException {
        manager.atacar(member, teamDefensor, moreHPmember);
    }
} 