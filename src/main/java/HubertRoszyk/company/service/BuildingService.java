package HubertRoszyk.company.service;

import HubertRoszyk.company.entiti_class.Building;
import HubertRoszyk.company.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingService {
    @Autowired
    BuildingRepository repository;

    public BuildingService(BuildingRepository buildingRepository) {
        repository = buildingRepository;
    }

    public Building saveBuilding(Building building) {
        return repository.save(building);
    }
    public List<Building> saveBuildingsList(List<Building> buildings) {
        return repository.saveAll(buildings);
    }
    public List<Building> getBuildingsList() {
        return repository.findAll();
    }
    public Building getBuildingById(int id) {
        return repository.findById(id).orElse(null);
    }
    public void deleteBuilding() {
        repository.deleteAll();
        System.out.println("All Buildings deleted");
    }
    public List<Building> getBuildingsByPlanetId(int planetId) {
        return repository.findBuildingByPlanetId(planetId);
    }
}
