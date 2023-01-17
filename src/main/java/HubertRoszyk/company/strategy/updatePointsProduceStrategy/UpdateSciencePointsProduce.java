package HubertRoszyk.company.strategy.updatePointsProduceStrategy;

import HubertRoszyk.company.entiti_class.Planet;
import HubertRoszyk.company.controller.GalaxyPointsController;
import HubertRoszyk.company.enumTypes.BuildingType;
import HubertRoszyk.company.service.PlanetService;

public class UpdateSciencePointsProduce implements UpdatePointsProduceStrategy {
    PlanetService planetService;
    GalaxyPointsController galaxyPointsController;

    public UpdateSciencePointsProduce(PlanetService planetService, GalaxyPointsController galaxyPointsController) {
        this.planetService = planetService;
        this.galaxyPointsController = galaxyPointsController;
    }

    @Override
    public void update(BuildingType buildingType, int planetId) {
        Planet planet = planetService.getPlanetById(planetId);

        double gotSciencePoints = planet.getSciencePointsProduce();
        int producesPoints = buildingType.getVolume();

        double setSciencePoints = gotSciencePoints + producesPoints;
        planet.setSciencePointsProduce(setSciencePoints);

        planetService.savePlanet(planet);

        galaxyPointsController.getTotalSciencePointsIncome(planet.getUser().getId(), planet.getGalaxy().getId());
    }
}
