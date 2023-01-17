package HubertRoszyk.company.strategy.timerActionStategy;

import HubertRoszyk.company.controller.movingResources.MovementController;
import HubertRoszyk.company.entiti_class.*;
import HubertRoszyk.company.enumStatus.PlanetStatus;
import HubertRoszyk.company.enumTypes.TimerActionType;
import HubertRoszyk.company.service.*;

public class TimerActionBattle implements TimerActionStrategy{
    private final MovementController movementController;

    private final BattleService battleService;

    private final PlanetService planetService;

    private final TimerActionService timerActionService;

    private final TimerEntityService timerEntityService;

    public TimerActionBattle(
            MovementController movementController,
            BattleService battleService,
            TimerEntityService timerEntityService,
            TimerActionService timerActionService,
            PlanetService planetService
    ){
        this.movementController = movementController;
        this.battleService = battleService;
        this.planetService = planetService;
        this.timerEntityService = timerEntityService;
        this.timerActionService = timerActionService;
    }
    @Override
    public void executeAction(TimerAction timerAction) {
        int attackId = timerAction.getExecutionId();
        Attack attack = battleService.getBattleById(attackId);

        movementController.attackExecution(attack);

        Planet planet = attack.getDefencePlanet();
        planet.setPlanetStatus(PlanetStatus.AFTER_ATTACK);

        double gotIndustryPointsProduced = planet.getIndustryPointsProduce();
        double gotSciencePointsProduced = planet.getSciencePointsProduce();

        double setIndustryPointsProduced = gotIndustryPointsProduced / 2;
        double setSciencePointsProduced = gotSciencePointsProduced / 2;

        planet.setIndustryPointsProduce(setIndustryPointsProduced);
        planet.setSciencePointsProduce(setSciencePointsProduced);

        planetService.savePlanet(planet);

        TimerEntity timerEntity = timerEntityService.getTimerEntityByGalaxyId(planet.getGalaxy().getId());
        TimerAction battleTimerAction = new TimerAction(TimerActionType.PLANET_STATUS_AFTER_ATTACK, timerEntity.getCyclesNum() + 5, attack.getDefencePlanet().getId(), timerEntity);
        timerActionService.saveTimerAction(battleTimerAction);
    }
}
