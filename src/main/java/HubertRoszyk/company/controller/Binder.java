package HubertRoszyk.company.controller;

import HubertRoszyk.company.entiti_class.Galaxy;
import HubertRoszyk.company.entiti_class.Planet;
import HubertRoszyk.company.entiti_class.User;
import HubertRoszyk.company.service.GalaxyService;
import HubertRoszyk.company.service.PlanetService;
import HubertRoszyk.company.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://127.0.0.1:5500/", allowedHeaders = "*")
@RestController
@Controller
public class Binder {
    @Autowired
    UserService userService;

    @Autowired
    PlanetService planetService;

    @Autowired
    GalaxyService galaxyService;

    @Autowired
    GalaxyPointsController galaxyPointsController;

    @Autowired
    PlanetPointsController planetPointsController;

    @PostMapping("/binder/user/{userId}/planet/{planetId}")
    Planet bindPlanetToUser(@PathVariable int userId, @PathVariable int planetId) {

        User user = userService.getUserById(userId);
        Planet planet = planetService.getPlanetById(planetId);

        if (planet == null || user == null) {
            return null;
        } else {
            planet.asignUser(user);

            planetService.savePlanet(planet); //najpierw trzeba zapisać planetę usera a potem szukać jej punkty
            planetPointsController.getTotalIndustryPointsIncome(planet.getId());

            return planet;
        }
    }
    public Galaxy bindGalaxyToUser(int userId, int galaxyId) {
        User user = userService.getUserById(userId);
        Galaxy galaxy = galaxyService.getGalaxyById(galaxyId);

        if (user == null || galaxy == null) {
            return null;
        } else {
            galaxy.addUser();

            galaxyPointsController.createGalaxyPoints(user, galaxy);

            return galaxyService.saveGalaxy(galaxy);
        }
    }
}
