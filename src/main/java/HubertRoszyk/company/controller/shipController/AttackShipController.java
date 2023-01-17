package HubertRoszyk.company.controller.shipController;

import HubertRoszyk.company.controller.ArmyController;
import HubertRoszyk.company.controller.industryPurchaseController.ShipPurchase;
import HubertRoszyk.company.converters.StringToShipTypeConverter;
import HubertRoszyk.company.entiti_class.*;
import HubertRoszyk.company.entiti_class.ship.AttackShip;
import HubertRoszyk.company.enumTypes.ShipType;
import HubertRoszyk.company.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/attack-ship-controller")
public class AttackShipController implements ShipControllerInterface<AttackShip, Map<Integer, Integer>>{
    @Autowired
    StringToShipTypeConverter stringToShipTypeConverter;

    @Autowired
    UserService userService;

    @Autowired
    ShipPurchase shipPurchase;

    @Autowired
    ShipService shipService;

    @Autowired
    PlanetPointsService planetPointsService;

    @Autowired
    PlanetService planetService;

    @Autowired
    TimerActionService timerActionService;

    @Autowired
    TimerEntityService timerEntityService;

    @Autowired
    TravelRouteService travelRouteService;

    @Override
    public void executeLoad(AttackShip ship, Map<Integer, Integer> armyToLoad, PlanetPoints planetPoints) {
        Map<Integer, Integer> setShipLoad = ArmyController.combineArmy(ship.getShipLoad(), armyToLoad);

        ship.setShipLoad(setShipLoad);
        shipService.saveShip(ship);

        Map<Integer, Integer> planetArmy = planetPoints.getArmy();
        Map<Integer, Integer> planetArmySubtract = ArmyController.subtractLoad(planetArmy, armyToLoad);

        planetPoints.setArmy(planetArmySubtract);
        planetPointsService.savePoints(planetPoints);
    }

    @Override
    public void executeUnload(AttackShip ship, Map<Integer, Integer> shipLoad, Map<Integer, Integer> toUnload, PlanetPoints planetPoints) {
        System.out.println("e");
        Map<Integer, Integer> setShipLoad = ArmyController.subtractLoad(ship.getShipLoad(), toUnload);

        ship.setShipLoad(setShipLoad);
        shipService.saveShip(ship);

        Map<Integer, Integer> planetArmy = planetPoints.getArmy();
        Map<Integer, Integer> planetSetArmy = ArmyController.combineArmy(planetArmy, toUnload);

        planetPoints.setArmy(planetSetArmy);
        planetPointsService.savePoints(planetPoints);
    }

    /*@Override
    public void executeTravel(TravelRoute travelRoute, Ship ship) {
        changeShipHarbour(travelRoute.getDeparturePlanet().getId(), travelRoute.getArrivalPlanet().getId());

        *//** timer task*//*
        TimerEntity timerEntity = timerEntityService.getTimerEntityByGalaxyId(travelRoute.getArrivalPlanet().getGalaxy().getId());

        TimerAction timerAction = new TimerAction(TimerActionType.ATTACK_CARGO, travelRoute.getRouteEndingCycle(), ship.getId(), timerEntity);

        timerEntityService.saveTimerEntity(timerEntity);
        timerActionService.saveTimerAction(timerAction);
    }*/

    @Override
    public int getVolume(Map<Integer, Integer> armyToLoad) {
        return ArmyController.getArmySize(armyToLoad);
    }

    @Override
    public int getPlanetCapacity(PlanetPoints planetPoints) {
        return planetPoints.getTotalAttackBuildingSize();
    }

    @Override
    public int getPlanetLoadVolume(PlanetPoints planetPoints) {
        return planetPoints.getTotalAttackBuildingLoad();
    }

    @Override
    public int getShipLoadVolume(AttackShip attackShip) {
        return ArmyController.getArmySize(attackShip.getShipLoad());
    }

    @Override
    public Map<Integer, Integer> getToLoad(JSONObject jsonObject) throws JsonProcessingException {
        return new ObjectMapper().readValue(jsonObject.toJSONString(), new TypeReference<Map<Integer, Integer>>() {});
    }

    @Override
    public Map<Integer, Integer> getShipLoad(AttackShip ship) {
        return ship.getShipLoad();
    }



    @Override
    public AttackShip createShip(int level, User user, int PlanetId) {
        return new AttackShip(ShipType.ATTACK_CARGO, level, user);
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
