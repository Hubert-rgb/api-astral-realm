package HubertRoszyk.company.controller.movingResources;

import HubertRoszyk.company.RandomDraw;
import HubertRoszyk.company.configuration.GameProperties;
import HubertRoszyk.company.controller.ArmyController;
import HubertRoszyk.company.controller.BuildingsController;
import HubertRoszyk.company.entiti_class.*;
import HubertRoszyk.company.entiti_class.ship.AttackShip;
import HubertRoszyk.company.entiti_class.ship.IndustryShip;
import HubertRoszyk.company.entiti_class.ship.Ship;
import HubertRoszyk.company.enumTypes.TimerActionType;
import HubertRoszyk.company.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Controller
@RestController
@RequestMapping("/battle-controller")
public class BattleController implements AttackController{
    @Autowired
    GameProperties gameProperties;

    @Override
    public Map<Integer, Integer> subtractArmy(Map<Integer, Integer> army, double attackArmySize, double percentage) {
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
    @Override
    public void loadAndSendShips(Map<Integer, Integer> army, Set<AttackShip> attackShipSet, Set<IndustryShip> industryShipsSet, PlanetPoints defencePlanetPoints) {
        //creating empty army in every ship
        List<AttackShip> ships = new ArrayList<>(attackShipSet);
        List<IndustryShip> industryShips = new ArrayList<>(industryShipsSet);

        for (AttackShip ship: ships){
            ship.setShipLoad(ArmyController.getEmptyArmy());
        }
        //sorting by the capacity
        ships.sort(Comparator.comparing(Ship::getShipCapacity).reversed());

        List<AttackShip> loadedShips = addingArmyToBarracksAndShips(defencePlanetPoints, army, ships); // would be different

        shipManagement(defencePlanetPoints, loadedShips, industryShips);
    }



    @Override
    public List<AttackShip> addingArmyToBarracksAndShips(PlanetPoints defencePlanetPoints, Map<Integer, Integer> army, List<AttackShip> ships) {
        PlanetPointsService planetPointsService = getPlanetPointsService();

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

        return ships;
    }

    @Override
    public void shipManagement(PlanetPoints defencePlanetPoints, List<AttackShip> ships, List<IndustryShip> industryShips) {
        TimerEntityService timerEntityService = getTimerEntityService();
        TravelRouteService travelRouteService = getTravelRouteService();
        TimerActionService timerActionService = getTimerActionService();
        ShipService shipService = getShipService();

        Planet departurePlanet = ships.get(0).getTravelRoute().get(ships.get(0).getTravelRoute().size() - 2).getArrivalPlanet();
        for (AttackShip ship: ships){
            if (defencePlanetPoints.getTotalHarbourLoad() > 0) {
                //if there is room for ship in harbour it changes harbour but doesn't leave planet
                BuildingsController.changeShipHarbour(departurePlanet.getId(), defencePlanetPoints.getPlanet().getId());
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


    @Override
    public boolean getBattleWin(PlanetPoints defencePlanetPoints, Attack attack) {
        int attackPoints = attack.getAttackArmyValue();
        int defencePoints = defencePlanetPoints.getDefensePoints();
        int defencePlanetArmyValue = ArmyController.getArmyValue(defencePlanetPoints.getArmy());

        double attackMultiplier = RandomDraw.battleMultiplierDraw();
        double defenceMultiplier = RandomDraw.battleMultiplierDraw();

        double battleAttackPoints = attackPoints * gameProperties.getAttackBattleMultiplier() * attackMultiplier;
        double battleDefencePoints = defencePoints + defencePlanetArmyValue * gameProperties.getDefenceBattleMultiplier() * defenceMultiplier;

        return battleAttackPoints > battleDefencePoints;
    }


    @Autowired
    BattleService battleService;

    @Autowired
    PlanetService planetService;

    @Autowired
    PlanetPointsService planetPointsService;

    @Autowired
    ShipService shipService;

    @Autowired
    TravelRouteService travelRouteService;

    @Autowired
    TimerEntityService timerEntityService;

    @Autowired
    TimerActionService timerActionService;

    @Override
    public BattleService getBattleService() {
        return battleService;
    }

    @Override
    public PlanetService getPlanetService() {
        return planetService;
    }

    @Override
    public PlanetPointsService getPlanetPointsService() {
        return planetPointsService;
    }

    @Override
    public ShipService getShipService() {
        return shipService;
    }

    @Override
    public TravelRouteService getTravelRouteService() {
        return travelRouteService;
    }

    @Override
    public TimerEntityService getTimerEntityService() {
        return timerEntityService;
    }

    @Override
    public TimerActionService getTimerActionService() {
        return timerActionService;
    }
}
