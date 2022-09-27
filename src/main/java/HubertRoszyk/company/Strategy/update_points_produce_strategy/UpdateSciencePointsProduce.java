package HubertRoszyk.company.Strategy.update_points_produce_strategy;

import HubertRoszyk.company.entiti_class.Building;
import HubertRoszyk.company.entiti_class.Planet;
import HubertRoszyk.company.controller.GalaxyPointsController;
import HubertRoszyk.company.service.PlanetService;

public class UpdateSciencePointsProduce implements UpdatePointsProduceStrategy {
    PlanetService planetService;
    GalaxyPointsController galaxyPointsController;

    public UpdateSciencePointsProduce(PlanetService planetService, GalaxyPointsController galaxyPointsController) {
        this.planetService = planetService;
    }

    @Override
    public void update(Building building) {
        Planet planet = planetService.getPlanetById(building.getPlanet().getId());

        int gotSciencePoints = planet.getSciencePointsProduce();
        int producesPoints = building.getBuildingType().getVolume();

        int setSciencePoints = gotSciencePoints + producesPoints;
        planet.setSciencePointsProduce(setSciencePoints);

        planetService.savePlanet(planet);

        galaxyPointsController.getTotalSciencePointsIncome(building.getPlanet().getUser().getId(), building.getPlanet().getGalaxy().getId());
    }
}
