package HubertRoszyk.company.controller;

import HubertRoszyk.company.entiti_class.*;
import HubertRoszyk.company.RandomDraw;
import HubertRoszyk.company.enumTypes.PeriodType;
import HubertRoszyk.company.enumTypes.PlanetType;
import HubertRoszyk.company.service.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class GalaxyController {
    @Autowired
    PlanetService planetService;

    @Autowired
    GalaxyService galaxyService;

    @Autowired
    UserService userService;

    @Autowired
    PlanetPointsService planetPointsService;

    @Autowired
    TimerEntityService timerEntityService;

    @Autowired
    TimerEntityController timerEntityController;

    @Autowired
    Binder binder;

    @PostMapping("/galaxy-controller/users/{userId}/galaxies/{galaxyId}")
    public Set<Planet> connectToGalaxy(@PathVariable("userId") int userId, @PathVariable("galaxyId") int galaxyId) {

        User user = userService.getUserById(userId);

        Set<Planet> galaxyPlanets = planetService.getPlanetsByGalaxy(galaxyId);

        //nie wiem czy nie lepiej po prostu zawsze bindować
        for (GalaxyPoints galaxyPoints : user.getPoints()) {
            if (galaxyPoints.getGalaxy().getId() == galaxyId) {
                return galaxyPlanets;
            }
        }
        Galaxy galaxy = binder.bindGalaxyToUser(userId, galaxyId);
        if (galaxy == null) {
            return null;
        } else {
            return galaxyPlanets;
        }
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500/", allowedHeaders = "*")
    @PostMapping("/galaxy-controller/galaxies")
    public List<Planet> galaxyInit(@RequestBody JSONObject jsonInput){ //do przeanalizowania bo nie wygląda za ładnie
        int maximalUserNumber = (int) jsonInput.get("maximalUserNumber");
        String galaxyName = (String) jsonInput.get("galaxyName");
        String periodTypeString = (String) jsonInput.get("periodType");

        PeriodType periodType = PeriodType.valueOf(periodTypeString);
        //TODO period as Enum

        Galaxy galaxy = new Galaxy(maximalUserNumber, galaxyName, periodType);
        galaxyService.saveGalaxy(galaxy);

        TimerEntity timerEntity = new TimerEntity(0, LocalDateTime.now(), galaxy);
        timerEntityService.saveTimerEntity(timerEntity);
        timerEntityController.timerExecution(galaxy.getId());

        List<Planet> planets = new ArrayList<>();

        //crating galaxy with every type of planet
        for (PlanetType planetType : PlanetType.values()) {
            planets.addAll(createPlanet(planetType, galaxy));
        }

        planetService.savePlanetsList(planets);

        return planets;
    }
    /** creating planets */
    private List<Planet> createPlanet(PlanetType planetType, Galaxy galaxy) {
        List<Planet> planets = new ArrayList<>();

        int planetsNum = galaxy.getMaximalUserNumber() + 1;
        int randomVariablesSum = planetType.getRandomVariablesSum();

        for(int i = 0; i < planetsNum; i++){
            int size = RandomDraw.sizeDraw(planetType);

            //Draw a size, industry multiplier and science multiplier

            int localRandomVariablesSum = randomVariablesSum - size / 2;

            int industryPointsMultiplier = RandomDraw.industryPointsMultiplierDraw(localRandomVariablesSum);
            int sciencePointsMultiplier = localRandomVariablesSum - industryPointsMultiplier;

            //TODO to recreate (radial positioning)
            PlanetLocation planetLocation = RandomDraw.locationDraw();

            //Creating planet

            Planet planet = new Planet(
                    planetType,
                    industryPointsMultiplier,
                    sciencePointsMultiplier,
                    size,
                    planetLocation.getXLocation(),
                    planetLocation.getYLocation(),
                    galaxy
            );
            PlanetPoints planetPoints = new PlanetPoints(planet);

            Map<Integer, Integer> army = ArmyController.getEmptyArmy();
            double armySizeMultiplier = RandomDraw.armyDivisionNumberDraw();
            army.put(1, (int) Math.floor(armySizeMultiplier * planetType.getDefaultArmySize()));

            planetPoints.setArmy(army);
            planetService.savePlanet(planet);
            planetPointsService.savePoints(planetPoints);

            planets.add(planet);
        }

        return planets;
    }
    @GetMapping("/galaxy-controller/galaxies")
    public List<Galaxy> getGalaxies() {
        List<Galaxy> galaxies = galaxyService.getAllGalaxies();
        return galaxies;
    }
    @GetMapping("/galaxy-controller/galaxies/{galaxyId}")
    Set<Planet> getGalaxyById(@PathVariable int galaxyId) {
        Galaxy galaxy = galaxyService.getGalaxyById(galaxyId);
        Set<Planet> planets = null;
        try {
            planets = galaxy.getEnrolledPlanets();
        } catch (Exception exception) {
            System.out.println(exception);
        }

        //return planets
        return planets;
    }
}

