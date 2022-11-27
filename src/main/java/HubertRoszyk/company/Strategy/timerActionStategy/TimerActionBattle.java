package HubertRoszyk.company.Strategy.timerActionStategy;

import HubertRoszyk.company.controller.movingResources.MovementController;
import HubertRoszyk.company.entiti_class.Attack;
import HubertRoszyk.company.entiti_class.Planet;
import HubertRoszyk.company.entiti_class.TimerAction;
import HubertRoszyk.company.enumStatus.PlanetStatus;
import HubertRoszyk.company.service.BattleService;

public class TimerActionBattle implements TimerActionStrategy{
    private final MovementController movementController;

    private final BattleService battleService;

    public TimerActionBattle(MovementController movementController, BattleService battleService){
        this.movementController = movementController;
        this.battleService = battleService;
    }
    @Override
    public void executeAction(TimerAction timerAction) {
        int attackId = timerAction.getExecutionId();
        Attack attack = battleService.getBattleById(attackId);

        movementController.attackExecution(attack);

        Planet planet = attack.getDefencePlanet();
        planet.setPlanetStatus(PlanetStatus.AFTER_ATTACK);
    }
}
