package HubertRoszyk.company.service;

import HubertRoszyk.company.entiti_class.TravelRoute;
import HubertRoszyk.company.repository.TravelRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TravelRouteService {
    @Autowired
    TravelRouteRepository repository;

    public TravelRoute saveTravelRoutes(TravelRoute travelRoute) {
        return repository.save(travelRoute);
    }
    public List<TravelRoute> getTravelRouteList() {
        return repository.findAll();
    }
    public TravelRoute getTravelRouteById(int id) {
        return repository.findById(id).orElse(null);
    }
    public void deleteTravelRoute() {
        repository.deleteAll();
        System.out.println("All TravelRoute deleted");
    }
}
