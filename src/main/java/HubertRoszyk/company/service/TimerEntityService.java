package HubertRoszyk.company.service;

import HubertRoszyk.company.entiti_class.Planet;
import HubertRoszyk.company.entiti_class.TimerEntity;
import HubertRoszyk.company.repository.TimerEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimerEntityService {
    @Autowired
    TimerEntityRepository repository;

    public TimerEntity getTimerEntityById(int id) {
        return repository.findById(id).orElse(null);
    }

    public TimerEntity saveTimerEntity(TimerEntity timerEntity) {
        return repository.save(timerEntity);
    }

    public TimerEntity getTimerEntityByGalaxyId(int galaxyId) {
        return repository.findTimerEntityByGalaxyId(galaxyId);
    }
}
