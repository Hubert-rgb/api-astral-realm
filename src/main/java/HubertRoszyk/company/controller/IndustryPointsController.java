package HubertRoszyk.company.controller;

import HubertRoszyk.company.Strategy.update_points_produce_strategy.*;
import HubertRoszyk.company.configuration.GameProperties;
import HubertRoszyk.company.entiti_class.Building;
import HubertRoszyk.company.entiti_class.Planet;
import HubertRoszyk.company.entiti_class.PlanetPoints;
import HubertRoszyk.company.entiti_class.Ship;
import HubertRoszyk.company.enumStatus.PurchaseStatus;
import HubertRoszyk.company.enumTypes.BuildingType;
import HubertRoszyk.company.service.BuildingService;
import HubertRoszyk.company.service.PlanetPointsService;
import HubertRoszyk.company.service.PlanetService;
import HubertRoszyk.company.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.concurrent.Callable;

@Controller
public class IndustryPointsController {
    @Autowired
    PlanetPointsService planetPointsService;

    @Autowired
    GameProperties gameProperties;

    @Autowired
    BuildingService buildingService;

    @Autowired
    PlanetService planetService;

    @Autowired
    PlanetPointsController planetPointsController;

    @Autowired
    GalaxyPointsController galaxyPointsController;

    @Autowired
    ShipService shipService;

    public <T> PurchaseStatus executePurchase(int planetId, T object) {
        PlanetPoints planetPoints = planetPointsService.getPointsByPlanetId(planetId);
        Planet planet = planetService.getPlanetById(planetId);

        int setLevel = upgradeLevel(object);

        double price = getPrice(object);
        double gotIndustryPoints = planetPoints.getIndustryPoints();

        boolean isEnoughPoints;
        boolean isNotOnMaximumLevel;
        boolean isEnoughSpaceOnPlanet;

        isEnoughPoints = gotIndustryPoints >= price;
        isNotOnMaximumLevel = getIsNotOnMaximumLevel(object);

        if(setLevel > 1) {
            isEnoughSpaceOnPlanet = true;
        } else {
            isEnoughSpaceOnPlanet = planet.getSize() > planet.getBuildingList().size();
        }

        if (isEnoughPoints && isNotOnMaximumLevel  && isEnoughSpaceOnPlanet) {  //strategy
            double setIndustryPoints = gotIndustryPoints - price;
            planetPoints.setIndustryPoints(setIndustryPoints);

            if(object instanceof Building) {
                updatePointsIncome(((Building) object).getBuildingType(), planetId);
                //upgrade ship capacity
            }
            planetPointsService.savePoints(planetPoints);
            saveObject(object);

            return PurchaseStatus.UPGRADED;
        } else if (!isNotOnMaximumLevel) {
            return PurchaseStatus.MAX_LEVEL;
        } else if (!isEnoughSpaceOnPlanet){
            return PurchaseStatus.NOT_ENOUGH_SPACE;
        }  else {
            return PurchaseStatus.NOT_ENOUGH_POINTS;
        }
    }

    public void updatePointsIncome(BuildingType buildingType, int planetId) {
        UpdatePointsProduceContext context = new UpdatePointsProduceContext();

        switch (buildingType) {
            case INDUSTRY -> context.setStrategy(new UpdateIndustryPointsProduce(planetService, planetPointsController));
            case SCIENCE -> context.setStrategy(new UpdateSciencePointsProduce(planetService, galaxyPointsController));
            case DEFENSE -> context.setStrategy(new UpdateDefensePointsProduce(planetService, planetPointsController));
            case ATTACK -> context.setStrategy(new UpdateAttackPointsProduce(planetService, planetPointsController));
            case STORAGE -> context.setStrategy(new UpdateTotalStorageSize(planetPointsService, planetPointsController));
        }
        context.executeStrategy(buildingType, planetId);
    }
    private  <T> void saveObject(T object){
        if(object instanceof Building) {
            saveBuilding((Building) object);
        } else if (object instanceof Ship) {
            saveShip((Ship) object);
        }
    }
    private void saveShip(Ship ship){
        shipService.saveShip(ship);
    }
    private void saveBuilding(Building building){
        buildingService.saveBuilding(building);
    }
    private  <T> int upgradeLevel(T object){
        if (object instanceof Building) {
            return upgradeBuildingLevel((Building) object);
        } else if (object instanceof Ship) {
            return upgradeShipLevel((Ship) object);
        } else {
            return -1;
        }
    }

    private int upgradeBuildingLevel(Building building) {
        int gotBuildingLevel = building.getBuildingLevel();
        int setBuildingLevel = gotBuildingLevel + 1;

        building.setBuildingLevel(setBuildingLevel);

        return setBuildingLevel;
    }
    private int upgradeShipLevel(Ship ship) {
        int gotShipLevel = ship.getCapacityLevel();
        int setShipLevel = gotShipLevel + 1;

        ship.setCapacityLevel(setShipLevel);

        return setShipLevel;
    }
    private  <T> boolean getIsNotOnMaximumLevel(T object) {
        if(object instanceof Building) {
            return getBuildingIsNotOnMaximumLevel((Building) object);
        } else if (object instanceof Ship) {
            return getShipIsNotOnMaximumLevel((Ship) object);
        } else {
            return false;
        }
    }
    private boolean getBuildingIsNotOnMaximumLevel(Building building){
        return building.getBuildingLevel() < building.getBuildingType().getLevelNums();
    }
    private boolean getShipIsNotOnMaximumLevel(Ship ship) {
        return ship.getCapacityLevel() < ship.getShipType().getLevelNums();
    }

    private  <T> double getPrice(T object){
        if(object instanceof Building) {
            return getBuildingPrice((Building) object);
        } else if (object instanceof Ship) {
            return getShipPrice((Ship) object);
        } else {
            return -1;
        }
    }

    private double getBuildingPrice(Building building) {
        int buildingTypePrice = building.getBuildingType().getBuildingPrice();
        double costMultiplier = gameProperties.getLevelCostMultiplier() * building.getBuildingLevel();
        return buildingTypePrice * costMultiplier;
    }
    private double getShipPrice(Ship ship){
        int shipTypePrice = ship.getShipType().getShipPrice();
        double costMultiplier = gameProperties.getLevelCostMultiplier() * ship.getCapacityLevel();
        return shipTypePrice * costMultiplier;
    }


}
