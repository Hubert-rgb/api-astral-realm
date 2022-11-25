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


    /** mniej więcej działą */
    //DONE TODO ustawiać armię też na statku
    //TODO statek powinien albo wpływać do portu jak jest miejsce albo wracać
    //TO DISCUSS TODO jak armia jest pakowana z powrotem na statek / do baraków na planecie po wygranej
    //DONE TODO najlepsze dewizje zostają w barakach na planecie, gorsze są pakowane na statki które nie musza wracać, a najgorsze na te co wracają (najgorsze statki)
    public String battle(int attackId) {
        Attack attack = battleService.getBattleById(attackId);

        //attack - ships and their load
        //defence - from planet points

        Planet defencePlanet = attack.getDefencePlanet();
        PlanetPoints defencePlanetPoints = planetPointsService.getPointsByPlanetId(defencePlanet.getId());
        PlanetPoints attackPlanetPoints = planetPointsService.getPointsByPlanetId(attack.getAttackPlanetId());

        int attackPoints = attack.getAttackArmyValue();
        int defencePoints = defencePlanetPoints.getDefensePoints();
        int defencePlanetArmyValue = ArmyController.getArmyValue(defencePlanetPoints.getArmy());

        double attackMultiplier = RandomDraw.battleMultiplierDraw();
        double defenceMultiplier = RandomDraw.battleMultiplierDraw();

        double battleAttackPoints = attackPoints * gameProperties.getAttackMultiplier() * attackMultiplier;
        double battleDefencePoints = defencePoints + defencePlanetArmyValue * gameProperties.getDefenceMultiplier() * defenceMultiplier;

        double attackArmySize = attack.getAttackArmySize();
        double defenceArmySize = ArmyController.getArmySize(defencePlanetPoints.getArmy());

        double armyRatio = (attackArmySize / (attackArmySize + defenceArmySize)) * 100;

        Set<AttackShip> attackShips = attack.getAttackShips();

        if (battleAttackPoints > battleDefencePoints) {
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
                loadArmyAndSendShips(setAttackArmy, attackShips, defencePlanetPoints, attackPlanetPoints);

            } else if (armyRatio >= 30 && armyRatio < 50) {
                double percentage = 0.7;
                setAttackArmy = subtractArmy(gotAttackArmy, attackArmySize, percentage);
                System.out.println("30:70");
                loadArmyAndSendShips(setAttackArmy, attackShips, defencePlanetPoints, attackPlanetPoints);

            } else if (armyRatio >= 50 && armyRatio < 70){
                double percentage = 0.4;
                setAttackArmy = subtractArmy(gotAttackArmy, attackArmySize, percentage);
                System.out.println(attack.getArmy());
                System.out.println("50:50");
                loadArmyAndSendShips(setAttackArmy, attackShips, defencePlanetPoints, attackPlanetPoints);

            } else if (armyRatio >= 70 && armyRatio < 90){
                double percentage = 0.2;
                setAttackArmy = subtractArmy(gotAttackArmy, attackArmySize, percentage);
                System.out.println(attack.getArmy());
                System.out.println("70:30");
                loadArmyAndSendShips(setAttackArmy, attackShips, defencePlanetPoints, attackPlanetPoints);

            } else if (armyRatio >= 90 && armyRatio <= 100) {
                double percentage = 0.1;
                setAttackArmy = subtractArmy(gotAttackArmy, attackArmySize, percentage);
                System.out.println(attack.getArmy());
                System.out.println("90:10");
                loadArmyAndSendShips(setAttackArmy, attackShips, defencePlanetPoints, attackPlanetPoints);

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
    //TODO albo zrobię stack i będę dodawał tam gdzie bedzie miał iść,
    // albo będę musiał mieć więcej warunków żeby dodawać część dywizji do jednego miejsca a resztę gdzieś indziej
    private void loadArmyAndSendShips(Map<Integer, Integer> army, Set<AttackShip> shipsSet, PlanetPoints defencePlanetPoints, PlanetPoints attackPlanetPoints){
        int leftSpaceInArmyBuilding = defencePlanetPoints.getTotalAttackBuildingSize();
        Map<Integer, Integer> barrackArmy = ArmyController.getEmptyArmy();
        List<AttackShip> ships = new ArrayList<>(shipsSet);

        for (AttackShip ship: ships){
            ship.setShipLoad(ArmyController.getEmptyArmy());
        }

        ships.sort(Comparator.comparing(Ship::getShipCapacity).reversed());



        /** rekurencyjnie */
        /*for (int i = army.size() - 1; i >= 0; i--){
            if (leftSpaceInArmyBuilding - army.get(i) >= 0){
                Map<Integer, Integer> singleMap = new HashMap<>();
                singleMap.put(i, army.get(i));
                ArmyController.combineArmy(barrackArmy, singleMap);
                leftSpaceInArmyBuilding -= army.get(i);
            } else if (leftSpaceInArmyBuilding == 0){
                for (AttackShip ship: ships){

                    if (ship.getShipCapacity() - ship.getShipLoad() >= army.get(i)){

                    } else {

                    }
                }
            } else {

            }
        }*/

        /** dość dużo operacji żeby stworzyć stack a potem jest łatwo */

        Stack<Integer> armyStack = armyMapToStack(army);
        int shipNum = 0;
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
            System.out.println(leftSpaceInArmyBuilding);
            System.out.println(singleMap);
            System.out.println("s" + armyStack.size());
        }

        defencePlanetPoints.setArmy(barrackArmy);

        planetPointsService.savePoints(defencePlanetPoints);

        int leftSpaceInHarbour = defencePlanetPoints.getTotalHarbourSize() - defencePlanetPoints.getTotalHarbourLoad();
        for (AttackShip ship: ships){
            if (leftSpaceInHarbour > 0) {
                changeShipHarbour(ship.getTravelRoute().get(ship.getTravelRoute().size() - 2).getArrivalPlanet().getId(), defencePlanetPoints.getPlanet().getId());
                leftSpaceInHarbour -= 1;
            } else {
                TimerEntity timerEntity = timerEntityService.getTimerEntityByGalaxyId(ship.getCurrentPlanet().getGalaxy().getId());

                TravelRoute travelRoute = new TravelRoute(defencePlanetPoints.getPlanet(), attackPlanetPoints.getPlanet(), ship, timerEntityService);
                TimerAction timerAction = new TimerAction(TimerActionType.INDUSTRY_CARGO, travelRoute.getRouteEndingCycle(), ship.getId(), timerEntity);

                travelRouteService.saveTravelRoutes(travelRoute);
                timerActionService.saveTimerAction(timerAction);
            }
            shipService.saveShip(ship);
        }
    }
    private Stack<Integer> armyMapToStack(Map<Integer, Integer> army){
        Stack<Integer> stack = new Stack<>();
        for (int i = 1; i < army.size(); i++){
            for (int j = 0; j < army.get(i); j++){
                int armyDev = army.get(i);
                stack.add(armyDev);
            }
        }
        return stack;
    }
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
