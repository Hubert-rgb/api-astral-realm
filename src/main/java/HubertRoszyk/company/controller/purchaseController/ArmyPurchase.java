package HubertRoszyk.company.controller.purchaseController;

import HubertRoszyk.company.configuration.GameProperties;
import HubertRoszyk.company.entiti_class.Planet;
import HubertRoszyk.company.entiti_class.PlanetPoints;
import HubertRoszyk.company.service.PlanetPointsService;
import HubertRoszyk.company.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ArmyPurchase implements PurchaseInterface<Map<Integer, Integer>> {
    @Autowired
    PlanetPointsService planetPointsService;

    @Autowired
    PlanetService planetService;

    @Autowired
    GameProperties gameProperties;

    @Override
    public PlanetPointsService getPlanetPointsService() {
        return planetPointsService;
    }

    @Override
    public PlanetService getPlanetService() {
        return planetService;
    }

    @Override
    public boolean getIsEnoughSpace(Map<Integer, Integer> object, Planet planet) {
        int level = object.keySet().iterator().next();
        int amount = object.get(level);

        PlanetPoints planetPoints = planetPointsService.getPointsByPlanetId(planet.getId());

        int roomLeftInArmyBuilding = planetPoints.getTotalAttackBuildingSize() - planetPoints.getTotalAttackBuildingLoad();

        System.out.println("t" +  planetPoints.getTotalAttackBuildingSize());
        System.out.println("l" + planetPoints.getTotalAttackBuildingLoad());
        return 0 <= roomLeftInArmyBuilding;
    }

    @Override
    public void saveObject(Map<Integer, Integer> object, int planetId) {
        /*int level = object.keySet().iterator().next();
        int amount = object.get(level);

        PlanetPoints planetPoints = planetPointsService.getPointsByPlanetId(planetId);
        Map<Integer, Integer> army = planetPoints.getArmy();
        army.put(level, army.get(level) + amount);

        planetPoints.setArmy(army);*/
        //planetPointsService.savePoints(planetPoints);
    }

    @Override
    public void upgradeLevel(Map<Integer, Integer> object, int setLevel, int planetId, int...amount) {
        int level = object.keySet().iterator().next();

        if(setLevel != level) {

            PlanetPoints planetPoints = planetPointsService.getPointsByPlanetId(planetId);

            Map<Integer, Integer> army = planetPoints.getArmy();

            army.put(setLevel - 1, army.get(setLevel - 1) - amount[0]);
            army.put(setLevel, army.get(setLevel) + amount[0]);
            planetPoints.setArmy(army);

           //planetPointsService.savePoints(planetPoints);
        } else {
            updateArmyBuildingLoad(object, planetId, setLevel);
        }
    }

    @Override
    public boolean getIsNotOnMaximumLevel(Map<Integer, Integer> object, int planetId) {
        int level = object.keySet().iterator().next();
        return level <= 7;
    }

    @Override
    public double getPrice(Map<Integer, Integer> object, int...amount) {
        int level = object.keySet().iterator().next();

        return level * amount[0] * gameProperties.getLevelCostMultiplier() * gameProperties.getArmyCost();
    }

    @Override
    public void executeTimerAction(Map<Integer, Integer> object, int planetId, int setLevel) {

    }
    void updateArmyBuildingLoad(Map<Integer, Integer> object, int planetId, int setLevel){
        int level = object.keySet().iterator().next();
        int amount = object.get(level);

        PlanetPoints planetPoints = planetPointsService.getPointsByPlanetId(planetId);
        int gotTotalAttackBuildingLoad = planetPoints.getTotalAttackBuildingLoad();
        int setTotalAttackBuildingLoad = gotTotalAttackBuildingLoad + amount;
        planetPoints.setTotalAttackBuildingLoad(setTotalAttackBuildingLoad);
    }

}
