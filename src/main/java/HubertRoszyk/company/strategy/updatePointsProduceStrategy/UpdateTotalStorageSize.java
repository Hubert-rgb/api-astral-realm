package HubertRoszyk.company.strategy.updatePointsProduceStrategy;

import HubertRoszyk.company.controller.PlanetPointsController;
import HubertRoszyk.company.entiti_class.PlanetPoints;
import HubertRoszyk.company.enumTypes.BuildingType;
import HubertRoszyk.company.service.PlanetPointsService;

public class UpdateTotalStorageSize implements UpdatePointsProduceStrategy{
    PlanetPointsService planetPointsService;

    PlanetPointsController planetPointsController;

    public UpdateTotalStorageSize(PlanetPointsService planetPointsService, PlanetPointsController planetPointsController){
        this.planetPointsService = planetPointsService;
        this.planetPointsController = planetPointsController;
    }
    @Override
    public void update(BuildingType buildingType, int planetId) {
        PlanetPoints planetPoints = planetPointsService.getPointsByPlanetId(planetId);

        int gotStorageSize = planetPoints.getTotalStorageSize();
        System.out.println(gotStorageSize);
        int updatedSize = buildingType.getVolume();

        int setStorageSize = gotStorageSize + updatedSize;
        planetPoints.setTotalStorageSize(setStorageSize);

        planetPointsService.savePoints(planetPoints);

        //planetPointsController.getTotalStorageSize(planetId);
    }
}
