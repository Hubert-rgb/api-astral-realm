package HubertRoszyk.company.controller;

import HubertRoszyk.company.PointGenerator;
import HubertRoszyk.company.configuration.GameProperties;
import HubertRoszyk.company.entiti_class.TimerAction;
import HubertRoszyk.company.entiti_class.TimerEntity;
import HubertRoszyk.company.service.TimerActionService;
import HubertRoszyk.company.service.TimerEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class TimerEntityController {
    @Autowired
    TimerEntityService timerEntityService;

    @Autowired
    GameProperties gameProperties;

    @Autowired
    PointGenerator pointGenerator;

    @Autowired
    TimerActionController timerActionController;

    @Autowired
    TimerActionService timerActionService;

    @GetMapping(value = "timer-entity-controller/galaxy/{galaxyId}")
    @ResponseBody
    public long getTimeLeftInCycle(@PathVariable int galaxyId) {
        LocalDateTime currentTime = LocalDateTime.now();

        TimerEntity timerEntity = timerEntityService.getTimerEntityByGalaxyId(galaxyId);
        LocalDateTime timerStartTime = timerEntity.getTimerStartTime();

        long cyclesNum = timerEntity.getCyclesNum() - 1;
        LocalDateTime cycleStartTime = timerStartTime.plusSeconds((gameProperties.getPeriod() * cyclesNum));

        Duration cycleDuration = Duration.between(cycleStartTime, currentTime);
        System.out.println(gameProperties.getPeriod());
        long timeLeft = gameProperties.getPeriod() - (cycleDuration.getSeconds());

        return timeLeft;
    }
    public void timerExecution(int galaxyId) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("wukonane");
                TimerEntity timerEntity = timerEntityService.getTimerEntityByGalaxyId(galaxyId);
                int gotCycles = timerEntity.getCyclesNum();
                int setCycles = gotCycles + 1;
                timerEntity.setCyclesNum(setCycles);
                timerEntityService.saveTimerEntity(timerEntity);

                pointGenerator.generatePoints();

                List<TimerAction> timerActionList = timerActionService.getAllTimerActionsByTimerEntityId(timerEntity.getId());

                if (timerActionList != null) {
                    for (TimerAction timerAction: timerActionList) {
                        System.out.println("e" + timerAction.getEndingCycle());
                        System.out.println("s" + setCycles);
                        if (setCycles >= timerAction.getEndingCycle()){
                            timerActionController.execute(timerAction);
                            timerActionService.removeTimerActionById(timerAction.getId());
                        }
                    }

                }

                /** method checking changes between cycles (battles, ships making etc.)*/
            }
        };
        timer.schedule(timerTask, 0, gameProperties.getPeriod() * 1000 / 2);
    }
}

