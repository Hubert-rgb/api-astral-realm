package HubertRoszyk.company.Strategy.update_points_produce_strategy;

import HubertRoszyk.company.controller.PlanetPointsController;
import HubertRoszyk.company.entiti_class.Building;
import HubertRoszyk.company.entiti_class.Planet;
import HubertRoszyk.company.entiti_class.PlanetPoints;
import HubertRoszyk.company.service.PlanetPointsService;
import HubertRoszyk.company.service.PlanetService;

public class UpdateTotalStorageSize implements UpdatePointsProduceStrategy{
    PlanetPointsService planetPointsService;

    PlanetPointsController planetPointsController;

    public UpdateTotalStorageSize(PlanetPointsService planetPointsService, PlanetPointsController planetPointsController){
        this.planetPointsService = planetPointsService;
        this.planetPointsController = planetPointsController;
    }
    @Override
    public void update(Building building) {
        PlanetPoints planetPoints = planetPointsService.getPointsByPlanetId(building.getPlanet().getId());

        int gotStorageSize = planetPoints.getTotalStorageSize();
        int updatedSize = building.getBuildingType().getVolume();

        int setStorageSize = gotStorageSize + updatedSize;
        planetPoints.setTotalStorageSize(setStorageSize);

        planetPointsService.savePoints(planetPoints);

        planetPointsController.getTotalStorageSize(building.getPlanet().getId());
    }
}
