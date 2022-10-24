package HubertRoszyk.company.controller.shipController;

import HubertRoszyk.company.controller.purchaseController.ShipPurchase;
import HubertRoszyk.company.converters.StringToShipTypeConverter;
import HubertRoszyk.company.entiti_class.Planet;
import HubertRoszyk.company.entiti_class.PlanetPoints;
import HubertRoszyk.company.entiti_class.TravelRoute;
import HubertRoszyk.company.entiti_class.ship.Ship;
import HubertRoszyk.company.entiti_class.User;
import HubertRoszyk.company.enumStatus.PurchaseStatus;
import HubertRoszyk.company.enumStatus.ShipLoadStatus;
import HubertRoszyk.company.enumStatus.ShipStatus;
import HubertRoszyk.company.enumTypes.ShipType;
import HubertRoszyk.company.service.PlanetPointsService;
import HubertRoszyk.company.service.ShipService;
import HubertRoszyk.company.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/ship-controller")
public interface ShipControllerInterface<ShipT, LoadType> {
    @PostMapping("/ship")
    default PurchaseStatus buildShip(@RequestBody JSONObject jsonObject){
        ShipPurchase shipPurchase = getShipPurchase();
        UserService userService = getUserService();
        StringToShipTypeConverter stringToShipTypeConverter = getStringToShipTypeConverter();

        String shipTypeString = (String) jsonObject.get("shipType");
        int planetId = (int) jsonObject.get("planetId");
        int userId = (int) jsonObject.get("userId");
        int level = (int) jsonObject.get("level");

        ShipType shipType = stringToShipTypeConverter.convert(shipTypeString);

        User user = userService.getUserById(userId);

        ShipT ship = createShip(level, user);

        return shipPurchase.executePurchase(planetId, (Ship) ship, level);
    }
    @PutMapping("/ship/{shipId}")
    default PurchaseStatus upgradeShip(@PathVariable int shipId){
        ShipService shipService = getShipService();
        ShipPurchase shipPurchase = getShipPurchase();

        Ship ship = shipService.getShipById(shipId);
        TravelRoute lastRoute = ship.getTravelRoute().get(ship.getTravelRoute().size() - 1);
        Planet planet = lastRoute.getArrivalPlanet();

        int level = ship.getCapacityLevel();

        return shipPurchase.executePurchase(planet.getId(), ship, level + 1);
    }
    @PutMapping("/ship/{shipId}/load")
    default ShipLoadStatus loadShip(@PathVariable int shipId, @RequestBody JSONObject jsonObject) throws JsonProcessingException {
        ShipService shipService = getShipService();
        PlanetPointsService planetPointsService = getPlanetPointsService();

        ShipT gotShip = (ShipT) shipService.getShipById(shipId);
        LoadType load = getToLoad(jsonObject);

        Ship ship = (Ship) gotShip;

        int planetId =  ship.getTravelRoute().get(ship.getTravelRoute().size() - 1).getArrivalPlanet().getId();
        PlanetPoints planetPoints = planetPointsService.getPointsByPlanetId(planetId);

        int capacity = ship.getShipCapacity();
        int gotLoad = getShipLoadVolume(gotShip);

        int volume = getVolume(load);
        System.out.println(volume);

        if(capacity >= gotLoad + volume && ship.getShipStatus().equals(ShipStatus.DOCKED)) {
            executeLoad(gotShip, load, planetPoints);

            return ShipLoadStatus.EVERYTHING_LOADED;
        } else if (ship.getShipStatus().equals(ShipStatus.TRAVELING)) {
            return ShipLoadStatus.TRAVELLING;
        } else if (ship.getShipStatus().equals(ShipStatus.IN_BUILD)) {
            return ShipLoadStatus.IN_BUILD;
        } else {
            return ShipLoadStatus.NOTHING_LOAD;
        }
    }
    @PutMapping("/ship/{shipId}/unload")
    default ShipLoadStatus unloadShip(@PathVariable int shipId, @RequestBody JSONObject jsonObject) throws JsonProcessingException {
        ShipService shipService = getShipService();
        PlanetPointsService planetPointsService = getPlanetPointsService();

        ShipT gotShip = (ShipT) shipService.getShipById(shipId);
        Ship ship = (Ship) gotShip;
        LoadType load = getToLoad(jsonObject);
        LoadType shipLoad = getShipLoad(gotShip);

        int volume = getVolume(load);

        int planetId = ship.getTravelRoute().get(ship.getTravelRoute().size() - 1).getArrivalPlanet().getId();
        PlanetPoints planetPoints = planetPointsService.getPointsByPlanetId(planetId);

        int capacity = getPlanetCapacity(planetPoints);
        int planetLoadVolume = getPlanetLoadVolume(planetPoints);

        if(capacity >= planetLoadVolume + volume && ship.getShipStatus().equals(ShipStatus.DOCKED)) {
            executeUnload(gotShip, shipLoad, load, planetPoints);
            System.out.println("exe");

            return ShipLoadStatus.EVERYTHING_LOADED;
        } else if (ship.getShipStatus().equals(ShipStatus.TRAVELING)) {
            return ShipLoadStatus.TRAVELLING;
        } else if (ship.getShipStatus().equals(ShipStatus.IN_BUILD)) {
            return ShipLoadStatus.IN_BUILD;
        } else {
            return ShipLoadStatus.NOTHING_LOAD;
        }
    }

    void executeLoad(ShipT ship, LoadType load, PlanetPoints planetPoints);

    void executeUnload(ShipT ship, LoadType shipLoad, LoadType toUnload, PlanetPoints planetPoints);

    int getVolume(LoadType load);

    int getPlanetCapacity(PlanetPoints planetPoints);

    int getPlanetLoadVolume(PlanetPoints planetPoints);

    int getShipLoadVolume(ShipT shipT);

    LoadType getToLoad(JSONObject jsonObject) throws JsonProcessingException;
    LoadType getShipLoad(ShipT ship);

    ShipT createShip(int level, User user);

    ShipService getShipService();
    ShipPurchase getShipPurchase();
    UserService getUserService();

    StringToShipTypeConverter getStringToShipTypeConverter();

    PlanetPointsService getPlanetPointsService();
}
