package HubertRoszyk.company.controller;

import HubertRoszyk.company.converters.StringToShipTypeConverter;
import HubertRoszyk.company.entiti_class.*;
import HubertRoszyk.company.enumStatus.PurchaseStatus;
import HubertRoszyk.company.enumTypes.ShipType;
import HubertRoszyk.company.enumTypes.cardsType.EconomyCardType;
import HubertRoszyk.company.service.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@RestController
public class ShipController {
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
    IndustryPointsController industryPointsController;

    @PostMapping("ship-controller/ship")
    public PurchaseStatus buildShip(@RequestBody JSONObject jsonObject){//Industry points
        String shipTypeString = (String) jsonObject.get("shipType");
        int planetId = (int) jsonObject.get("planetId");
        int userId = (int) jsonObject.get("userId");

        ShipType shipType = stringToShipTypeConverter.convert(shipTypeString);

        PlanetPoints planetPoints = planetPointsService.getPointsByPlanetId(planetId);
        GalaxyPoints galaxyPoints = galaxyPointsService.getPointsByUserIdAndGalaxyId(userId, planetPoints.getPlanet().getGalaxy().getId());
        User user = userService.getUserById(userId);

        //card leveling TO DISCUSS
        int speedLevel;
        if(galaxyPoints.getEconomyCards().contains(EconomyCardType.FASTER_INDUSTRY_CARGO_SHIP)) {
            speedLevel = 1;
        } else {
            speedLevel = 0;
        }


        Ship ship = new Ship(shipType, speedLevel, 0, user);
        //building by industry points methode

        return industryPointsController.executePurchase(planetId, ship);
    }
    @PutMapping("ship-controller/ship/{shipId}")
    public PurchaseStatus upgradeShip(@PathVariable int shipId){
        Ship ship = shipService.getShipById(shipId);
        TravelRoute lastRoute = ship.getTravelRoute().get(ship.getTravelRoute().size() - 1);
        Planet planet = lastRoute.getArrivalPlanet();

        PlanetPoints planetPoints = planetPointsService.getPointsByPlanetId(planet.getId());

        int gotLevel = ship.getCapacityLevel();
        int shipYardLevel = planetPoints.getShipYardLevel();


        Ship shipAfterPurchase = shipService.getShipById(shipId);

        shipService.saveShip(shipAfterPurchase);
        return industryPointsController.executePurchase(planet.getId(), ship);
    }
    /*@PutMapping("ship-controller/ship/{shipId}/load/{volume}")
    public Ship loadShip(){

    }*/
    /*@PutMapping("ship-controller/ship/{shipId}/unload/{volume}")
    public Ship unloadShip(){

    }*/
    /*@PutMapping("ship-controller/ship/{shipId}/planet/{destinationPlanetId}")
    public TravelRoute sendShip(){

    }*/
    @GetMapping("ship-controller/ship-types")
    public List<Enum> getShipTypes(){
        List<Enum> shipTypesEnumValues = new ArrayList<Enum>(EnumSet.allOf(ShipType.class));
        return shipTypesEnumValues;
    }
}
