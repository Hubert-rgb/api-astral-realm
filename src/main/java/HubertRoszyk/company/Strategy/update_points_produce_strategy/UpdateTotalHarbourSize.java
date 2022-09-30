package HubertRoszyk.company.Strategy.update_points_produce_strategy;

import HubertRoszyk.company.controller.PlanetPointsController;
import HubertRoszyk.company.entiti_class.Building;
import HubertRoszyk.company.entiti_class.PlanetPoints;
import HubertRoszyk.company.enumTypes.BuildingType;
import HubertRoszyk.company.service.PlanetPointsService;

public class UpdateTotalHarbourSize implements UpdatePointsProduceStrategy{
    PlanetPointsService planetPointsService;

    PlanetPointsController planetPointsController;

    public UpdateTotalHarbourSize(PlanetPointsService planetPointsService, PlanetPointsController planetPointsController){
        this.planetPointsService = planetPointsService;
        this.planetPointsController = planetPointsController;
    }
    @Override
    public void update(BuildingType buildingType, int planetId) {
        PlanetPoints planetPoints = planetPointsService.getPointsByPlanetId(planetId);

        int gotHarbourSize = planetPoints.getTotalHarbourSize();
        int updatedSize = buildingType.getVolume();

        int setHarbourSize = gotHarbourSize + updatedSize;
        planetPoints.setTotalStorageSize(setHarbourSize);

        planetPointsService.savePoints(planetPoints);

        planetPointsController.getTotalHarbourSize(planetId);
    }
}
