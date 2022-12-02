package HubertRoszyk.company.controller.movingResources;

import HubertRoszyk.company.RandomDraw;
import HubertRoszyk.company.configuration.GameProperties;
import HubertRoszyk.company.controller.ArmyController;
import HubertRoszyk.company.entiti_class.*;
import HubertRoszyk.company.entiti_class.ship.AttackShip;
import HubertRoszyk.company.entiti_class.ship.IndustryShip;
import HubertRoszyk.company.entiti_class.ship.Ship;
import HubertRoszyk.company.enumTypes.TimerActionType;
import HubertRoszyk.company.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class PillageController implements AttackController{
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

    @Autowired
    GameProperties gameProperties;

    @Override
    public Map<Integer, Integer> subtractArmy(Map<Integer, Integer> army, double attackArmySize, double percentage) {
        double pillagePercentage = percentage / 2;
        int deadArmySize = (int) Math.ceil(attackArmySize * pillagePercentage);
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

    @Override
    public void loadAndSendShips(Map<Integer, Integer> army, Set<AttackShip> attackShipSet, Set<IndustryShip> industryShipsSet, PlanetPoints defencePlanetPoints) {
        //creating empty army in every ship
        List<AttackShip> attackShips = new ArrayList<>(attackShipSet);
        List<IndustryShip> industryShips = new ArrayList<>(industryShipsSet);



        for (AttackShip ship: attackShips){
            ship.setShipLoad(ArmyController.getEmptyArmy());
        }
        //sorting by the capacity
        attackShips.sort(Comparator.comparing(Ship::getShipCapacity).reversed());
        industryShips.sort(Comparator.comparing(Ship::getShipCapacity).reversed());

        List<AttackShip> loadedWithArmyShips = addingArmyToBarracksAndShips(defencePlanetPoints, army, attackShips); // would be different

        //To Ask TODO ile punktów przejmuje
        int industryPointsToPack = (int) Math.ceil(defencePlanetPoints.getIndustryPoints() / 4);
        int industryShipsLeftCapacity = 0;
        int attackShipsLeftCapacity = 0;

        for(AttackShip ship: loadedWithArmyShips){
            attackShipsLeftCapacity += ship.getShipCapacity() - ship.getShipLoadSize();
        }
        for(IndustryShip ship: industryShips){
            industryShipsLeftCapacity += ship.getShipCapacity() - ship.getShipLoad();
        }

        int shipNumber = 0;
        while(industryPointsToPack > 0 && industryShipsLeftCapacity > 0 && shipNumber < industryShips.size()){
            IndustryShip currentShip = industryShips.get(shipNumber);
            //TODO could be optimalised
            if (currentShip.getShipCapacity() - currentShip.getShipLoad() > 0){
                int gotShipLoad = currentShip.getShipLoad();
                int setShipLoad = gotShipLoad + 1;
                currentShip.setShipLoad(setShipLoad);

                industryShipsLeftCapacity -= 1;
                industryPointsToPack -= 1;
            } else {
                shipNumber += 1;
            }
        }
        shipNumber = 0;
        while(industryPointsToPack > 0 && attackShipsLeftCapacity > 0 && shipNumber < loadedWithArmyShips.size()){
            AttackShip currentShip = loadedWithArmyShips.get(shipNumber);
            //TODO could be optimalised
            if (currentShip.getShipCapacity() - currentShip.getShipLoadSize() > 0){
                int gotShipLoad = currentShip.getIndustryPointsShipLoad();
                int setShipLoad = gotShipLoad + 1;
                currentShip.setIndustryPointsShipLoad(setShipLoad);

                industryShipsLeftCapacity -= 1;
                industryPointsToPack -= 1;
            } else {
                shipNumber += 1;
            }
        }

        shipManagement(defencePlanetPoints, loadedWithArmyShips, industryShips);
    }

    @Override
    public List<AttackShip> addingArmyToBarracksAndShips(PlanetPoints defencePlanetPoints, Map<Integer, Integer> army, List<AttackShip> ships) {
        PlanetPointsService planetPointsService = getPlanetPointsService();

        Map<Integer, Integer> barrackArmy = ArmyController.getEmptyArmy();
        Stack<Integer> armyStack = ArmyController.armyMapToStack(army);
        int shipNum = 0;
        while(armyStack.size() > 0){
            int armyDev = armyStack.pop();
            Map<Integer, Integer> singleMap = ArmyController.getEmptyArmy();
            singleMap.put(armyDev, 1);
            AttackShip currentShip = ships.get(shipNum);

            if (currentShip.getShipCapacity() - ArmyController.getArmySize(currentShip.getShipLoad()) > 0){
                currentShip.setShipLoad(ArmyController.combineArmy(currentShip.getShipLoad(), singleMap));
            } else {
                shipNum += 1;
                Map<Integer, Integer> shipArmy = ArmyController.combineArmy(singleMap, currentShip.getShipLoad());
                currentShip.setShipLoad(shipArmy);
            }
        }
        defencePlanetPoints.setArmy(barrackArmy);
        planetPointsService.savePoints(defencePlanetPoints);

        return ships;
    }

    @Override
    public void shipManagement(PlanetPoints defencePlanetPoints, List<AttackShip> attackShips, List<IndustryShip> industryShips) {
        Planet departurePlanet = attackShips.get(0).getTravelRoute().get(attackShips.get(0).getTravelRoute().size() - 2).getArrivalPlanet();
        Planet defencePlanet = defencePlanetPoints.getPlanet();
        sendShipsBack(defencePlanet, departurePlanet, attackShips);
        sendShipsBack(defencePlanet, departurePlanet, industryShips);
    }
    private <S extends Ship> void sendShipsBack(Planet defencePlanet, Planet departurePlanet, List<S> ships) {
        for (Ship ship: ships){
            TimerEntity timerEntity = timerEntityService.getTimerEntityByGalaxyId(ship.getCurrentPlanet().getGalaxy().getId());

            TravelRoute travelRoute = new TravelRoute(defencePlanet, departurePlanet, ship, timerEntityService);
            TimerAction timerAction = new TimerAction(TimerActionType.INDUSTRY_CARGO, travelRoute.getRouteEndingCycle(), ship.getId(), timerEntity);

            travelRouteService.saveTravelRoutes(travelRoute);
            timerActionService.saveTimerAction(timerAction);
            shipService.saveShip(ship);
        }
    }

    @Override
    public boolean getBattleWin(PlanetPoints defencePlanetPoints, Attack attack) {
        int attackPoints = attack.getAttackArmyValue();
        int defencePoints = defencePlanetPoints.getDefensePoints();
        int defencePlanetArmyValue = ArmyController.getArmyValue(defencePlanetPoints.getArmy());

        double attackMultiplier = RandomDraw.attackBattleMultiplierDraw();
        double defenceMultiplier = RandomDraw.defenceBattleMultiplierDraw();

        double battleAttackPoints = attackPoints * gameProperties.getAttackPillageMultiplier() * attackMultiplier;
        double battleDefencePoints = defencePoints + defencePlanetArmyValue * gameProperties.getDefencePillageMultiplier() * defenceMultiplier;

        return battleAttackPoints > battleDefencePoints;
    }

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
