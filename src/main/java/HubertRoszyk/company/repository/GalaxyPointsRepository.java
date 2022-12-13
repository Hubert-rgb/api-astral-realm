package HubertRoszyk.company.repository;

import HubertRoszyk.company.entiti_class.GalaxyPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface GalaxyPointsRepository extends JpaRepository<GalaxyPoints, Integer> {
    Set<GalaxyPoints> findGalaxy_PointsByUserId(int userId);

    GalaxyPoints findGalaxy_PointsByUserIdAndGalaxyId(int userId, int galaxyId);
}
