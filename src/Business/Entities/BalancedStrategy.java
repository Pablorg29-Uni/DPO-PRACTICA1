package Business.Entities;

import Business.CombatManager;
import Exceptions.BusinessException;

public class BalancedStrategy implements AttackStrategy {
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
            if (member.getArmadura() != null) {
                if (member.getMalRebut() >= 0.5 && member.getMalRebut() <= 1.0) {
                    manager.defensarSeguentAtac(member);
                } else {
                    manager.atacar(member, teamDefensor, -1);
                }
            } else {
                manager.atacar(member, teamDefensor, -1);
            }
        }
    }
} 