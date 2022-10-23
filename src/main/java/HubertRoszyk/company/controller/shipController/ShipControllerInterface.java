package HubertRoszyk.company.controller.shipController;

import HubertRoszyk.company.controller.purchaseController.ShipPurchase;
import HubertRoszyk.company.converters.StringToShipTypeConverter;
import HubertRoszyk.company.entiti_class.Planet;
import HubertRoszyk.company.entiti_class.PlanetPoints;
import HubertRoszyk.company.entiti_class.TravelRoute;
import HubertRoszyk.company.entiti_class.ship.IndustryShip;
import HubertRoszyk.company.entiti_class.ship.Ship;
import HubertRoszyk.company.entiti_class.User;
import HubertRoszyk.company.enumStatus.PurchaseStatus;
import HubertRoszyk.company.enumStatus.ShipLoadStatus;
import HubertRoszyk.company.enumTypes.ShipType;
import HubertRoszyk.company.service.PlanetPointsService;
import HubertRoszyk.company.service.ShipService;
import HubertRoszyk.company.service.UserService;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/ship-controller")
public interface ShipControllerInterface<S> {
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

        S ship = createShip(level, user);

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
    ShipLoadStatus loadShip(@PathVariable int shipId, @RequestBody JSONObject jsonObject) throws IOException;

    @PutMapping("ship/{shipId}/unload")
    ShipLoadStatus unloadShip(@PathVariable int shipId, @RequestBody JSONObject jsonObject);
    S createShip(int level, User user);
    ShipService getShipService();
    ShipPurchase getShipPurchase();
    UserService getUserService();

    StringToShipTypeConverter getStringToShipTypeConverter();
}
