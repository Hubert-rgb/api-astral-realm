package HubertRoszyk.company.Strategy.update_points_produce_strategy;

import HubertRoszyk.company.entiti_class.Building;
import HubertRoszyk.company.enumTypes.BuildingType;

public interface UpdatePointsProduceStrategy {

    void update(BuildingType buildingType, int planetId);
}
