package HubertRoszyk.company.repository;

import HubertRoszyk.company.entiti_class.TimerAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimerActionRepository extends JpaRepository<TimerAction, Integer> {
    List<TimerAction> findTimerActionByTimerEntityId(int timerEntityId);

    List<TimerAction> findByTimerEntity_Galaxy_Id(int id);
}
