package HubertRoszyk.company.controller;

import HubertRoszyk.company.entiti_class.*;
import HubertRoszyk.company.enumTypes.BuildingType;
import HubertRoszyk.company.service.BuildingService;
import HubertRoszyk.company.service.PlanetPointsService;
import HubertRoszyk.company.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
public class PlanetPointsController {
    @Autowired
    private PlanetPointsService planetPointsService;

    @Autowired
    private PlanetService planetService;

    @Autowired
    private BuildingService buildingService;

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

        double planetIndustryPointsIncome = planet.getIndustryPointsProduce() * planet.getIndustryPointsMultiplier();

        planetPoints.setIndustryPointsIncome(planetIndustryPointsIncome);

        planetPointsService.savePoints(planetPoints);
    }
    /*public void getTotalStorageSize(int planetId){
        PlanetPoints planetPoints = planetPointsService.getPointsByPlanetId(planetId);
        List<Building> buildingList = buildingService.getBuildingsByPlanetId(planetId);

        int gotStorageSize = planetPoints.getTotalStorageSize();
        int totalStorageSize = 0;
        for(Building building: buildingList) {
            System.out.println(building.getBuildingType());
            if (building.getBuildingType() == BuildingType.STORAGE) {
                System.out.println("jest");
                totalStorageSize += building.getBuildingType().getVolume();
            }
        }
        planetPoints.setTotalStorageSize(totalStorageSize);
        planetPointsService.savePoints(planetPoints);
    }*/
    public void getTotalHarbourSize(int planetId) {
        PlanetPoints planetPoints = planetPointsService.getPointsByPlanetId(planetId);
        List<Building> buildingList = buildingService.getBuildingsByPlanetId(planetId);

        int totalHarbourSize = 0;
        for(Building building: buildingList) {
            if (building.getBuildingType().equals(BuildingType.HARBOUR)) {
                totalHarbourSize += building.getBuildingType().getVolume();
            }
        }
        planetPoints.setTotalHarbourSize(totalHarbourSize);
        planetPointsService.savePoints(planetPoints);
    }
}
