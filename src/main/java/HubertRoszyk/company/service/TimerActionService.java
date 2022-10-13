package HubertRoszyk.company.service;

import HubertRoszyk.company.entiti_class.TimerAction;
import HubertRoszyk.company.repository.TimerActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimerActionService {
    @Autowired
    TimerActionRepository repository;

    public TimerAction saveTimerAction(TimerAction timerAction){
        return repository.save(timerAction);
    }
    public List<TimerAction> saveAllTimerActions(List<TimerAction> timerActionList) {
        return repository.saveAll(timerActionList);
    }
    public List<TimerAction> getAllTimerActionsByTimerEntityId(int timerEntityId){
        return repository.findTimerActionByTimerEntityId(timerEntityId);
    }
    public void removeTimerActionById(int timerActionId) {
        repository.deleteById(timerActionId);
    }
}
