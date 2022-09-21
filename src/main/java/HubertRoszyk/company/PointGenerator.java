package HubertRoszyk.company;

import HubertRoszyk.company.entiti_class.PlanetPoints;
import HubertRoszyk.company.entiti_class.GalaxyPoints;
import HubertRoszyk.company.configuration.GameProperties;
import HubertRoszyk.company.service.PlanetPointsService;
import HubertRoszyk.company.service.GalaxyPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    /*private static PointGenerator instance;
    public static PointGenerator getInstance(){
        if (instance == null){
            instance = new PointGenerator();
        }
        return instance;
    }*/
    //@EventListener(ApplicationReadyEvent.class)
    public void generatePoints() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                List<GalaxyPoints> galaxyPointsList = galaxyPointsService.getPointsList();
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
                    double setIndustryPoints = gotIndustryPoints + planetPoints.getIndustryPointsIncome();

                    planetPoints.setDefensePoints(setDefencePoints);
                    planetPoints.setAttackPoints(setAttackPoints);
                    planetPoints.setIndustryPoints(setIndustryPoints);

                    planetPointsService.savePoints(planetPoints);
                }
               // System.out.println("wygenerowano");
            }
        };
        Timer timer = new Timer();
        System.out.println(gameProperties.getPeriod());
        timer.schedule(task, 0, gameProperties.getPeriod());
    }
}
