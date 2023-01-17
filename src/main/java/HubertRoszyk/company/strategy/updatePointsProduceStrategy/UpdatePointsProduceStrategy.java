package HubertRoszyk.company.strategy.updatePointsProduceStrategy;

import HubertRoszyk.company.enumTypes.BuildingType;

public interface UpdatePointsProduceStrategy {

    void update(BuildingType buildingType, int planetId);
}
