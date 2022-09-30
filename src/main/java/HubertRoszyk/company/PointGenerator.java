package HubertRoszyk.company;

import HubertRoszyk.company.controller.BuildingsController;
import HubertRoszyk.company.controller.PlanetPointsController;
import HubertRoszyk.company.entiti_class.*;
import HubertRoszyk.company.configuration.GameProperties;
import HubertRoszyk.company.service.BuildingService;
import HubertRoszyk.company.service.PlanetPointsService;
import HubertRoszyk.company.service.GalaxyPointsService;
import HubertRoszyk.company.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class PointGenerator {
    @Autowired
    GalaxyPointsService galaxyPointsService;

    @Autowired
    PlanetPointsService planetPointsService;

    @Autowired
    GameProperties gameProperties;

    @Autowired
    PlanetPointsController planetPointsController;


    /*private static PointGenerator instance;
    public static PointGenerator getInstance(){
        if (instance == null){
            instance = new PointGenerator();
        }
        return instance;
    }*/
    //@EventListener(ApplicationReadyEvent.class)
    public void generatePoints() {

        List<GalaxyPoints> galaxyPointsList = galaxyPointsService.getPointsList(); //both by galaxy
        List<PlanetPoints> planetPointsList = planetPointsService.getPlanetPointsList();
        for (GalaxyPoints galaxyPoints : galaxyPointsList) {
            double gotSciencePoints = galaxyPoints.getSciencePoints();

            double setSciencePoints = gotSciencePoints + galaxyPoints.getSciencePointsIncome();

            galaxyPoints.setSciencePoints(setSciencePoints);

            galaxyPointsService.updatePoints(galaxyPoints);
        }

        for (PlanetPoints planetPoints : planetPointsList) {
            double gotDefencePoints = planetPoints.getDefensePoints();
            double gotAttackPoints = planetPoints.getAttackPoints();
            double gotIndustryPoints = planetPoints.getIndustryPoints();

            double setDefencePoints = gotDefencePoints + planetPoints.getDefensePointsIncome();
            double setAttackPoints = gotAttackPoints + planetPoints.getAttackPointsIncome();
            double setIndustryPoints;

            //planetPointsController.getTotalStorageSize(planetPoints.getPlanet().getId());
            if (planetPoints.getTotalStorageSize() < gotIndustryPoints + planetPoints.getIndustryPointsIncome()){
                setIndustryPoints = planetPoints.getTotalStorageSize();
            } else {
                setIndustryPoints = gotIndustryPoints + planetPoints.getIndustryPointsIncome();
            }

            planetPoints.setDefensePoints(setDefencePoints);
            planetPoints.setAttackPoints(setAttackPoints);
            planetPoints.setIndustryPoints(setIndustryPoints);

            planetPointsService.savePoints(planetPoints);
        }
        // System.out.println("wygenerowano");
    }
}
