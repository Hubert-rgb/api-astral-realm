package HubertRoszyk.company.strategy.scienceCardExecutionStrategy.economyCards;

import HubertRoszyk.company.entiti_class.GalaxyPoints;
import HubertRoszyk.company.service.GalaxyPointsService;
import HubertRoszyk.company.strategy.scienceCardExecutionStrategy.ScienceCardExecutionStrategy;

public class FasterIndustryShipExecution implements ScienceCardExecutionStrategy {
    @Override
    public void execute(GalaxyPointsService galaxyPointsService, int galaxyId, int userId) {
        //changes galaxy points addition for building time
        GalaxyPoints galaxyPoints = galaxyPointsService.getPointsByUserIdAndGalaxyId(galaxyId, userId);
        galaxyPoints.setIndustryShipSpeedAddition(10);
        galaxyPointsService.savePoints(galaxyPoints);
    }
}
