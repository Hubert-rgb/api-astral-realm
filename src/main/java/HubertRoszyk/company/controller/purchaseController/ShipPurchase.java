package HubertRoszyk.company.controller.purchaseController;

import HubertRoszyk.company.configuration.GameProperties;
import HubertRoszyk.company.entiti_class.*;
import HubertRoszyk.company.entiti_class.ship.Ship;
import HubertRoszyk.company.enumStatus.ShipStatus;
import HubertRoszyk.company.enumTypes.TimerActionType;
import HubertRoszyk.company.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ShipPurchase implements PurchaseInterface<Ship>{
    @Autowired
    GameProperties gameProperties;

    @Autowired
    ShipService shipService;

    @Autowired
    TimerEntityService timerEntityService;

    @Autowired
    TimerActionService timerActionService;

    @Autowired
    TravelRouteService travelRouteService;

    @Override
    public boolean getIsEnoughSpace(Ship ship, Planet planet) {
        if(ship.getCapacityLevel() > 1) {
            return true;
        } else {
            PlanetPoints planetPoints = planetPointsService.getPointsByPlanetId(planet.getId());
            return  planetPoints.getTotalHarbourSize() > planetPoints.getTotalHarbourLoad() + 1;
        }
    }

    @Override
    public void saveObject(Ship ship, int planetId) {
        ship.setShipStatus(ShipStatus.IN_BUILD);
        shipService.saveShip(ship);
    }

    @Override
    public void upgradeLevel(Ship ship, int setLevel, int planetId) {
        /*int gotShipLevel = ship.getCapacityLevel();
        int setShipLevel = gotShipLevel + 1;*/

        ship.setCapacityLevel(setLevel);

        //return setLevel;
    }

    @Override
    public boolean getIsNotOnMaximumLevel(Ship ship, int planetId) {
        return ship.getCapacityLevel() <= ship.getShipType().getLevelNums();
    }

    @Override
    public double getPrice(Ship ship) {
        int shipTypePrice = ship.getShipType().getShipPrice();
        double costMultiplier = gameProperties.getLevelCostMultiplier() * ship.getCapacityLevel();
        return shipTypePrice * costMultiplier;
    }

    @Override
    public void executeTimerAction(Ship object, int planetId, int setLevel) {
        Planet planet = planetService.getPlanetById(planetId);
        PlanetPoints planetPoints = planetPointsService.getPointsByPlanetId(planetId);

        TravelRoute travelRoute = new TravelRoute(planet, planet, (Ship) object, 0, timerEntityService);
        travelRouteService.saveTravelRoutes(travelRoute);
        if(setLevel <= 1) {
            int gotHarbourLoad = planetPoints.getTotalHarbourLoad();
            int setHarbourLoad = gotHarbourLoad + 1;
            planetPoints.setTotalHarbourLoad(setHarbourLoad);
        }

        /** timer action */
        TimerEntity timerEntity = timerEntityService.getTimerEntityByGalaxyId(planet.getGalaxy().getId());
        int currentCycle = timerEntity.getCyclesNum();
        int buildingEndCycle = ((Ship) object).getShipType().getConstructionCycles() + currentCycle;

        TimerAction timerAction = new TimerAction(TimerActionType.SHIP, buildingEndCycle, ((Ship) object).getId(), timerEntity);

        timerEntityService.saveTimerEntity(timerEntity);
        timerActionService.saveTimerAction(timerAction);
    }

    @Autowired
    PlanetPointsService planetPointsService;

    @Autowired
    PlanetService planetService;

    @Override
    public PlanetPointsService getPlanetPointsService() {
        return planetPointsService;
    }

    @Override
    public PlanetService getPlanetService() {
        return planetService;
    }

}
