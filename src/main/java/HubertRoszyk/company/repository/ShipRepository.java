package HubertRoszyk.company.repository;

import HubertRoszyk.company.entiti_class.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipRepository extends JpaRepository<Ship, Integer> {
    Ship findByTravelRoute_DeparturePlanet_Id(@NonNull int id);
    //public List<Ship> findByPlanetId(int planetId);
}
