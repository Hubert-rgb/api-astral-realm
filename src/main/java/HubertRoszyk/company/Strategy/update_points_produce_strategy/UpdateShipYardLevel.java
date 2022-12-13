package HubertRoszyk.company.Strategy.update_points_produce_strategy;

import HubertRoszyk.company.entiti_class.PlanetPoints;
import HubertRoszyk.company.enumTypes.BuildingType;
import HubertRoszyk.company.service.PlanetPointsService;

public class UpdateShipYardLevel implements UpdatePointsProduceStrategy{
    private PlanetPointsService planetPointsService;
    public UpdateShipYardLevel(PlanetPointsService planetPointsService){
        this.planetPointsService = planetPointsService;
    }
    @Override
    public void update(BuildingType buildingType, int planetId) {
        PlanetPoints planetPoints = planetPointsService.getPointsByPlanetId(planetId);

        int gotShipYardLevel = planetPoints.getShipYardLevel();
        int setShipYardLevel = gotShipYardLevel + 1;

        planetPoints.setShipYardLevel(setShipYardLevel);

        planetPointsService.savePoints(planetPoints);

    }
}
