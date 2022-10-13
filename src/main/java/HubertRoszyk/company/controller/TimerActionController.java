package HubertRoszyk.company.controller;

import HubertRoszyk.company.Strategy.timerActionStategy.TimerActionBattle;
import HubertRoszyk.company.Strategy.timerActionStategy.TimerActionBuild;
import HubertRoszyk.company.Strategy.timerActionStategy.TimerActionContext;
import HubertRoszyk.company.Strategy.timerActionStategy.TimerActionTravel;
import HubertRoszyk.company.entiti_class.TimerAction;
import HubertRoszyk.company.enumTypes.TimerActionType;
import HubertRoszyk.company.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TimerActionController {
    @Autowired
    private ShipService shipService;

    public void execute(TimerAction timerAction) {
        TimerActionType timerActionType = timerAction.getTimerActionType();
        TimerActionContext context = new TimerActionContext();

        switch (timerActionType) {
            case BATTLE -> context.setStrategy(new TimerActionBattle());
            case TRAVEL -> context.setStrategy(new TimerActionTravel(shipService));
            case BUILDING -> context.setStrategy(new TimerActionBuild());
        }
        context.executeStrategy(timerAction);
    }
}
