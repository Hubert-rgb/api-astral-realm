package HubertRoszyk.company.repository;

import HubertRoszyk.company.entiti_class.ship.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipRepository extends JpaRepository<Ship, Integer> {
    Ship findByTravelRoute_ArrivalPlanet_Id(@NonNull int id);
    //public List<Ship> findByPlanetId(int planetId);
}
