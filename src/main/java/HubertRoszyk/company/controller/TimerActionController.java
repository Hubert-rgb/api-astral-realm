package HubertRoszyk.company.controller;

import HubertRoszyk.company.Strategy.timerActionStategy.*;
import HubertRoszyk.company.controller.movingResources.MovementController;
import HubertRoszyk.company.controller.purchaseController.BuildingPurchase;
import HubertRoszyk.company.controller.shipController.ShipControllerInterface;
import HubertRoszyk.company.entiti_class.TimerAction;
import HubertRoszyk.company.enumTypes.TimerActionType;
import HubertRoszyk.company.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequestMapping("/timer-action-controller")
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

    @Autowired
    BattleService battleService;


    public void execute(TimerAction timerAction) {
        TimerActionType timerActionType = timerAction.getTimerActionType();
        TimerActionContext context = new TimerActionContext();

        switch (timerActionType) {
            case BATTLE -> context.setStrategy(new TimerActionBattle(movementController, battleService));
            case INDUSTRY_CARGO -> context.setStrategy(new TimerActionIndustryCargo(shipService));
            case ATTACK_CARGO -> context.setStrategy(new TimerActionAttackCargo(shipService, timerEntityService, timerActionService, battleService));
            case BUILDING -> context.setStrategy(new TimerActionBuild(buildingPurchase, buildingService));
            case SHIP -> context.setStrategy(new TimerActionShip(shipService));
            case PLANET_STATUS_AFTER_ATTACK -> context.setStrategy(new TimerActionPlanetStatusAfterAttack(planetService));
        }
        context.executeStrategy(timerAction);
    }
    @GetMapping("/timer-action/galaxy/{galaxyId}")
    public List<TimerAction> getTimerActions(@PathVariable int galaxyId){
        return timerActionService.getAllTimerActionsByGalaxyId(galaxyId);
    }
}
