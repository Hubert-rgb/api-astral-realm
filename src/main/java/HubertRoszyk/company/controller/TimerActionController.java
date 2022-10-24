package HubertRoszyk.company.controller;

import HubertRoszyk.company.Strategy.timerActionStategy.*;
import HubertRoszyk.company.controller.purchaseController.BuildingPurchase;
import HubertRoszyk.company.controller.shipController.ShipControllerInterface;
import HubertRoszyk.company.entiti_class.TimerAction;
import HubertRoszyk.company.enumTypes.TimerActionType;
import HubertRoszyk.company.service.BuildingService;
import HubertRoszyk.company.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TimerActionController {
    @Autowired
    private ShipService shipService;

    @Autowired
    BuildingService buildingService;

    @Autowired
    BuildingPurchase buildingPurchase;

    @Autowired
    ShipControllerInterface shipControllerInterface;

    public void execute(TimerAction timerAction) {
        TimerActionType timerActionType = timerAction.getTimerActionType();
        TimerActionContext context = new TimerActionContext();

        switch (timerActionType) {
            case BATTLE -> context.setStrategy(new TimerActionBattle());
            case INDUSTRY_CARGO -> context.setStrategy(new TimerActionIndustryCargo(shipService));
            case ATTACK_CARGO -> context.setStrategy(new TimerActionAttackCargo(shipService));
            case BUILDING -> context.setStrategy(new TimerActionBuild(buildingPurchase, buildingService));
            case SHIP -> context.setStrategy(new TimerActionShip(shipService));
        }
        context.executeStrategy(timerAction);
    }
}
