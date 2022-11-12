package HubertRoszyk.company.Strategy.timerActionStategy;

import HubertRoszyk.company.controller.movingResources.MovementController;
import HubertRoszyk.company.entiti_class.TimerAction;
import HubertRoszyk.company.entiti_class.ship.AttackShip;
import HubertRoszyk.company.entiti_class.ship.Ship;
import HubertRoszyk.company.enumStatus.ShipStatus;
import HubertRoszyk.company.service.ShipService;

public class TimerActionAttackCargo implements TimerActionStrategy{
    ShipService shipService;
    MovementController movementController;
    public TimerActionAttackCargo(ShipService shipService, MovementController movementController) {
        this.shipService = shipService;
        this.movementController = movementController;
    }
    @Override
    public void executeAction(TimerAction timerAction) {
        int shipId = timerAction.getExecutionId();
        AttackShip ship = (AttackShip) shipService.getShipByIdWithRoutes(shipId);

        ship.setShipStatus(ShipStatus.IN_BATTLE);
        movementController.attackExecution(ship);

        shipService.saveShip(ship);
    }
}
