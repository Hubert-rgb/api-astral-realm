package HubertRoszyk.company;

import HubertRoszyk.company.entiti_class.Battle;
import HubertRoszyk.company.entiti_class.Planet;
import HubertRoszyk.company.entiti_class.PlanetPoints;
import HubertRoszyk.company.entiti_class.GalaxyPoints;
import HubertRoszyk.company.configuration.GameProperties;
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
    PlanetService planetService;

    @Autowired
    GameProperties gameProperties;

    /*private static PointGenerator instance;
    public static PointGenerator getInstance(){
        if (instance == null){
            instance = new PointGenerator();
        }
        return instance;
    }*/
    //@EventListener(ApplicationReadyEvent.class)
    public void generatePoints() {

        List<GalaxyPoints> galaxyPointsList = galaxyPointsService.getPointsList();
        List<PlanetPoints> planetPointsList = planetPointsService.getPlanetPointsList();
        for (GalaxyPoints galaxyPoints : galaxyPointsList) {
            double gotSciencePoints = galaxyPoints.getSciencePoints();

            double setSciencePoints = gotSciencePoints + galaxyPoints.getSciencePointsIncome();

            galaxyPoints.setSciencePoints(setSciencePoints);

            galaxyPointsService.updatePoints(galaxyPoints);
        }
        for (PlanetPoints planetPoints : planetPointsList) {
            /** checking if storage is full */
            Planet planet = planetService.getPlanetById(planetPoints.getPlanet().getId());


            double gotDefencePoints = planetPoints.getDefensePoints();
            double gotAttackPoints = planetPoints.getAttackPoints();
            double gotIndustryPoints = planetPoints.getIndustryPoints();

            double setDefencePoints = gotDefencePoints + planetPoints.getDefensePointsIncome();
            double setAttackPoints = gotAttackPoints + planetPoints.getAttackPointsIncome();
            double setIndustryPoints = gotIndustryPoints + planetPoints.getIndustryPointsIncome();

            planetPoints.setDefensePoints(setDefencePoints);
            planetPoints.setAttackPoints(setAttackPoints);
            planetPoints.setIndustryPoints(setIndustryPoints);

            planetPointsService.savePoints(planetPoints);
        }
        // System.out.println("wygenerowano");
    }
}
