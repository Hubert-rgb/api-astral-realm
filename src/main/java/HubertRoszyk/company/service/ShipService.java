package HubertRoszyk.company.service;

import HubertRoszyk.company.entiti_class.Ship;
import HubertRoszyk.company.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipService {
    @Autowired
    ShipRepository repository;

    public Ship saveShip(Ship ship) {
        return repository.save(ship);
    }
    public List<Ship> getShipsList() {
        return repository.findAll();
    }
    public Ship getShipById(int id) {
        return repository.findById(id).orElse(null);
    }
    public void deleteShips() {
        repository.deleteAll();
        System.out.println("All Ships deleted");
    }
   /* public List<Ship> getShipsByPlanetId(int planetId) {
        return repository.findByTravelRoute_DeparturePlanet_Id(planetId);
    }*/
}
