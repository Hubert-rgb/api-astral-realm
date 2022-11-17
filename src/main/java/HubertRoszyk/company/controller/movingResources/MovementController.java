package HubertRoszyk.company.controller.movingResources;

import HubertRoszyk.company.controller.shipController.ShipControllerInterface;
import HubertRoszyk.company.converters.StringToAttackTypeConverter;
import HubertRoszyk.company.entiti_class.*;
import HubertRoszyk.company.configuration.GameProperties;
import HubertRoszyk.company.entiti_class.ship.AttackShip;
import HubertRoszyk.company.entiti_class.ship.Ship;
import HubertRoszyk.company.enumStatus.PlanetStatus;
import HubertRoszyk.company.enumStatus.ShipStatus;
import HubertRoszyk.company.enumTypes.AttackType;
import HubertRoszyk.company.enumTypes.TimerActionType;
import HubertRoszyk.company.service.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class MovementController {
    @Autowired
    PlanetService planetService;


    @Autowired
    UserService userService;

    @Autowired
    BattleService battleService;

    @Autowired
    BattleController battleController;

    @Autowired
    PillageController pillageController;

    @Autowired
    ColonisationController colonisationController;

    @Autowired
    ShipService shipService;

    @Autowired
    TimerEntityService timerEntityService;

    @Autowired
    TimerActionService timerActionService;

    @Autowired
    StringToAttackTypeConverter stringToAttackTypeConverter;

    @Autowired
    TravelRouteService travelRouteService;

    @PostMapping("/battle-controller/battles")
    public void armyMovement(@RequestBody JSONObject jsonInput) {
        int userId = (int) jsonInput.get("userId");
        int attackPlanetId = (int) jsonInput.get("attackPlanetId");
        int defensePlanetId = (int) jsonInput.get("defensePlanetId");
        List<Integer> shipsIdList = (List<Integer>) jsonInput.get("shipsIdList");
        String attackTypeString = (String) jsonInput.get("attackType");

        AttackType attackType = stringToAttackTypeConverter.convert(attackTypeString);

        Planet attackPlanet = planetService.getPlanetById(attackPlanetId);
        Planet defensePlanet = planetService.getPlanetById(defensePlanetId);
        User user = userService.getUserById(userId);

        Set<AttackShip> shipsSet = new HashSet<>();
        for (int i : shipsIdList){
            AttackShip ship = (AttackShip) shipService.getShipById(i);
            shipsSet.add(ship);
        }

        TimerEntity timerEntity = timerEntityService.getTimerEntityByGalaxyId(defensePlanet.getGalaxy().getId());

        Attack attack = new Attack(shipsSet, defensePlanet, user, attackType);
        defensePlanet.setPlanetStatus(PlanetStatus.BEFORE_ATTACK);
        battleService.saveBattle(attack);
        planetService.savePlanet(defensePlanet);

        for (Ship ship : shipsSet) {
            ship.setShipStatus(ShipStatus.ATTACKING);
            TravelRoute travelRoute = new TravelRoute(attackPlanet, defensePlanet, ship, timerEntityService);
            travelRouteService.saveTravelRoutes(travelRoute);

            /** timer task*/
            TimerAction timerAction = new TimerAction(TimerActionType.ATTACK_CARGO, travelRoute.getRouteEndingCycle(), ship.getId(), timerEntity);
            timerActionService.saveTimerAction(timerAction);
        }
    }
    public void attackExecution(AttackShip ship){
        Planet defencePlanet = ship.getCurrentPlanet();
        int userId = ship.getUser().getId();
        System.out.println(defencePlanet.getId());
        List<Attack> attacks = battleService.getAttackByDefencePlanetId(defencePlanet.getId());
        Attack attack =  attacks.get(attacks.size() - 1);
        AttackType attackType = attack.getAttackType();

        switch (attackType){
            case BATTLE -> battleController.battle(attack.getId());
            case PILLAGE -> pillageController.pillage();
            case COLONISATION -> colonisationController.colonize();
        }

    }

    /** geting battle status */
    @GetMapping("/battle-controller/battles/{battleId}/status")
    public String getBattleStatus(@PathVariable int battleId) {
        Attack attack = battleService.getBattleById(battleId);
        return attack.getStatus();
    }

    /** getting time left on battle*/
    @GetMapping("/battle-controller/battles/{battleId}/timeLeft")
    public long getBattleTimeLeft(@PathVariable int battleId) {
        LocalDateTime currentTime = LocalDateTime.now();

        Attack attack = battleService.getBattleById(battleId);
        LocalDateTime battleStartingTime = attack.getStartingTime();

        Duration battleDuration = Duration.between(battleStartingTime, currentTime);

        //battle time in nanoseconds
        long battleTimeLeft = attack.getBattleTime() - (battleDuration.getSeconds() * 1000);
        System.out.println(attack.getBattleTime());
        System.out.println(battleDuration.getNano());

        return battleTimeLeft;
    }
}
