package HubertRoszyk.company.controller;

import HubertRoszyk.company.Strategy.timerActionStategy.*;
import HubertRoszyk.company.controller.movingResources.MovementController;
import HubertRoszyk.company.controller.purchaseController.BuildingPurchase;
import HubertRoszyk.company.controller.shipController.ShipControllerInterface;
import HubertRoszyk.company.entiti_class.TimerAction;
import HubertRoszyk.company.enumTypes.TimerActionType;
import HubertRoszyk.company.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TimerActionController {
    @Autowired
    ShipService shipService;

    @Autowired
    BuildingService buildingService;

    @Autowired
    BuildingPurchase buildingPurchase;

    @Autowired
    MovementController movementController;

    @Autowired
    PlanetService planetService;

    @Autowired
    TimerEntityService timerEntityService;

    @Autowired
    TimerActionService timerActionService;


    public void execute(TimerAction timerAction) {
        TimerActionType timerActionType = timerAction.getTimerActionType();
        TimerActionContext context = new TimerActionContext();

        switch (timerActionType) {
            case BATTLE -> context.setStrategy(new TimerActionBattle(movementController, shipService));
            case INDUSTRY_CARGO -> context.setStrategy(new TimerActionIndustryCargo(shipService));
            case ATTACK_CARGO -> context.setStrategy(new TimerActionAttackCargo(shipService, timerEntityService, timerActionService));
            case BUILDING -> context.setStrategy(new TimerActionBuild(buildingPurchase, buildingService));
            case SHIP -> context.setStrategy(new TimerActionShip(shipService));
            case PLANET_STATUS_AFTER_ATTACK -> context.setStrategy(new TimerActionPlanetStatusAfterAttack(planetService));
        }
        context.executeStrategy(timerAction);
    }
}
