package HubertRoszyk.company.controller;

import HubertRoszyk.company.Strategy.timerActionStategy.*;
import HubertRoszyk.company.entiti_class.TimerAction;
import HubertRoszyk.company.enumTypes.TimerActionType;
import HubertRoszyk.company.service.BuildingService;
import HubertRoszyk.company.service.ShipService;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TimerActionController {
    @Autowired
    private ShipService shipService;

    @Autowired
    BuildingService buildingService;

    @Autowired
    IndustryPointsController industryPointsController;

    public void execute(TimerAction timerAction) {
        TimerActionType timerActionType = timerAction.getTimerActionType();
        TimerActionContext context = new TimerActionContext();

        switch (timerActionType) {
            case BATTLE -> context.setStrategy(new TimerActionBattle());
            case TRAVEL -> context.setStrategy(new TimerActionTravel(shipService));
            case BUILDING -> context.setStrategy(new TimerActionBuild(industryPointsController, buildingService));
            case SHIP -> context.setStrategy(new TimerActionShip(shipService));
        }
        context.executeStrategy(timerAction);
    }
}
