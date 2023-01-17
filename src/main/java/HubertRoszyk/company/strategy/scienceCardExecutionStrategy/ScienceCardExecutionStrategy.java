package HubertRoszyk.company.strategy.scienceCardExecutionStrategy;

import HubertRoszyk.company.entiti_class.GalaxyPoints;
import HubertRoszyk.company.service.GalaxyPointsService;

public interface ScienceCardExecutionStrategy {
    void execute(GalaxyPointsService galaxyPointsService, int galaxyId, int userId);
}
