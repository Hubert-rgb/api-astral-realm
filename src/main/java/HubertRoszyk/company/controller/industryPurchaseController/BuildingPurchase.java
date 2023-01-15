package HubertRoszyk.company.controller.industryPurchaseController;

import HubertRoszyk.company.Strategy.update_points_produce_strategy.*;
import HubertRoszyk.company.configuration.GameProperties;
import HubertRoszyk.company.controller.GalaxyPointsController;
import HubertRoszyk.company.controller.PlanetPointsController;
import HubertRoszyk.company.entiti_class.*;
import HubertRoszyk.company.enumStatus.BuildingStatus;
import HubertRoszyk.company.enumTypes.BuildingType;
import HubertRoszyk.company.enumTypes.TimerActionType;
import HubertRoszyk.company.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BuildingPurchase implements PurchaseInterface<Building> {
    @Autowired
    BuildingService buildingService;

    @Autowired
    GameProperties gameProperties;

    @Autowired
    PlanetPointsController planetPointsController;

    @Autowired
    GalaxyPointsController galaxyPointsController;

    @Override
    public boolean getIsEnoughSpace(Building building, Planet planet) {
        if (building.getBuildingLevel() > 1) {
            return true;
        } else {
            return planet.getSize() > planet.getBuildingList().size();
        }
    }

    @Override
    public void saveObject(Building building, int planetId) {
        buildingService.saveBuilding(building);
    }

    @Override
    public void upgradeLevel(Building building, int setLevel, int planetId, int...amount) {
        /*int gotBuildingLevel = building.getBuildingLevel();
        int setBuildingLevel = gotBuildingLevel + 1;*/

        building.setBuildingLevel(setLevel);
        building.setBuildingStatus(BuildingStatus.IN_BUILD);

        //return setBuildingLevel;
    }

    @Override
    public boolean getIsNotOnMaximumLevel(Building building, int planetId) {
        return building.getBuildingLevel() <= building.getBuildingType().getLevelNums();
    }

    @Override
    public double getPrice(Building building, int...amount) {
        int buildingTypePrice = building.getBuildingType().getBuildingPrice();
        double costMultiplier = gameProperties.getLevelCostMultiplier() * building.getBuildingLevel();
        return buildingTypePrice * costMultiplier;
    }

    @Override
    public void executeTimerAction(Building building, int planetId, int setLevel) {
        /** timer action*/
        TimerEntity timerEntity = timerEntityService.getTimerEntityByGalaxyId(building.getPlanet().getGalaxy().getId());
        int currentCycle = timerEntity.getCyclesNum();
        int buildingEndCycle = building.getBuildingType().getConstructionCycles() + currentCycle;

        TimerAction timerAction = new TimerAction(TimerActionType.BUILDING, buildingEndCycle, building.getId(), timerEntity);

        timerEntityService.saveTimerEntity(timerEntity);
        timerActionService.saveTimerAction(timerAction);
    }

    public void updatePointsIncome(BuildingType buildingType, int planetId) {
        UpdatePointsProduceContext context = new UpdatePointsProduceContext();

        switch (buildingType) {
            case INDUSTRY -> context.setStrategy(new UpdateIndustryPointsProduce(planetService, planetPointsController));
            case SCIENCE -> context.setStrategy(new UpdateSciencePointsProduce(planetService, galaxyPointsController));
            //case DEFENSE -> context.setStrategy(new UpdateDefensePointsProduce(planetService, planetPointsController));
            //case ATTACK -> context.setStrategy(new UpdateAttackPointsProduce(planetService, planetPointsController));
            case STORAGE -> context.setStrategy(new UpdateTotalStorageSize(planetPointsService, planetPointsController));
            case SHIP_YARD -> context.setStrategy(new UpdateShipYardLevel(planetPointsService));
            case HARBOUR -> context.setStrategy(new UpdateTotalHarbourSize(planetPointsService, planetPointsController));
        }
        context.executeStrategy(buildingType, planetId);
    }

    @Autowired
    PlanetPointsService planetPointsService;

    @Autowired
    PlanetService planetService;

    @Autowired
    TimerEntityService timerEntityService;

    @Autowired
    TimerActionService timerActionService;

    @Override
    public PlanetPointsService getPlanetPointsService() {
        return planetPointsService;
    }

    @Override
    public PlanetService getPlanetService() {
        return planetService;
    }
}
