/*
package HubertRoszyk.company.Strategy.update_points_produce_strategy;

import HubertRoszyk.company.entiti_class.Building;
import HubertRoszyk.company.entiti_class.Planet;
import HubertRoszyk.company.controller.PlanetPointsController;
import HubertRoszyk.company.enumTypes.BuildingType;
import HubertRoszyk.company.service.PlanetService;

public class UpdateAttackPointsProduce implements UpdatePointsProduceStrategy {
    PlanetService planetService;
    PlanetPointsController planetPointsController;

    public UpdateAttackPointsProduce(PlanetService planetService, PlanetPointsController planetPointsController) {
        this.planetService = planetService;
        this.planetPointsController = planetPointsController;
    }

    @Override
    public void update(BuildingType buildingType, int planetId) {
        Planet planet = planetService.getPlanetById(planetId);

        int gotAttackPoints = planet.getAttackPointsProduce();
        int producesPoints = buildingType.getVolume();

        int setAttackPoints = gotAttackPoints + producesPoints;
        planet.setAttackPointsProduce(setAttackPoints);

        planetService.savePlanet(planet);

        planetPointsController.getTotalAttackIncome(planetId);
    }
}
*/
