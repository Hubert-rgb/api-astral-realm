package HubertRoszyk.company.repository;

import HubertRoszyk.company.entiti_class.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipRepository extends JpaRepository<Ship, Integer> {
    public List<Ship> findShipByPlanetId(int planetId);
}
