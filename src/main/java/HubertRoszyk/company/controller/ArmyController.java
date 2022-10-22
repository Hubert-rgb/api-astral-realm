package HubertRoszyk.company.controller;

import HubertRoszyk.company.controller.purchaseController.ArmyPurchase;
import HubertRoszyk.company.entiti_class.Building;
import HubertRoszyk.company.entiti_class.Planet;
import HubertRoszyk.company.entiti_class.PlanetPoints;
import HubertRoszyk.company.enumStatus.PurchaseStatus;
import HubertRoszyk.company.enumTypes.BuildingType;
import HubertRoszyk.company.service.PlanetPointsService;
import HubertRoszyk.company.service.PlanetService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ArmyController {
    @Autowired
    PlanetService planetService;

    @Autowired
    PlanetPointsService planetPointsService;

    @Autowired
    ArmyPurchase armyPurchase;

    @PostMapping("/army-controller/army")
    public PurchaseStatus addBuilding(@RequestBody JSONObject jsonInput) { //exception not string
        int planetId = (int) jsonInput.get("planetId");
        int userId = (int) jsonInput.get("userId");
        int level = (int) jsonInput.get("level");
        int amount = (int) jsonInput.get("amount");

        Planet planet = planetService.getPlanetById(planetId);

        PlanetPoints planetPoints = planetPointsService.getPointsByPlanetId(planetId);
        Map<Integer, Integer> army = planetPoints.getArmy();
        Map<Integer, Integer> armyDivision = Collections.singletonMap(level, amount);


        return armyPurchase.executePurchase(planetId, armyDivision, level);
    }
}
