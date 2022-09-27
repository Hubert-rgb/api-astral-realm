package HubertRoszyk.company.repository;

import HubertRoszyk.company.entiti_class.Building;
import HubertRoszyk.company.entiti_class.TimerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimerEntityRepository extends JpaRepository<TimerEntity, Integer> {

    TimerEntity findTimerEntityByGalaxyId(int galaxyId);

}