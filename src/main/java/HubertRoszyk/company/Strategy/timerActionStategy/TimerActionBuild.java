package HubertRoszyk.company.Strategy.timerActionStategy;

import HubertRoszyk.company.controller.IndustryPointsController;
import HubertRoszyk.company.entiti_class.Building;
import HubertRoszyk.company.entiti_class.TimerAction;
import HubertRoszyk.company.service.BuildingService;

public class TimerActionBuild implements TimerActionStrategy{
    private IndustryPointsController industryPointsController;
    private BuildingService buildingService;

    public TimerActionBuild(IndustryPointsController industryPointsController, BuildingService buildingService){
        this.industryPointsController = industryPointsController;
        this.buildingService = buildingService;
    }
    @Override
    public void executeAction(TimerAction timerAction) {
        int buildingId = timerAction.getExecutionId();
        Building building = buildingService.getBuildingById(buildingId);

        industryPointsController.updatePointsIncome(building.getBuildingType(), building.getPlanet().getId());
    }
}
