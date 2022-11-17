package HubertRoszyk.company.Strategy.timerActionStategy;

import HubertRoszyk.company.controller.movingResources.MovementController;
import HubertRoszyk.company.entiti_class.Attack;
import HubertRoszyk.company.entiti_class.Planet;
import HubertRoszyk.company.entiti_class.TimerAction;
import HubertRoszyk.company.entiti_class.ship.AttackShip;
import HubertRoszyk.company.enumStatus.PlanetStatus;
import HubertRoszyk.company.service.ShipService;

public class TimerActionBattle implements TimerActionStrategy{
    private final MovementController movementController;
    private final ShipService shipService;

    public TimerActionBattle(MovementController movementController, ShipService shipService){
        this.movementController = movementController;
        this.shipService = shipService;
    }
    @Override
    public void executeAction(TimerAction timerAction) {
        int shipId = timerAction.getExecutionId();
        AttackShip ship = (AttackShip) shipService.getShipById(shipId);

        movementController.attackExecution(ship);

        Planet planet = ship.getCurrentPlanet();
        planet.setPlanetStatus(PlanetStatus.AFTER_ATTACK);

        shipService.saveShip(ship);
    }
}
