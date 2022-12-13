package HubertRoszyk.company.service;

import HubertRoszyk.company.entiti_class.PlanetPoints;
import HubertRoszyk.company.repository.PlanetPointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanetPointsService {
    @Autowired
    PlanetPointsRepository repository;

    public List<PlanetPoints> getPlanetPointsList() {
        return repository.findAll();
    }
    /*public Set<Points> getPointsByUserId(int userId) {
        return repository.findPointsByUserId(userId);
    }*/
    public PlanetPoints savePoints(PlanetPoints planetPoints) {
        return repository.save(planetPoints);
    }
    public List<PlanetPoints> savePlanetPointsList(List<PlanetPoints> points) {
        return repository.saveAll(points);
    }
    public void deleteAllPlanetPoints() {
        repository.deleteAll();
        System.out.println("usunięto wszystkie Punkty armii");
    }
    public PlanetPoints getPointsByPlanetId(int planetId) {
        return repository.findPlanetPointsByPlanetId(planetId);
    }
}
