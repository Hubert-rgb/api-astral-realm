package HubertRoszyk.company.Strategy.timerActionStategy;

import HubertRoszyk.company.controller.movingResources.MovementController;
import HubertRoszyk.company.entiti_class.Planet;
import HubertRoszyk.company.entiti_class.TimerAction;
import HubertRoszyk.company.entiti_class.TimerEntity;
import HubertRoszyk.company.entiti_class.ship.AttackShip;
import HubertRoszyk.company.entiti_class.ship.Ship;
import HubertRoszyk.company.enumStatus.PlanetStatus;
import HubertRoszyk.company.enumStatus.ShipStatus;
import HubertRoszyk.company.enumTypes.TimerActionType;
import HubertRoszyk.company.service.ShipService;
import HubertRoszyk.company.service.TimerActionService;
import HubertRoszyk.company.service.TimerEntityService;

public class TimerActionAttackCargo implements TimerActionStrategy{
    private final ShipService shipService;

    private final TimerEntityService timerEntityService;
    private final TimerActionService timerActionService;

    public TimerActionAttackCargo(ShipService shipService, TimerEntityService timerEntityService, TimerActionService timerActionService) {
        this.shipService = shipService;
        this.timerEntityService = timerEntityService;
        this.timerActionService = timerActionService;
    }
    @Override
    public void executeAction(TimerAction timerAction) {
        int shipId = timerAction.getExecutionId();
        AttackShip ship = (AttackShip) shipService.getShipById(shipId);

        ship.setShipStatus(ShipStatus.IN_BATTLE);

        Planet planet = ship.getCurrentPlanet();
        planet.setPlanetStatus(PlanetStatus.UNDER_ATTACK);

        TimerEntity timerEntity = timerEntityService.getTimerEntityByGalaxyId(planet.getGalaxy().getId());
        TimerAction battleTimerAction = new TimerAction(TimerActionType.BATTLE, timerEntity.getCyclesNum() + 1, shipId, timerEntity);
        timerActionService.saveTimerAction(battleTimerAction);

        shipService.saveShip(ship);
    }
}
