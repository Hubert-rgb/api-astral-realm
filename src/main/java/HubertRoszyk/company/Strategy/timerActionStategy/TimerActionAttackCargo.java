package HubertRoszyk.company.Strategy.timerActionStategy;

import HubertRoszyk.company.controller.shipController.ShipControllerInterface;
import HubertRoszyk.company.entiti_class.Planet;
import HubertRoszyk.company.entiti_class.TimerAction;
import HubertRoszyk.company.entiti_class.ship.Ship;
import HubertRoszyk.company.enumStatus.ShipStatus;
import HubertRoszyk.company.service.ShipService;

public class TimerActionAttackCargo implements TimerActionStrategy{
    ShipService shipService;
    //ShipControllerInterface shipControllerInterface;
    //Planet destinationPlanet;
    public TimerActionAttackCargo(ShipService shipService /*ShipControllerInterface shipControllerInterface, Planet destinationPlanet*/) {
        this.shipService = shipService;
        /*this.shipControllerInterface = shipControllerInterface;
        this.destinationPlanet = destinationPlanet;*/
    }
    @Override
    public void executeAction(TimerAction timerAction) {
        int shipId = timerAction.getExecutionId();
        Ship ship = shipService.getShipById(shipId);

        //Planet departurePlanet = ship.getTravelRoute().get(ship.getTravelRoute().size() - 1).getArrivalPlanet();

        //shipControllerInterface.changeShipHarbour(departurePlanet.getId(), departurePlanet.getId());

        ship.setShipStatus(ShipStatus.DOCKED);
        shipService.saveShip(ship);
    }
}
