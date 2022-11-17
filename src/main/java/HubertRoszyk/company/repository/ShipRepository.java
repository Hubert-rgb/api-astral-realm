package HubertRoszyk.company.repository;

import HubertRoszyk.company.entiti_class.ship.Ship;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShipRepository extends JpaRepository<Ship, Integer> {
    Ship findByTravelRoute_ArrivalPlanet_Id(@NonNull int id);
    //public List<Ship> findByPlanetId(int planetId);


    @EntityGraph(value = "ship")
    Optional<Ship> findById(@Param("id") int id);
}
