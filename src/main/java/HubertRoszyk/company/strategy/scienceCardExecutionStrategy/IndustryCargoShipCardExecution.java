package HubertRoszyk.company.strategy.scienceCardExecutionStrategy;

import HubertRoszyk.company.entiti_class.GalaxyPoints;
import HubertRoszyk.company.service.GalaxyPointsService;

public class IndustryCargoShipCardExecution implements ScienceCardExecutionStrategy{

    @Override
    public void execute(GalaxyPointsService galaxyPointsService, int galaxyId, int userId) {
        GalaxyPoints galaxyPoints = galaxyPointsService.getPointsByUserIdAndGalaxyId(galaxyId, userId);
        galaxyPoints.canBuildIndustryShip(true);
        galaxyPointsService.savePoints(galaxyPoints);
    }
}
