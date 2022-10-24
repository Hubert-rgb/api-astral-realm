package HubertRoszyk.company.controller.shipController;

import HubertRoszyk.company.controller.purchaseController.ShipPurchase;
import HubertRoszyk.company.converters.StringToShipTypeConverter;
import HubertRoszyk.company.entiti_class.PlanetPoints;
import HubertRoszyk.company.entiti_class.User;
import HubertRoszyk.company.entiti_class.ship.AttackShip;
import HubertRoszyk.company.enumStatus.ShipLoadStatus;
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

    @Override
    public void executeLoad(AttackShip ship, Map<Integer, Integer> armyToLoad, PlanetPoints planetPoints) {
        Map<Integer, Integer> setShipLoad = combineLoad(ship.getShipLoad(), armyToLoad);

        ship.setShipLoad(setShipLoad);
        shipService.saveShip(ship);

        Map<Integer, Integer> planetArmy = planetPoints.getArmy();
        Map<Integer, Integer> planetArmySubtract = subtractLoad(planetArmy, armyToLoad);

        planetPoints.setArmy(planetArmy);
        planetPointsService.savePoints(planetPoints);
    }

    @Override
    public void executeUnload(AttackShip ship, Map<Integer, Integer> shipLoad, Map<Integer, Integer> toUnload, PlanetPoints planetPoints) {
        System.out.println("e");
        Map<Integer, Integer> setShipLoad = subtractLoad(ship.getShipLoad(), toUnload);

        ship.setShipLoad(setShipLoad);
        shipService.saveShip(ship);

        Map<Integer, Integer> planetArmy = planetPoints.getArmy();
        Map<Integer, Integer> planetSetArmy = combineLoad(planetArmy, toUnload);

        planetPoints.setArmy(planetSetArmy);
        planetPointsService.savePoints(planetPoints);
    }

    @Override
    public int getVolume(Map<Integer, Integer> armyToLoad) {
        return getShipLoadVolume(armyToLoad);
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
        return getShipLoadVolume(attackShip.getShipLoad());
    }

    @Override
    public Map<Integer, Integer> getToLoad(JSONObject jsonObject) throws JsonProcessingException {
        return new ObjectMapper().readValue(jsonObject.toJSONString(), new TypeReference<Map<Integer, Integer>>() {});
    }

    @Override
    public Map<Integer, Integer> getShipLoad(AttackShip ship) {
        return ship.getShipLoad();
    }

    public int getShipLoadVolume(Map<Integer, Integer> shipLoad){
        int armyLoad = 0;
        for(int i = 1; i <= shipLoad.size(); i++) {
            armyLoad += shipLoad.get(i);
        }
        return armyLoad;
    }
    private Map<Integer, Integer> combineLoad(Map<Integer, Integer> shipLoad, Map<Integer, Integer> addedLoad){
        Map<Integer, Integer> combinedLoad = new HashMap<>();

        for (int i = 1; i <= shipLoad.size(); i++){
            combinedLoad.put(i, shipLoad.get(i) + addedLoad.get(i));
        }

        return combinedLoad;
    }
    private Map<Integer, Integer> subtractLoad(Map<Integer, Integer> army, Map<Integer, Integer> armyToSubtract){
        for (int i = 1; i <= armyToSubtract.size(); i++){
            int gotDivisions = army.get(i);
            int setDivisions = gotDivisions - armyToSubtract.get(i);
            army.put(i, setDivisions);
        }
        return army;
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
