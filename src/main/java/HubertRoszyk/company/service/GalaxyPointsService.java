package HubertRoszyk.company.service;

import HubertRoszyk.company.entiti_class.GalaxyPoints;
import HubertRoszyk.company.repository.GalaxyPointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GalaxyPointsService {
    @Autowired
    GalaxyPointsRepository repository;

    public List<GalaxyPoints> getPointsList() {
        return repository.findAll();
    }
    /*public Set<Points> getPointsByUserId(int userId) {
        return repository.findPointsByUserId(userId);
    }*/
    public GalaxyPoints savePoints(GalaxyPoints galaxyPoints) {
        return repository.save(galaxyPoints);
    }
    public List<GalaxyPoints> savePointsList(List<GalaxyPoints> points) {
        return repository.saveAll(points);
    }
    public void deleteAllPoints() {
        repository.deleteAll();
        System.out.println("usunięto wszystkie Punkty");
    }
    public GalaxyPoints getPointsByUserIdAndGalaxyId(int userId, int galaxyId) {
        return repository.findGalaxy_PointsByUserIdAndGalaxyId(userId, galaxyId);
    }
    public void updatePoints(GalaxyPoints galaxyPoints) {
        //repository.deleteById(points.getId());
        repository.save(galaxyPoints);
    }
}
