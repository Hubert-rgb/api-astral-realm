package HubertRoszyk.company.Strategy.timerActionStategy;

import HubertRoszyk.company.controller.movingResources.MovementController;
import HubertRoszyk.company.entiti_class.Attack;
import HubertRoszyk.company.entiti_class.Planet;
import HubertRoszyk.company.entiti_class.TimerAction;
import HubertRoszyk.company.entiti_class.TimerEntity;
import HubertRoszyk.company.entiti_class.ship.AttackShip;
import HubertRoszyk.company.entiti_class.ship.Ship;
import HubertRoszyk.company.enumStatus.PlanetStatus;
import HubertRoszyk.company.enumStatus.ShipStatus;
import HubertRoszyk.company.enumTypes.TimerActionType;
import HubertRoszyk.company.service.*;

import java.util.List;
import java.util.Set;

public class TimerActionAttackCargo implements TimerActionStrategy{
    private final ShipService shipService;

    private final TimerEntityService timerEntityService;
    private final TimerActionService timerActionService;

    private final BattleService battleService;

    private final PlanetService planetService;

    public TimerActionAttackCargo(ShipService shipService, BattleService battleService, TimerEntityService timerEntityService, TimerActionService timerActionService, PlanetService planetService) {
        this.shipService = shipService;
        this.timerEntityService = timerEntityService;
        this.timerActionService = timerActionService;
        this.battleService = battleService;
        this.planetService = planetService;
    }
    @Override
    public void executeAction(TimerAction timerAction) {
        int attackId = timerAction.getExecutionId();
        Attack attack = battleService.getBattleById(attackId);
        Set<AttackShip> attackShips = attack.getAttackShips();

        for (AttackShip ship: attackShips) {
            ship.setShipStatus(ShipStatus.IN_BATTLE);

            shipService.saveShip(ship);
        }


        Planet planet = attack.getDefencePlanet();
        planet.setPlanetStatus(PlanetStatus.UNDER_ATTACK);
        planetService.savePlanet(planet);

        TimerEntity timerEntity = timerEntityService.getTimerEntityByGalaxyId(planet.getGalaxy().getId());
        TimerAction battleTimerAction = new TimerAction(TimerActionType.BATTLE, timerEntity.getCyclesNum() + 1, attackId, timerEntity);
        timerActionService.saveTimerAction(battleTimerAction);
    }
}
