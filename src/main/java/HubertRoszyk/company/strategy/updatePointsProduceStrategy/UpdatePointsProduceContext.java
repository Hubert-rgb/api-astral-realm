package HubertRoszyk.company.strategy.updatePointsProduceStrategy;

import HubertRoszyk.company.enumTypes.BuildingType;

public class UpdatePointsProduceContext {
    private UpdatePointsProduceStrategy strategy;

    public void setStrategy(UpdatePointsProduceStrategy strategy) {
        this.strategy = strategy;
    }

    public void executeStrategy(BuildingType buildingType, int planetId) {
        strategy.update(buildingType, planetId);
    }
}
