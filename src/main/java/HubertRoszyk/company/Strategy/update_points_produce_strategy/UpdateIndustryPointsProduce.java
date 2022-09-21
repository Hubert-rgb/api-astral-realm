package HubertRoszyk.company.Strategy.update_points_produce_strategy;

import HubertRoszyk.company.controller.PlanetPointsController;
import HubertRoszyk.company.entiti_class.Building;
import HubertRoszyk.company.entiti_class.Planet;
import HubertRoszyk.company.controller.GalaxyPointsController;
import HubertRoszyk.company.service.PlanetService;

public class UpdateIndustryPointsProduce implements UpdatePointsProduceStrategy {
    //@Autowired
    private PlanetService planetService;
    private final PlanetPointsController planetPointsController;

    public UpdateIndustryPointsProduce(PlanetService planetService, PlanetPointsController planetPointsController) {
        this.planetService = planetService;
        this.planetPointsController = planetPointsController;
    }

    @Override
    public void update(Building building) {
        Planet planet = planetService.getPlanetById(building.getPlanet().getId());

        int gotIndustryPoints = planet.getIndustryPointsProduce();
        int producesPoints = building.getBuildingType().getPointsProduces();

        int setIndustryPoints = gotIndustryPoints + producesPoints;
        planet.setIndustryPointsProduce(setIndustryPoints);

        planetService.savePlanet(planet);

        planetPointsController.getTotalIndustryPointsIncome(planet.getId());
    }
}
