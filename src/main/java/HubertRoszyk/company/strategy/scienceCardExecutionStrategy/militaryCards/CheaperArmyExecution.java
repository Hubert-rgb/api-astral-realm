package HubertRoszyk.company.strategy.scienceCardExecutionStrategy.militaryCards;

import HubertRoszyk.company.entiti_class.GalaxyPoints;
import HubertRoszyk.company.service.GalaxyPointsService;
import HubertRoszyk.company.strategy.scienceCardExecutionStrategy.ScienceCardExecutionStrategy;

public class CheaperArmyExecution implements ScienceCardExecutionStrategy {
    @Override
    public void execute(GalaxyPointsService galaxyPointsService, int galaxyId, int userId) {
        GalaxyPoints galaxyPoints = galaxyPointsService.getPointsByUserIdAndGalaxyId(galaxyId, userId);
        galaxyPoints.setArmyCostSubtrahend(5);
        galaxyPointsService.savePoints(galaxyPoints);
    }
}
