package HubertRoszyk.company.controller.shipController;

import HubertRoszyk.company.controller.purchaseController.ShipPurchase;
import HubertRoszyk.company.converters.StringToShipTypeConverter;
import HubertRoszyk.company.entiti_class.*;
import HubertRoszyk.company.entiti_class.ship.Ship;
import HubertRoszyk.company.enumStatus.PurchaseStatus;
import HubertRoszyk.company.enumStatus.ShipLoadStatus;
import HubertRoszyk.company.enumStatus.ShipStatus;
import HubertRoszyk.company.enumTypes.ShipType;
import HubertRoszyk.company.enumTypes.TimerActionType;
import HubertRoszyk.company.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@RestController
@RequestMapping("/ship-controller")
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
    @PutMapping("/ship/{shipId}/planet/{destinationPlanetId}")
    default TravelRoute sendShip(@PathVariable int shipId, @PathVariable int destinationPlanetId){
        ShipService shipService = getShipService();
        PlanetService planetService = getPlanetService();
        TimerEntityService timerEntityService = getTimerEntityService();
        TimerActionService timerActionService = getTimerActionService();
        TravelRouteService travelRouteService = getTravelRouteService();

        Ship ship = shipService.getShipById(shipId);
        Planet destinationPlanet = planetService.getPlanetById(destinationPlanetId);
        Planet departurePlanet = ship.getTravelRoute().get(ship.getTravelRoute().size()-1).getArrivalPlanet();

        if(ship.getShipStatus().equals(ShipStatus.DOCKED)) {
            ship.setShipStatus(ShipStatus.TRAVELING);

            TravelRoute travelRoute = new TravelRoute(departurePlanet, destinationPlanet, ship, timerEntityService);

            //TODO if it's army cargo it should be after flight time
            executeTravel(travelRoute, ship);

            shipService.saveShip(ship);
            travelRouteService.saveTravelRoutes(travelRoute);

            return travelRoute;
        } else {
            //returning status (travelling, in build, done)
            return null;
        }
    }
    @GetMapping("/ship-types")
    default List<Enum> getShipTypes(){
        List<Enum> shipTypesEnumValues = new ArrayList<Enum>(EnumSet.allOf(ShipType.class));
        return shipTypesEnumValues;
    }
   /* @GetMapping("ship-controller/planet/{planetId}")
    public List<Ship> getShipsByPlanetId(@PathVariable int planetId){

    }*/
    @GetMapping("/ship")
    default List<Ship> getShips(){
        ShipService shipService = getShipService();
        return shipService.getShipsList();
    }

    default void changeShipHarbour(int departurePlanetId, int destinationPlanetId) {
        PlanetPointsService planetPointsService = getPlanetPointsService();

        PlanetPoints destinationPlanetPoints = planetPointsService.getPointsByPlanetId(destinationPlanetId);
        PlanetPoints departurePlanetPoints = planetPointsService.getPointsByPlanetId(departurePlanetId);

        int gotDepartureHarbourLoad = departurePlanetPoints.getTotalHarbourLoad();
        int setDepartureHarbourLoad = gotDepartureHarbourLoad - 1;
        departurePlanetPoints.setTotalHarbourLoad(setDepartureHarbourLoad);
        planetPointsService.savePoints(departurePlanetPoints);

        int gotDestinationHarbourLoad = destinationPlanetPoints.getTotalHarbourLoad();
        int setDestinationHarbourLoad = gotDestinationHarbourLoad + 1;
        destinationPlanetPoints.setTotalHarbourLoad(setDestinationHarbourLoad);
        planetPointsService.savePoints(departurePlanetPoints);
    }

    void executeLoad(ShipT ship, LoadType load, PlanetPoints planetPoints);
    void executeUnload(ShipT ship, LoadType shipLoad, LoadType toUnload, PlanetPoints planetPoints);

    /** orginalnie chciałęm żeby było oddzielnie dla Industry i Attack, ale myśle, że będzie można transportować armię nie atakując
     * wtedy to działałoby jak transportowanie industry zajmując miejsce w porcie przeznaczenia*/
    default void executeTravel(TravelRoute travelRoute, Ship ship){
        TimerActionService timerActionService = getTimerActionService();
        TimerEntityService timerEntityService = getTimerEntityService();

        changeShipHarbour(travelRoute.getDeparturePlanet().getId(), travelRoute.getArrivalPlanet().getId());

        /** timer task*/
        TimerEntity timerEntity = timerEntityService.getTimerEntityByGalaxyId(travelRoute.getArrivalPlanet().getGalaxy().getId());

        TimerAction timerAction = new TimerAction(TimerActionType.INDUSTRY_CARGO, travelRoute.getRouteEndingCycle(), ship.getId(), timerEntity);

        timerEntityService.saveTimerEntity(timerEntity);
        timerActionService.saveTimerAction(timerAction);
    }

    int getVolume(LoadType load);

    int getPlanetCapacity(PlanetPoints planetPoints);
    int getPlanetLoadVolume(PlanetPoints planetPoints);

    int getShipLoadVolume(ShipT shipT);
    LoadType getShipLoad(ShipT ship);

    LoadType getToLoad(JSONObject jsonObject) throws JsonProcessingException;

    ShipT createShip(int level, User user);

    ShipService getShipService();
    ShipPurchase getShipPurchase();
    UserService getUserService();
    PlanetService getPlanetService();
    TimerEntityService getTimerEntityService();
    TimerActionService getTimerActionService();
    TravelRouteService getTravelRouteService();
    StringToShipTypeConverter getStringToShipTypeConverter();
    PlanetPointsService getPlanetPointsService();
}
