package Business.Entities;

import Business.CombatManager;
import Exceptions.BusinessException;

public class OffensiveStrategy implements AttackStrategy {
    @Override
    public void executeAttack(Member member, Team teamDefensor, int moreHPmember, CombatManager manager) throws BusinessException {
        if (member.getArma() == null) {
            member.setLastAttack(null);
            try {
                manager.getItemsManager().obtenirArmaRandom();
            } catch (BusinessException e) {
                throw new BusinessException(e.getMessage());
            }
        } else {
            manager.atacar(member, teamDefensor, -1);
        }
    }
} 