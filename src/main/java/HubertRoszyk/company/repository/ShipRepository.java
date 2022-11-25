package HubertRoszyk.company.repository;

import HubertRoszyk.company.entiti_class.ship.Ship;
import HubertRoszyk.company.enumStatus.ShipStatus;
import HubertRoszyk.company.enumTypes.ShipType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShipRepository extends JpaRepository<Ship, Integer> {

    //@EntityGraph(value = "ship")
   /* @Query(value = "SELECT * FROM ship INNER JOIN travel_route on ship.id = travel_route.ship_id WHERE ship.ship_status = 1 AND travel_route.arrival_planet_planet_id = ?1", nativeQuery = true)
    List<Ship> findByPlanetId(int id);*/

    @EntityGraph(value = "ship")
    List<Ship> findByShipStatusAndTravelRoute_ArrivalPlanet_Id(ShipStatus shipStatus, int id);


    @EntityGraph(value = "ship")
    Optional<Ship> findById(@Param("id") int id);
}
