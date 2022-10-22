package HubertRoszyk.company.controller;

import HubertRoszyk.company.controller.purchaseController.BuildingPurchase;
import HubertRoszyk.company.entiti_class.*;
import HubertRoszyk.company.converters.StringToBuildingsTypeConverter;
import HubertRoszyk.company.enumStatus.PurchaseStatus;
import HubertRoszyk.company.enumTypes.BuildingType;
import HubertRoszyk.company.service.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@RestController
public class BuildingsController { //dodaje, updatuje i usuwa budynki

    //musi updatować industry points i total industry points
    @Autowired
    BuildingService buildingService;

    @Autowired
    PlanetService planetService;

    @Autowired
    StringToBuildingsTypeConverter converter;

    @Autowired
    BuildingPurchase buildingPurchase;

    @Autowired
    TimerEntityService timerEntityService;

    @Autowired
    TimerActionService timerActionService;

    @PostMapping("/building-controller/buildings")
    public PurchaseStatus addBuilding(@RequestBody JSONObject jsonInput) { //exception not string
        int planetId = (int) jsonInput.get("planetId");
        int userId = (int) jsonInput.get("userId");
        String buildingsTypeString = (String) jsonInput.get("buildingType");

        System.out.println(buildingsTypeString);

        BuildingType buildingType = converter.convert(buildingsTypeString);

        System.out.println(buildingType);

        Planet planet = planetService.getPlanetById(planetId);
        //Set<Planet> usersPlanets = planetService.getPlanetsByUserId(userId);

        Building building = new Building(buildingType, planet);

        return buildingPurchase.executePurchase(building.getPlanet().getId(), building, 1);
    }
    @PutMapping("/building-controller/buildings/{buildingId}")
    public PurchaseStatus upgradeBuilding(@PathVariable int buildingId) {

        Building building = buildingService.getBuildingById(buildingId);
        int level = building.getBuildingLevel();
       // Set<Planet> planets = planetService.getPlanetsByUserId(userId);

        return buildingPurchase.executePurchase(building.getPlanet().getId(), building, level + 1);
    }

    @GetMapping("/building-controller/buildings/planets/{planetId}")
    public List<Building> getBuildingListOnPlanet(@PathVariable int planetId) {
        return buildingService.getBuildingsByPlanetId(planetId);
    }

    @GetMapping("building-controller/building-types")
    public List<Enum> getAllBuildingTypes(){
        List<Enum> buildingTypesEnumValues = new ArrayList<Enum>(EnumSet.allOf(BuildingType.class));
        return buildingTypesEnumValues;
    }
}
