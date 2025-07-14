package Business.Entities;

import Business.CombatManager;
import Business.Entities.Team;
import Exceptions.BusinessException;

public interface AttackStrategy {
    void executeAttack(Member attacker, Team defenders, int moreHPmember, CombatManager manager) throws BusinessException;
} 