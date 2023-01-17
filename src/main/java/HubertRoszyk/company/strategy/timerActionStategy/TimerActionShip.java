package HubertRoszyk.company.strategy.timerActionStategy;

import HubertRoszyk.company.entiti_class.ship.Ship;
import HubertRoszyk.company.entiti_class.TimerAction;
import HubertRoszyk.company.enumStatus.ShipStatus;
import HubertRoszyk.company.service.ShipService;

public class TimerActionShip implements TimerActionStrategy{
    private ShipService shipService;

    public TimerActionShip(ShipService shipService){
        this.shipService = shipService;
    }
    @Override
    public void executeAction(TimerAction timerAction) {
        int shipId = timerAction.getExecutionId();
        Ship ship = shipService.getShipById(shipId);

        ship.setShipStatus(ShipStatus.DOCKED);
        ship.getCapacity();

        shipService.saveShip(ship);
    }
}
