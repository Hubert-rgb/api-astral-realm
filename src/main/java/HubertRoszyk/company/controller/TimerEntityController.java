package HubertRoszyk.company.controller;

import HubertRoszyk.company.PointGenerator;
import HubertRoszyk.company.configuration.GameProperties;
import HubertRoszyk.company.entiti_class.TimerEntity;
import HubertRoszyk.company.service.TimerEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class TimerEntityController {
    @Autowired
    TimerEntityService timerEntityService;

    @Autowired
    GameProperties gameProperties;

    @Autowired
    PointGenerator pointGenerator;

    @GetMapping("timer-entity-controller/galaxy/{galaxyId}")
    public long getTimeLeftInCycle(@PathVariable int galaxyId) {
        LocalDateTime currentTime = LocalDateTime.now();

        TimerEntity timerEntity = timerEntityService.getTimerEntityByGalaxyId(galaxyId);
        LocalDateTime timerStartTime = timerEntity.getTimerStartTime();
        long cyclesNum = timerEntity.getCyclesNum();
        LocalDateTime cycleStartTime = timerStartTime.plusSeconds(gameProperties.getPeriod() * cyclesNum);

        Duration cycleDuration = Duration.between(cycleStartTime, currentTime);
        long timeLeft = gameProperties.getPeriod() - (cycleDuration.getSeconds());

        return timeLeft;
    }
    public void timerExecution() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                pointGenerator.generatePoints();
                /** method checking changes between cycles (battles, ships making etc.)*/
            }
        };
        timer.schedule(timerTask, 0, gameProperties.getPeriod());
    }
}

