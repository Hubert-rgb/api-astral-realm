package HubertRoszyk.company.strategy.updatePointsProduceStrategy;

import HubertRoszyk.company.controller.PlanetPointsController;
import HubertRoszyk.company.entiti_class.Planet;
import HubertRoszyk.company.enumTypes.BuildingType;
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
    public void update(BuildingType buildingType, int planetId) {
        Planet planet = planetService.getPlanetById(planetId);

        double gotIndustryPoints = planet.getIndustryPointsProduce();
        int producesPoints = buildingType.getVolume();

        double setIndustryPoints = gotIndustryPoints + producesPoints;
        planet.setIndustryPointsProduce(setIndustryPoints);

        planetService.savePlanet(planet);

        planetPointsController.getTotalIndustryPointsIncome(planet.getId());
    }
}
