package HubertRoszyk.company.controller.movingResources;

import HubertRoszyk.company.converters.StringToAttackTypeConverter;
import HubertRoszyk.company.entiti_class.*;
import HubertRoszyk.company.entiti_class.ship.AttackShip;
import HubertRoszyk.company.entiti_class.ship.IndustryShip;
import HubertRoszyk.company.entiti_class.ship.Ship;
import HubertRoszyk.company.enumStatus.PlanetStatus;
import HubertRoszyk.company.enumStatus.ShipStatus;
import HubertRoszyk.company.enumTypes.AttackType;
import HubertRoszyk.company.enumTypes.TimerActionType;
import HubertRoszyk.company.service.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@RestController
public class MovementController{
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

    @PostMapping("/attack-controller/battles")
    public void armyMovement(@RequestBody JSONObject jsonInput) {
        int userId = (int) jsonInput.get("userId");
        int attackPlanetId = (int) jsonInput.get("attackPlanetId");
        int defensePlanetId = (int) jsonInput.get("defensePlanetId");
        List<Integer> attackShipsIdList = (List<Integer>) jsonInput.get("attackShipsIdList");
        List<Integer> industryShipsIdList = (List<Integer>) jsonInput.get("industryShipsIdList");
        String attackTypeString = (String) jsonInput.get("attackType");

        AttackType attackType = stringToAttackTypeConverter.convert(attackTypeString);

        Planet attackPlanet = planetService.getPlanetById(attackPlanetId);
        Planet defensePlanet = planetService.getPlanetById(defensePlanetId);
        User user = userService.getUserById(userId);

        Set<AttackShip> attackShipSet = getShipsFromDatabase(attackShipsIdList);
        Set<IndustryShip> industryShipSet = getShipsFromDatabase(industryShipsIdList);

        TimerEntity timerEntity = timerEntityService.getTimerEntityByGalaxyId(defensePlanet.getGalaxy().getId());

        Attack attack = new Attack(attackShipSet, industryShipSet, attackPlanetId, defensePlanet, user, attackType);
        defensePlanet.setPlanetStatus(PlanetStatus.BEFORE_ATTACK);
        battleService.saveBattle(attack);
        planetService.savePlanet(defensePlanet);

        /** timer task*/
        Iterator<AttackShip> attackShipIterator = attackShipSet.iterator();
        TravelRoute travelRouteToTimerAction = new TravelRoute(attackPlanet, defensePlanet, attackShipIterator.next(), timerEntityService);
        TimerAction timerAction = new TimerAction(TimerActionType.ATTACK_CARGO, travelRouteToTimerAction.getRouteEndingCycle(), attack.getId(), timerEntity);
        timerActionService.saveTimerAction(timerAction);

        Set<Ship> attackShipShipSet = getShipsFromDatabase(attackShipsIdList);
        Set<Ship> industryShipShipSet = getShipsFromDatabase(industryShipsIdList);

        createTravelRoutes(attackShipShipSet, attackPlanet, defensePlanet);
        createTravelRoutes(industryShipShipSet, attackPlanet, defensePlanet);
    }
    public void attackExecution(Attack attack){
        AttackType attackType = attack.getAttackType();

        switch (attackType){
            case BATTLE -> battleController.attack(attack);
            case PILLAGE -> pillageController.attack(attack);
            case COLONISATION -> colonisationController.attack(attack);
        }
    }

    /** geting attack status */
    @GetMapping("/attack-controller/battles/{battleId}/status")
    public String getBattleStatus(@PathVariable int battleId) {
        Attack attack = battleService.getBattleById(battleId);
        return attack.getStatus();
    }

    /** getting time left on attack*/
    @GetMapping("/attack-controller/battles/{battleId}/timeLeft")
    public long getBattleTimeLeft(@PathVariable int battleId) {
        LocalDateTime currentTime = LocalDateTime.now();

        Attack attack = battleService.getBattleById(battleId);
        LocalDateTime battleStartingTime = attack.getStartingTime();

        Duration battleDuration = Duration.between(battleStartingTime, currentTime);

        //attack time in nanoseconds
        long battleTimeLeft = attack.getBattleTime() - (battleDuration.getSeconds() * 1000);
        System.out.println(attack.getBattleTime());
        System.out.println(battleDuration.getNano());

        return battleTimeLeft;
    }
    //TODO check cast
    private <S> Set<S> getShipsFromDatabase(List<Integer> shipsIdList){
        Set<S> shipsSet = new HashSet<>();
        for (int i : shipsIdList){
            S ship = (S) shipService.getShipById(i);
            shipsSet.add(ship);
        }
        return shipsSet;
    }

    private void createTravelRoutes(Set<Ship> shipSet, Planet attackPlanet, Planet defensePlanet){
        for (Ship ship : shipSet) {
            TravelRoute travelRoute = new TravelRoute(attackPlanet, defensePlanet, ship, timerEntityService);
            ship.setShipStatus(ShipStatus.ATTACKING);
            travelRouteService.saveTravelRoutes(travelRoute);
        }
    }
}
