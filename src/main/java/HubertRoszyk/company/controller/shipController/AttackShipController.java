package HubertRoszyk.company.controller.shipController;

import HubertRoszyk.company.controller.PlanetPointsController;
import HubertRoszyk.company.controller.purchaseController.ShipPurchase;
import HubertRoszyk.company.converters.StringToShipTypeConverter;
import HubertRoszyk.company.entiti_class.PlanetPoints;
import HubertRoszyk.company.entiti_class.User;
import HubertRoszyk.company.entiti_class.ship.AttackShip;
import HubertRoszyk.company.entiti_class.ship.Ship;
import HubertRoszyk.company.enumStatus.ShipLoadStatus;
import HubertRoszyk.company.enumStatus.ShipStatus;
import HubertRoszyk.company.enumTypes.ShipType;
import HubertRoszyk.company.service.PlanetPointsService;
import HubertRoszyk.company.service.ShipService;
import HubertRoszyk.company.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.util.HashMap;
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

    /*@Override
    public ShipLoadStatus loadShip(int shipId, JSONObject jsonObject) throws IOException {
        AttackShip ship = (AttackShip) shipService.getShipById(shipId);
        Map<Integer, Integer> armyToLoad = new ObjectMapper().readValue(jsonObject.toJSONString(), new TypeReference<Map<Integer, Integer>>() {});

        int planetId = ship.getTravelRoute().get(ship.getTravelRoute().size() - 1).getArrivalPlanet().getId();
        PlanetPoints planetPoints = planetPointsService.getPointsByPlanetId(planetId);

        int capacity = ship.getShipCapacity();
        int gotLoad = getLoad(ship.getShipLoad());

        int volume = getLoad(armyToLoad);
        System.out.println(volume);

        if(capacity >= gotLoad + volume && ship.getShipStatus().equals(ShipStatus.DOCKED)) {
            Map<Integer, Integer> setLoad = combineLoad(ship.getShipLoad(), armyToLoad);

            ship.setShipLoad(setLoad);
            shipService.saveShip(ship);

            Map<Integer, Integer> planetArmy = planetPoints.getArmy();
            for (int i = 1; i <= armyToLoad.size(); i++){
                int gotDivisions = planetArmy.get(i);
                int setDivisions = gotDivisions - armyToLoad.get(i);
                planetArmy.put(i, setDivisions);
            }
            planetPoints.setArmy(planetArmy);
            planetPointsService.savePoints(planetPoints);

            return ShipLoadStatus.EVERYTHING_LOADED;
        } else if (ship.getShipStatus().equals(ShipStatus.TRAVELING)) {
            return ShipLoadStatus.TRAVELLING;
        } else if (ship.getShipStatus().equals(ShipStatus.IN_BUILD)) {
            return ShipLoadStatus.IN_BUILD;
        } else {
            return ShipLoadStatus.NOTHING_LOAD;
        }
    }*/
    @Override
    public void executeLoad(AttackShip ship, Map<Integer, Integer> armyToLoad, PlanetPoints planetPoints) {
        Map<Integer, Integer> setLoad = combineLoad(ship.getShipLoad(), armyToLoad);

        ship.setShipLoad(setLoad);
        shipService.saveShip(ship);

        Map<Integer, Integer> planetArmy = planetPoints.getArmy();
        for (int i = 1; i <= armyToLoad.size(); i++){
            int gotDivisions = planetArmy.get(i);
            int setDivisions = gotDivisions - armyToLoad.get(i);
            planetArmy.put(i, setDivisions);
        }
        planetPoints.setArmy(planetArmy);
        planetPointsService.savePoints(planetPoints);
    }

    @Override
    public int getVolume(Map<Integer, Integer> armyToLoad) {
        return getLoad(armyToLoad);
    }

    @Override
    public int getLoad(AttackShip attackShip) {
        return getLoad(attackShip.getShipLoad());
    }

    @Override
    public Map<Integer, Integer> getLoad(JSONObject jsonObject) throws JsonProcessingException {
        return new ObjectMapper().readValue(jsonObject.toJSONString(), new TypeReference<Map<Integer, Integer>>() {});
    }

    @Override
    public ShipLoadStatus unloadShip(int shipId, JSONObject jsonObject) {
        return null;
    }

    public int getLoad(Map<Integer, Integer> shipLoad){
        int armyLoad = 0;
        for(int i = 1; i <= shipLoad.size(); i++) {
            armyLoad += shipLoad.get(i);
        }
        return armyLoad;
    }
    public Map<Integer, Integer> combineLoad(Map<Integer, Integer> shipLoad, Map<Integer, Integer> addedLoad){
        Map<Integer, Integer> combinedLoad = new HashMap<>();

        for (int i = 1; i <= shipLoad.size(); i++){
            combinedLoad.put(i, shipLoad.get(i) + addedLoad.get(i));
        }

        return combinedLoad;
    }

    @Override
    public AttackShip createShip(int level, User user) {
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
    public StringToShipTypeConverter getStringToShipTypeConverter() {
        return stringToShipTypeConverter;
    }

    @Override
    public PlanetPointsService getPlanetPointsService() {
        return planetPointsService;
    }
}
