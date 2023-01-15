package HubertRoszyk.company.controller.shipController;

import HubertRoszyk.company.controller.industryPurchaseController.ShipPurchase;
import HubertRoszyk.company.converters.StringToShipTypeConverter;
import HubertRoszyk.company.entiti_class.*;
import HubertRoszyk.company.entiti_class.ship.IndustryShip;
import HubertRoszyk.company.enumTypes.ShipType;
import HubertRoszyk.company.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/industry-ship-controller")
public class IndustryShipController implements ShipControllerInterface<IndustryShip, Integer>{
    @Autowired
    ShipService shipService;

    @Autowired
    TravelRouteService travelRouteService;

    @Autowired
    StringToShipTypeConverter stringToShipTypeConverter;

    @Autowired
    PlanetService planetService;

    @Autowired
    PlanetPointsService planetPointsService;

    @Autowired
    GalaxyPointsService galaxyPointsService;

    @Autowired
    UserService userService;

    @Autowired
    ShipPurchase shipPurchase;

    @Autowired
    TimerEntityService timerEntityService;

    @Autowired
    TimerActionService timerActionService;

    @Override
    public IndustryShip createShip(int level, User user) {
        return new IndustryShip(ShipType.INDUSTRY_CARGO, level, user);
    }

    @Override
    public void executeLoad(IndustryShip ship, Integer load, PlanetPoints planetPoints) {
        double shipLoad = ship.getShipLoad();
        int setLoad = (int) (shipLoad + load);

        ship.setShipLoad(setLoad);
        shipService.saveShip(ship);

        double gotIndustryPoints = planetPoints.getIndustryPoints();
        double setIndustryPoints = gotIndustryPoints - load;
        planetPoints.setIndustryPoints(setIndustryPoints);
        planetPointsService.savePoints(planetPoints);
    }

    @Override
    public void executeUnload(IndustryShip ship, Integer shipLoad, Integer toUnload, PlanetPoints planetPoints) {
        int setLoad = (int) (shipLoad - toUnload);

        ship.setShipLoad(setLoad);
        shipService.saveShip(ship);

        double gotIndustryPoints = planetPoints.getIndustryPoints();
        double setIndustryPoints = gotIndustryPoints + toUnload;
        planetPoints.setIndustryPoints(setIndustryPoints);
        planetPointsService.savePoints(planetPoints);
    }

    /*@Override
    public void executeTravel(TravelRoute travelRoute, Ship ship) {
        changeShipHarbour(travelRoute.getDeparturePlanet().getId(), travelRoute.getArrivalPlanet().getId());

        *//** timer task*//*
        TimerEntity timerEntity = timerEntityService.getTimerEntityByGalaxyId(travelRoute.getArrivalPlanet().getGalaxy().getId());

        TimerAction timerAction = new TimerAction(TimerActionType.INDUSTRY_CARGO, travelRoute.getRouteEndingCycle(), ship.getId(), timerEntity);

        timerEntityService.saveTimerEntity(timerEntity);
        timerActionService.saveTimerAction(timerAction);
    }
*/
    @Override
    public int getVolume(Integer load) {
        return load;
    }

    @Override
    public int getPlanetCapacity(PlanetPoints planetPoints) {
        return planetPoints.getTotalStorageSize();
    }

    @Override
    public int getPlanetLoadVolume(PlanetPoints planetPoints) {
        return (int) planetPoints.getIndustryPoints();
    }

    @Override
    public int getShipLoadVolume(IndustryShip industryShip) {
        return (int) industryShip.getShipLoad();
    }

    @Override
    public Integer getToLoad(JSONObject jsonObject) throws JsonProcessingException {
        return (int) jsonObject.get("volume");
    }

    @Override
    public Integer getShipLoad(IndustryShip ship) {
        return ship.getShipLoad();
    }

    @Override
    public ShipService getShipService() {
        return shipService;
    }

    @Override
    public ShipPurchase getShipPurchase() {
        return shipPurchase;
    }

    @Override
    public UserService getUserService() {
        return userService;
    }

    @Override
    public PlanetService getPlanetService() {
        return planetService;
    }

    @Override
    public TimerEntityService getTimerEntityService() {
        return timerEntityService;
    }

    @Override
    public TimerActionService getTimerActionService() {
        return timerActionService;
    }

    @Override
    public TravelRouteService getTravelRouteService() {
        return travelRouteService;
    }

    @Override
    public StringToShipTypeConverter getStringToShipTypeConverter() {
        return stringToShipTypeConverter;
    }

    @Override
    public PlanetPointsService getPlanetPointsService() {
        return planetPointsService;
    }
}
