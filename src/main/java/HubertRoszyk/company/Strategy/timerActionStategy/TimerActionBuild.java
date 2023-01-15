package HubertRoszyk.company.Strategy.timerActionStategy;

import HubertRoszyk.company.controller.industryPurchaseController.BuildingPurchase;
import HubertRoszyk.company.entiti_class.Building;
import HubertRoszyk.company.entiti_class.TimerAction;
import HubertRoszyk.company.enumStatus.BuildingStatus;
import HubertRoszyk.company.service.BuildingService;

public class TimerActionBuild implements TimerActionStrategy{
    private BuildingPurchase buildingPurchase;
    private BuildingService buildingService;

    public TimerActionBuild(BuildingPurchase buildingPurchase, BuildingService buildingService){
        this.buildingPurchase = buildingPurchase;
        this.buildingService = buildingService;
    }
    @Override
    public void executeAction(TimerAction timerAction) {
        int buildingId = timerAction.getExecutionId();
        Building building = buildingService.getBuildingById(buildingId);

        buildingPurchase.updatePointsIncome(building.getBuildingType(), building.getPlanet().getId());
        building.setBuildingStatus(BuildingStatus.BUILT);

        buildingService.saveBuilding(building);
    }
}
