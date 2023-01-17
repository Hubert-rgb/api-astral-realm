package HubertRoszyk.company.strategy.scienceCardExecutionStrategy;

import HubertRoszyk.company.entiti_class.GalaxyPoints;
import HubertRoszyk.company.service.GalaxyPointsService;
import HubertRoszyk.company.strategy.updatePointsProduceStrategy.UpdatePointsProduceStrategy;

public class ScienceCardExecutionContext {
    private ScienceCardExecutionStrategy strategy;

    public void setStrategy(ScienceCardExecutionStrategy strategy){
        this.strategy =  strategy;
    }

    public void executeStrategy(GalaxyPointsService galaxyPointsService, int galaxyId, int userId){
        strategy.execute(galaxyPointsService, galaxyId, userId);
    }
}
