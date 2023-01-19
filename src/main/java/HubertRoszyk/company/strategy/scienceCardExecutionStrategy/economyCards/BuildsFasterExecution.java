package HubertRoszyk.company.strategy.scienceCardExecutionStrategy.economyCards;

import HubertRoszyk.company.entiti_class.GalaxyPoints;
import HubertRoszyk.company.service.GalaxyPointsService;
import HubertRoszyk.company.strategy.scienceCardExecutionStrategy.ScienceCardExecutionStrategy;

public class BuildsFasterExecution implements ScienceCardExecutionStrategy {
    @Override
    public void execute(GalaxyPointsService galaxyPointsService, int galaxyId, int userId) {
        GalaxyPoints galaxyPoints = galaxyPointsService.getPointsByUserIdAndGalaxyId(galaxyId, userId);
        galaxyPoints.setConstructionTimeSubtrahend(3);
        galaxyPointsService.savePoints(galaxyPoints);
    }
}
