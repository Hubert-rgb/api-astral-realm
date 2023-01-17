package HubertRoszyk.company.strategy.scienceCardExecutionStrategy;

import HubertRoszyk.company.entiti_class.GalaxyPoints;
import HubertRoszyk.company.service.GalaxyPointsService;

public class BuildingFasterCardExecution implements ScienceCardExecutionStrategy{
    @Override
    public void execute(GalaxyPointsService galaxyPointsService, int galaxyId, int userId) {
        //changes galaxy points multiplier for building time
    }
}
