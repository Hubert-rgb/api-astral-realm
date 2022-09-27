package HubertRoszyk.company.Strategy.update_points_produce_strategy;

import HubertRoszyk.company.entiti_class.Building;
import HubertRoszyk.company.entiti_class.Planet;
import HubertRoszyk.company.controller.PlanetPointsController;
import HubertRoszyk.company.service.PlanetService;

public class UpdateDefensePointsProduce implements UpdatePointsProduceStrategy {
    PlanetService planetService;
    PlanetPointsController planetPointsController;

    public UpdateDefensePointsProduce(PlanetService planetService, PlanetPointsController planetPointsController) {
        this.planetService = planetService;
        this.planetPointsController = planetPointsController;
    }

    @Override
    public void update(Building building) {
        Planet planet = planetService.getPlanetById(building.getPlanet().getId());

        int gotDefensePoints = planet.getDefensePointsProduce();
        int producesPoints = building.getBuildingType().getVolume();

        int setDefensePoints = gotDefensePoints + producesPoints;
        planet.setDefensePointsProduce(setDefensePoints);

        planetService.savePlanet(planet);

        planetPointsController.getTotalDefenceIncome(building.getPlanet().getId());
    }
}
