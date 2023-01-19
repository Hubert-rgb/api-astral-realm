package HubertRoszyk.company.strategy.scienceCardExecutionStrategy.economyCards;

import HubertRoszyk.company.entiti_class.GalaxyPoints;
import HubertRoszyk.company.service.GalaxyPointsService;
import HubertRoszyk.company.strategy.scienceCardExecutionStrategy.ScienceCardExecutionStrategy;

public class IndustryCargoShipCardExecution implements ScienceCardExecutionStrategy {

    @Override
    public void execute(GalaxyPointsService galaxyPointsService, int galaxyId, int userId) {
        GalaxyPoints galaxyPoints = galaxyPointsService.getPointsByUserIdAndGalaxyId(galaxyId, userId);
        galaxyPoints.canBuildIndustryShip(true);
        galaxyPointsService.savePoints(galaxyPoints);
    }
}
