package HubertRoszyk.company.Strategy.timerActionStategy;

import HubertRoszyk.company.entiti_class.ship.Ship;
import HubertRoszyk.company.entiti_class.TimerAction;
import HubertRoszyk.company.enumStatus.ShipStatus;
import HubertRoszyk.company.service.ShipService;

public class TimerActionIndustryCargo implements TimerActionStrategy{
    ShipService shipService;
    public TimerActionIndustryCargo(ShipService shipService) {
        this.shipService = shipService;
    }
    @Override
    public void executeAction(TimerAction timerAction) {
        int shipId = timerAction.getExecutionId();
        Ship ship = shipService.getShipById(shipId);

        ship.setShipStatus(ShipStatus.DOCKED);
        shipService.saveShip(ship);
    }
}
