package HubertRoszyk.company.repository;

import HubertRoszyk.company.entiti_class.PlanetPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetPointsRepository extends JpaRepository<PlanetPoints, Integer> {
    public PlanetPoints findPlanetPointsByPlanetId(int planetId);
}
