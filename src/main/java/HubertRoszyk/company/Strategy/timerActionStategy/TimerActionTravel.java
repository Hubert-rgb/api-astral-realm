package HubertRoszyk.company.Strategy.timerActionStategy;

import HubertRoszyk.company.entiti_class.Ship;
import HubertRoszyk.company.entiti_class.TimerAction;
import HubertRoszyk.company.entiti_class.TravelRoute;
import HubertRoszyk.company.enumStatus.ShipStatus;
import HubertRoszyk.company.service.ShipService;
import HubertRoszyk.company.service.TravelRouteService;
import org.springframework.beans.factory.annotation.Autowired;

public class TimerActionTravel implements TimerActionStrategy{
    ShipService shipService;
    public TimerActionTravel(ShipService shipService) {
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
