package HubertRoszyk.company.controller.movingResources;

import HubertRoszyk.company.RandomDraw;
import HubertRoszyk.company.configuration.GameProperties;
import HubertRoszyk.company.controller.ArmyController;
import HubertRoszyk.company.entiti_class.*;
import HubertRoszyk.company.entiti_class.ship.AttackShip;
import HubertRoszyk.company.entiti_class.ship.Ship;
import HubertRoszyk.company.enumStatus.PlanetStatus;
import HubertRoszyk.company.enumTypes.TimerActionType;
import HubertRoszyk.company.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class BattleController {
    @Autowired
    BattleService battleService;

    @Autowired
    PlanetService planetService;

    @Autowired
    PlanetPointsService planetPointsService;

    @Autowired
    GameProperties gameProperties;

    @Autowired
    ShipService shipService;

    @Autowired
    TravelRouteService travelRouteService;

    @Autowired
    TimerEntityService timerEntityService;

    @Autowired
    TimerActionService timerActionService;

    /** method executed while army movement is battle */
    public String battle(int attackId) {
        Attack attack = battleService.getBattleById(attackId);

        Planet defencePlanet = attack.getDefencePlanet();
        PlanetPoints defencePlanetPoints = planetPointsService.getPointsByPlanetId(defencePlanet.getId());
        PlanetPoints attackPlanetPoints = planetPointsService.getPointsByPlanetId(attack.getAttackPlanetId());

        //attack - army (Map)
        //defence - army from defence planet (Map) and defence as planet points (int) variable

        int attackPoints = attack.getAttackArmyValue();
        int defencePoints = defencePlanetPoints.getDefensePoints();
        int defencePlanetArmyValue = ArmyController.getArmyValue(defencePlanetPoints.getArmy());

        double attackMultiplier = RandomDraw.battleMultiplierDraw();
        double defenceMultiplier = RandomDraw.battleMultiplierDraw();

        double battleAttackPoints = attackPoints * gameProperties.getAttackMultiplier() * attackMultiplier;
        double battleDefencePoints = defencePoints + defencePlanetArmyValue * gameProperties.getDefenceMultiplier() * defenceMultiplier;

        double attackArmySize = attack.getAttackArmySize();
        double defenceArmySize = ArmyController.getArmySize(defencePlanetPoints.getArmy());

        //army ratio is defined by number of army division (not their power)

        double armyRatio = (attackArmySize / (attackArmySize + defenceArmySize)) * 100;

        Set<AttackShip> attackShips = attack.getAttackShips();

        if (battleAttackPoints > battleDefencePoints) {
            /** battle won */

            defencePlanet.asignUser(attack.getUser());
            defencePlanetPoints.setArmy(defencePlanetPoints.getEmptyArmy());
            defencePlanet.setPlanetStatus(PlanetStatus.AFTER_ATTACK);
            List<Ship> defencePlanetShip = shipService.getShipsByPlanetId(defencePlanet.getId());
            shipService.removeShipsList(defencePlanetShip);

            Map<Integer, Integer> gotAttackArmy = attack.getArmy();
            Map<Integer, Integer> setAttackArmy;
            if(armyRatio > 0 && armyRatio < 30){
                double percentage = 0.9;
                setAttackArmy = subtractArmy(gotAttackArmy, attackArmySize, percentage);
                System.out.println("10:90");
                loadArmyAndSendShips(setAttackArmy, attackShips, defencePlanetPoints);

            } else if (armyRatio >= 30 && armyRatio < 50) {
                double percentage = 0.7;
                setAttackArmy = subtractArmy(gotAttackArmy, attackArmySize, percentage);
                System.out.println("30:70");
                loadArmyAndSendShips(setAttackArmy, attackShips, defencePlanetPoints);

            } else if (armyRatio >= 50 && armyRatio < 70){
                double percentage = 0.4;
                setAttackArmy = subtractArmy(gotAttackArmy, attackArmySize, percentage);
                System.out.println(attack.getArmy());
                System.out.println("50:50");
                loadArmyAndSendShips(setAttackArmy, attackShips, defencePlanetPoints);

            } else if (armyRatio >= 70 && armyRatio < 90){
                double percentage = 0.2;
                setAttackArmy = subtractArmy(gotAttackArmy, attackArmySize, percentage);
                System.out.println(attack.getArmy());
                System.out.println("70:30");
                loadArmyAndSendShips(setAttackArmy, attackShips, defencePlanetPoints);

            } else if (armyRatio >= 90 && armyRatio <= 100) {
                double percentage = 0.1;
                setAttackArmy = subtractArmy(gotAttackArmy, attackArmySize, percentage);
                System.out.println(attack.getArmy());
                System.out.println("90:10");
                loadArmyAndSendShips(setAttackArmy, attackShips, defencePlanetPoints);

            } else if (armyRatio > 100){
                setAttackArmy = new HashMap<>();
                System.out.println("ERR:wywaliło poza skalę");
            } else {
                setAttackArmy = new HashMap<>();
                System.out.println("ERR:nic nie działa");
            }
            System.out.println(setAttackArmy);
            attack.setArmy(setAttackArmy);
            attack.setStatus("attack won");
            battleService.saveBattle(attack);
            return "attack won";
        } else {
            /** battle lost */
            attack.setArmy(ArmyController.getEmptyArmy());

            Map<Integer, Integer> gotDefenceArmy = defencePlanetPoints.getArmy();
            Map<Integer, Integer> setDefenceArmy;
            if (armyRatio <= 100 && armyRatio > 70){
                double percentage = 0.45;
                setDefenceArmy = subtractArmy(gotDefenceArmy, defenceArmySize, percentage);
                System.out.println("10:90");

            } else if (armyRatio <= 70 && armyRatio > 50) {
                double percentage = 0.35;
                setDefenceArmy = subtractArmy(gotDefenceArmy, defenceArmySize, percentage);
                System.out.println("30:70");

            } else if (armyRatio <= 50 && armyRatio > 30){
                double percentage = 0.2;
                setDefenceArmy = subtractArmy(gotDefenceArmy, defenceArmySize, percentage);
                System.out.println("50:50");

            } else if (armyRatio <= 30 && armyRatio > 10){
                double percentage = 0.1;
                setDefenceArmy = subtractArmy(gotDefenceArmy, defenceArmySize, percentage);
                System.out.println("70:30");

            } else if (armyRatio <= 10 && armyRatio >= 0) {
                double percentage = 0.05;
                setDefenceArmy = subtractArmy(gotDefenceArmy, defenceArmySize, percentage);
                System.out.println("90:10");

            }  else {
                setDefenceArmy = new HashMap<>();
                System.out.println("ERR:nic nie działa");
            }
            defencePlanetPoints.setArmy(setDefenceArmy);
            attack.setStatus("attack lost");
            battleService.saveBattle(attack);
            return  "attack lost";
        }
    }

    /** subtracting appropriately to the army ratio; the worst divisions die */
    private Map<Integer, Integer> subtractArmy(Map<Integer, Integer> army, double attackArmySize, double percentage){
        int deadArmySize = (int) Math.ceil(attackArmySize * percentage);
        int i = 1;
        while (deadArmySize > 0 || i <= army.size()){
            int gotArmy = army.get(i);
            int leftFromSubtract = deadArmySize - gotArmy;
            int setArmy = 0;
            if (leftFromSubtract < 0){
                setArmy = gotArmy - deadArmySize;
                deadArmySize = 0;

            } else {
                deadArmySize -= gotArmy;
            }
            army.put(i, setArmy);

            i += 1;
        }
        return army;
    }
    //the best division goes to barracks on planet,
    //worse to ships which stays on this planet,
    //the worst are going back to original planet
    //best stays, worse leaves the planet
    private void loadArmyAndSendShips(Map<Integer, Integer> army, Set<AttackShip> shipsSet, PlanetPoints defencePlanetPoints){
        //creating empty army in every ship
        List<AttackShip> ships = new ArrayList<>(shipsSet);

        for (AttackShip ship: ships){
            ship.setShipLoad(ArmyController.getEmptyArmy());
        }
        //sorting by the capacity
        ships.sort(Comparator.comparing(Ship::getShipCapacity).reversed());

        addingArmyToBarracks(defencePlanetPoints, army, ships);
        shipManagement(defencePlanetPoints, ships);
    }

    private void addingArmyToBarracks(PlanetPoints defencePlanetPoints, Map<Integer, Integer> army, List<AttackShip> ships){
        Map<Integer, Integer> barrackArmy = ArmyController.getEmptyArmy();
        Stack<Integer> armyStack = ArmyController.armyMapToStack(army);
        int shipNum = 0;
        int leftSpaceInArmyBuilding = defencePlanetPoints.getTotalAttackBuildingSize();
        while(armyStack.size() > 0){
            int armyDev = armyStack.pop();
            Map<Integer, Integer> singleMap = ArmyController.getEmptyArmy();
            singleMap.put(armyDev, 1);
            if (leftSpaceInArmyBuilding > 0){
                barrackArmy = ArmyController.combineArmy(barrackArmy, singleMap);
                leftSpaceInArmyBuilding -= 1;
            } else {
                AttackShip currentShip = ships.get(shipNum);
                if (currentShip.getShipCapacity() - ArmyController.getArmySize(currentShip.getShipLoad()) > 0){
                    currentShip.setShipLoad(ArmyController.combineArmy(currentShip.getShipLoad(), singleMap));
                } else {
                    shipNum += 1;
                    Map<Integer, Integer> shipArmy = ArmyController.combineArmy(singleMap, currentShip.getShipLoad());
                    currentShip.setShipLoad(shipArmy);
                }
            }
        }
        defencePlanetPoints.setArmy(barrackArmy);

        planetPointsService.savePoints(defencePlanetPoints);
    }
    private void shipManagement(PlanetPoints defencePlanetPoints, List<AttackShip> ships){
        Planet departurePlanet = ships.get(0).getTravelRoute().get(ships.get(0).getTravelRoute().size() - 2).getArrivalPlanet();
        for (AttackShip ship: ships){
            if (defencePlanetPoints.getTotalHarbourLoad() > 0) {
                //if there is room for ship in harbour it changes harbour but doesn't leave planet
                changeShipHarbour(departurePlanet.getId(), defencePlanetPoints.getPlanet().getId());
            } else {
                //if not, they are not changing harbours, but goes back on origin planet
                TimerEntity timerEntity = timerEntityService.getTimerEntityByGalaxyId(ship.getCurrentPlanet().getGalaxy().getId());

                TravelRoute travelRoute = new TravelRoute(defencePlanetPoints.getPlanet(), departurePlanet, ship, timerEntityService);
                TimerAction timerAction = new TimerAction(TimerActionType.INDUSTRY_CARGO, travelRoute.getRouteEndingCycle(), ship.getId(), timerEntity);

                travelRouteService.saveTravelRoutes(travelRoute);
                timerActionService.saveTimerAction(timerAction);
            }
            shipService.saveShip(ship);
        }
    }
    /** changing harbour load */
    //TODO it's implemented twice, should be once. I don't know where to put it
    public void changeShipHarbour(int departurePlanetId, int destinationPlanetId) {
        PlanetPoints destinationPlanetPoints = planetPointsService.getPointsByPlanetId(destinationPlanetId);
        PlanetPoints departurePlanetPoints = planetPointsService.getPointsByPlanetId(departurePlanetId);

        int gotDepartureHarbourLoad = departurePlanetPoints.getTotalHarbourLoad();
        int setDepartureHarbourLoad = gotDepartureHarbourLoad - 1;
        departurePlanetPoints.setTotalHarbourLoad(setDepartureHarbourLoad);
        planetPointsService.savePoints(departurePlanetPoints);

        int gotDestinationHarbourLoad = destinationPlanetPoints.getTotalHarbourLoad();
        int setDestinationHarbourLoad = gotDestinationHarbourLoad + 1;
        destinationPlanetPoints.setTotalHarbourLoad(setDestinationHarbourLoad);

        planetPointsService.savePoints(departurePlanetPoints);
        planetPointsService.savePoints(destinationPlanetPoints);
    }
}
