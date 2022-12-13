package HubertRoszyk.company.repository;

import HubertRoszyk.company.entiti_class.TravelRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelRouteRepository extends JpaRepository<TravelRoute, Integer> {
}
