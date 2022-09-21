package HubertRoszyk.company.controller;

import HubertRoszyk.company.entiti_class.Galaxy;
import HubertRoszyk.company.entiti_class.Planet;
import HubertRoszyk.company.entiti_class.GalaxyPoints;
import HubertRoszyk.company.entiti_class.User;
import HubertRoszyk.company.PointGenerator;
import HubertRoszyk.company.service.PlanetService;
import HubertRoszyk.company.service.GalaxyPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


@Controller
@RestController
public class GalaxyPointsController {
    @Autowired
    private GalaxyPointsService galaxyPointsService;

    @Autowired
    private PlanetService planetService;

    @Autowired
    private PointGenerator pointGenerator;

    @GetMapping("/galaxy-points-controller/users/{userId}/galaxies/{galaxyId}")
    public GalaxyPoints getGalaxyPoints(@PathVariable int userId, @PathVariable int galaxyId) {
        GalaxyPoints galaxyPoints = galaxyPointsService.getPointsByUserIdAndGalaxyId(userId, galaxyId);
        return galaxyPoints;
    }

    public void createGalaxyPoints(User user, Galaxy galaxy) {
        GalaxyPoints galaxyPoints = new GalaxyPoints(user, galaxy);
        galaxyPointsService.savePoints(galaxyPoints);

        //getTotalFactoryIncome(user.getId(), galaxy.getId());

        pointGenerator.generatePoints();
    }
    public void getTotalSciencePointsIncome(int userId, int galaxyId) {
        GalaxyPoints galaxyPoints = galaxyPointsService.getPointsByUserIdAndGalaxyId(userId, galaxyId);
        Set<Planet> userPlanets = planetService.getPlanetsByUserIdAndGalaxyId(userId, galaxyId);

        galaxyPoints.setSciencePointsIncome(0);

        int currentSciencePointsIncome = 0;
        if (userPlanets != null) {
            for (Planet planet: userPlanets) {
                currentSciencePointsIncome += planet.getSciencePointsMultiplier() * planet.getSciencePointsProduce();
            }
        }
        galaxyPoints.setSciencePointsIncome(currentSciencePointsIncome);

        galaxyPointsService.updatePoints(galaxyPoints);
    }
}
