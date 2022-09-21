package HubertRoszyk.company.controller;

import HubertRoszyk.company.entiti_class.*;
import HubertRoszyk.company.PointGenerator;
import HubertRoszyk.company.service.PlanetPointsService;
import HubertRoszyk.company.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Controller
@RestController
public class PlanetPointsController {
    @Autowired
    private PlanetPointsService planetPointsService;

    @Autowired
    private PlanetService planetService;

    @Autowired
    private PointGenerator pointGenerator;

    @GetMapping("/planet-points-controller/planets/{planetId}")
    public PlanetPoints getPlanetPoints(@PathVariable int planetId) {
        PlanetPoints planetPoints = planetPointsService.getPointsByPlanetId(planetId);

        return planetPoints;
    }

    public void getTotalDefenceIncome(int planetId) {
        PlanetPoints planetPoints = planetPointsService.getPointsByPlanetId(planetId);

        Planet planet = planetService.getPlanetById(planetId);

        int planetDefenseIncome = planet.getDefensePointsProduce() * planet.getDefencePointsMultiplier();

        planetPoints.setDefensePointsIncome(planetDefenseIncome);

        planetPointsService.savePoints(planetPoints);
    }
    public void getTotalAttackIncome(int planetId) {
        PlanetPoints planetPoints = planetPointsService.getPointsByPlanetId(planetId);

        Planet planet = planetService.getPlanetById(planetId);

        int planetAttackPointsIncome = planet.getAttackPointsProduce() * planet.getAttackPointsMultiplier();

        planetPoints.setAttackPointsIncome(planetAttackPointsIncome);

        planetPointsService.savePoints(planetPoints);
    }
    public void getTotalIndustryPointsIncome(int planetId) {
        PlanetPoints planetPoints = planetPointsService.getPointsByPlanetId(planetId);
        Planet planet = planetService.getPlanetById(planetId);

        int planetIndustryPointsIncome = planet.getIndustryPointsProduce() * planet.getIndustryPointsMultiplier();

        planetPoints.setIndustryPointsIncome(planetIndustryPointsIncome);

        planetPointsService.savePoints(planetPoints);
    }
}
