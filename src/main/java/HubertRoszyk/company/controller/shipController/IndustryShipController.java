package HubertRoszyk.company.controller.shipController;

import HubertRoszyk.company.controller.purchaseController.ShipPurchase;
import HubertRoszyk.company.converters.StringToShipTypeConverter;
import HubertRoszyk.company.entiti_class.*;
import HubertRoszyk.company.entiti_class.ship.IndustryShip;
import HubertRoszyk.company.enumStatus.ShipLoadStatus;
import HubertRoszyk.company.enumStatus.ShipStatus;
import HubertRoszyk.company.enumTypes.ShipType;
import HubertRoszyk.company.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/industry-ship-controller")
public class IndustryShipController implements ShipControllerInterface<IndustryShip, Integer>{
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
    ShipPurchase shipPurchase;

    @Autowired
    TimerEntityService timerEntityService;

    @Autowired
    TimerActionService timerActionService;

    @Override
    public IndustryShip createShip(int level, User user) {
        return new IndustryShip(ShipType.INDUSTRY_CARGO, level, user);
    }

    /*@Override
    public ShipLoadStatus loadShip(int shipId, JSONObject jsonObject){
        IndustryShip ship = (IndustryShip) shipService.getShipById(shipId);
        int volume = (int) jsonObject.get("volume");

        int planetId = ship.getTravelRoute().get(ship.getTravelRoute().size() - 1).getArrivalPlanet().getId();
        PlanetPoints planetPoints = planetPointsService.getPointsByPlanetId(planetId);

        int capacity = ship.getShipCapacity();
        double gotLoad = ship.getShipLoad();

        //volume <= industry points

        if(capacity >= gotLoad + volume && ship.getShipStatus().equals(ShipStatus.DOCKED)) {


            return ShipLoadStatus.EVERYTHING_LOADED;
        } else if (capacity == gotLoad) {
            return ShipLoadStatus.NOTHING_LOAD;
        } else if (ship.getShipStatus().equals(ShipStatus.TRAVELING)) {
            return ShipLoadStatus.TRAVELLING;
        } else if (ship.getShipStatus().equals(ShipStatus.IN_BUILD)) {
            return ShipLoadStatus.IN_BUILD;
        } else {
            double notLoaded = gotLoad + volume - capacity;
            double setLoad = gotLoad + volume - notLoaded;

            ship.setShipLoad(setLoad);
            shipService.saveShip(ship);

            double gotIndustryPoints = planetPoints.getIndustryPoints();
            double setIndustryPoints = gotIndustryPoints - volume + notLoaded;
            planetPoints.setIndustryPoints(setIndustryPoints);
            planetPointsService.savePoints(planetPoints);

            return ShipLoadStatus.NOT_EVERYTHING_LOAD;
        }
    }*/

    @Override
    public void executeLoad(IndustryShip ship, Integer load, PlanetPoints planetPoints) {
        double shipLoad = ship.getShipLoad();
        double setLoad = shipLoad + load;

        ship.setShipLoad(setLoad);
        shipService.saveShip(ship);

        double gotIndustryPoints = planetPoints.getIndustryPoints();
        double setIndustryPoints = gotIndustryPoints - load;
        planetPoints.setIndustryPoints(setIndustryPoints);
        planetPointsService.savePoints(planetPoints);
    }

    @Override
    public int getVolume(Integer load) {
        return load;
    }

    @Override
    public int getLoad(IndustryShip industryShip) {
        return (int) industryShip.getShipLoad();
    }

    @Override
    public Integer getLoad(JSONObject jsonObject) throws JsonProcessingException {
        return (int) jsonObject.get("volume");
    }

    @Override
   @PutMapping("unload/{volume}")
    public ShipLoadStatus unloadShip(int shipId, JSONObject jsonObject){
        IndustryShip ship = (IndustryShip) shipService.getShipById(shipId);
        int volume = (int) jsonObject.get("volume");

        int planetId = ship.getTravelRoute().get(ship.getTravelRoute().size() - 1).getArrivalPlanet().getId();
        PlanetPoints planetPoints = planetPointsService.getPointsByPlanetId(planetId);

        int capacity = planetPoints.getTotalStorageSize();
        double gotIndustryPoints = planetPoints.getIndustryPoints();

        double gotLoad = ship.getShipLoad();

        if(capacity >= gotIndustryPoints + volume && ship.getShipStatus().equals(ShipStatus.DOCKED)) {
            double setLoad = gotLoad - volume;

            ship.setShipLoad(setLoad);
            shipService.saveShip(ship);

            double setIndustryPoints = gotIndustryPoints + volume;
            planetPoints.setIndustryPoints(setIndustryPoints);
            planetPointsService.savePoints(planetPoints);

            return ShipLoadStatus.EVERYTHING_LOADED;
        } else if (capacity == gotLoad) {
            return ShipLoadStatus.NOTHING_LOAD;
        } else if (ship.getShipStatus().equals(ShipStatus.TRAVELING)) {
            return ShipLoadStatus.TRAVELLING;
        } else if (ship.getShipStatus().equals(ShipStatus.IN_BUILD)) {
            return ShipLoadStatus.IN_BUILD;
        } else {
            double notLoaded = gotIndustryPoints + volume - capacity;
            double setLoad = gotLoad - volume + notLoaded;

            ship.setShipLoad(setLoad);
            shipService.saveShip(ship);

            double setIndustryPoints = gotIndustryPoints + volume - notLoaded;
            planetPoints.setIndustryPoints(setIndustryPoints);
            planetPointsService.savePoints(planetPoints);

            return ShipLoadStatus.NOT_EVERYTHING_LOAD;
        }
    }
    /*
    @PutMapping("ship-controller/ship/{shipId}/planet/{destinationPlanetId}")
    public TravelRoute sendShip(@PathVariable int shipId, @PathVariable int destinationPlanetId){
        Ship<Double> ship = shipService.getShipById(shipId);
        Planet destinationPlanet = planetService.getPlanetById(destinationPlanetId);
        Planet departurePlanet = ship.getTravelRoute().get(ship.getTravelRoute().size()-1).getArrivalPlanet();

        if(ship.getShipStatus().equals(ShipStatus.DOCKED)) {
            ship.setShipStatus(ShipStatus.TRAVELING);

            TravelRoute travelRoute = new TravelRoute(departurePlanet, destinationPlanet, ship, timerEntityService);

            //TODO if it's army cargo it should be after flight time
            changeShipHarbour(departurePlanet.getId(), destinationPlanetId);

            shipService.saveShip(ship);
            travelRouteService.saveTravelRoutes(travelRoute);

            *//** timer task*//*
            TimerEntity timerEntity = timerEntityService.getTimerEntityByGalaxyId(destinationPlanet.getGalaxy().getId());

            TimerAction timerAction = new TimerAction(TimerActionType.TRAVEL, travelRoute.getRouteEndingCycle(), ship.getId(), timerEntity);

            timerEntityService.saveTimerEntity(timerEntity);
            timerActionService.saveTimerAction(timerAction);

            return travelRoute;
        } else {
            //returning status (travelling, in build, done)
            return null;
        }
    }
    @GetMapping("ship-controller/ship-types")
    public List<Enum> getShipTypes(){
        List<Enum> shipTypesEnumValues = new ArrayList<Enum>(EnumSet.allOf(ShipType.class));
        return shipTypesEnumValues;
    }
    *//*@GetMapping("ship-controller/planet/{planetId}")
    public List<Ship> getShipsByPlanetId(@PathVariable int planetId){

    }*//*
    @GetMapping("ship-controller/ship")
    public List<Ship> getShips(){
        return shipService.getShipsList();
    }
    private void changeShipHarbour(int departurePlanetId, int destinationPlanetId) {
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
    private void loadIndustryShip(){

    }
    private void unLoadIndustryShip(){

    }
    private void loadAttackShip(){

    }
    private void unLoadAttackShip(){

    }*/
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
