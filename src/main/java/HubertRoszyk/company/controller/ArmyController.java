package HubertRoszyk.company.controller;

import HubertRoszyk.company.controller.purchaseController.ArmyPurchase;
import HubertRoszyk.company.enumStatus.PurchaseStatus;
import HubertRoszyk.company.enumTypes.BuildingType;
import HubertRoszyk.company.service.PlanetPointsService;
import HubertRoszyk.company.service.PlanetService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@RestController
public class ArmyController {
    @Autowired
    PlanetService planetService;

    @Autowired
    PlanetPointsService planetPointsService;

    @Autowired
    ArmyPurchase armyPurchase;

    @PostMapping("/army-controller/army")
    public PurchaseStatus addArmy(@RequestBody JSONObject jsonInput) {
        int planetId = (int) jsonInput.get("planetId");
        int level = (int) jsonInput.get("level");
        int amount = (int) jsonInput.get("amount");

        Map<Integer, Integer> armyDivision = Collections.singletonMap(level, amount);

        return armyPurchase.executePurchase(planetId, armyDivision, level);
    }
    public static Map<Integer, Integer> combineArmy(Map<Integer, Integer> armyA, Map<Integer, Integer> armyB){
        Map<Integer, Integer> combinedArmy = new HashMap<>();

        for (int i = 1; i <= armyA.size(); i++){
            combinedArmy.put(i, armyA.get(i) + armyB.get(i));
        }

        return combinedArmy;
    }
    public static Map<Integer, Integer> subtractLoad(Map<Integer, Integer> army, Map<Integer, Integer> armyToSubtract){
        for (int i = 1; i <= armyToSubtract.size(); i++){
            int gotDivisions = army.get(i);
            int setDivisions = gotDivisions - armyToSubtract.get(i);
            army.put(i, setDivisions);
        }
        return army;
    }
    public static Map<Integer, Integer> getEmptyArmy(){
        Map<Integer, Integer> army = new HashMap<>();
        for (int i = 1; i <= BuildingType.ATTACK.getLevelNums(); i++){
            army.put(i, 0);
        }
        return army;
    }
    public static int getArmySize(Map<Integer, Integer> army){
        int armyLoad = 0;
        for(int i = 1; i <= army.size(); i++) {
            armyLoad += army.get(i);
        }
        return armyLoad;
    }
    public static int getArmyValue(Map<Integer, Integer> army){
        System.out.println(army);
        int value = 0;
        for (int armyLevel = 1; armyLevel <= army.size(); armyLevel++){
            int armyDivisionNumber = army.get(armyLevel);
            int armyLevelValue = armyLevel * armyDivisionNumber;
            value += armyLevelValue;
        }
        return value;
    }
    public static Stack<Integer> armyMapToStack(Map<Integer, Integer> army){
        Stack<Integer> stack = new Stack<>();
        for (int i = 1; i <= army.size(); i++){
            for (int j = 0; j < army.get(i); j++){
                stack.add(i);
            }
        }
        return stack;
    }
    //TODO upgrading army
   /* @PutMapping("/army-controller/army")
    public PurchaseStatus upgradeBuilding(@RequestBody JSONObject jsonInput) {


        Building building = buildingService.getBuildingById(buildingId);
        int level = building.getBuildingLevel();
        // Set<Planet> planets = planetService.getPlanetsByUserId(userId);

        return buildingPurchase.executePurchase(building.getPlanet().getId(), building, level + 1);
    }*/
}
