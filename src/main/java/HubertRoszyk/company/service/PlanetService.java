package HubertRoszyk.company.service;

import HubertRoszyk.company.EntitiClass.Planet;
import HubertRoszyk.company.repository.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanetService {
    @Autowired
    private PlanetRepository repository;


    public Planet savePlanet(Planet planet) {
        return repository.save(planet);
    }
    public List<Planet> savePlanetsList(List<Planet> planets) {
        return repository.saveAll(planets);
    }
    public List<Planet> getPlanetsList() {
        return repository.findAll();
    }
    public Planet getPlanetById(int id) {
        return repository.findById(id).orElse(null);
    }
    public void deletePlanets() {
        repository.deleteAll();
        System.out.println("All Planets deleted");
    }
    public Planet updatePlanet(Planet planet) {
        Planet existingPlanet = repository.findById(planet.getId()).orElse(null);
        existingPlanet.setBuildingList(planet.getBuildingList());
        existingPlanet.setIndustryPointsProduce(planet.getIndustryPointsProduce());
        existingPlanet.setSciencePointsProduce(planet.getSciencePointsProduce());

        return repository.save(existingPlanet);
    }
}
