package HubertRoszyk.company.controller.movingResources;

import HubertRoszyk.company.controller.ArmyController;
import HubertRoszyk.company.entiti_class.*;
import HubertRoszyk.company.entiti_class.ship.AttackShip;
import HubertRoszyk.company.entiti_class.ship.IndustryShip;
import HubertRoszyk.company.entiti_class.ship.Ship;
import HubertRoszyk.company.enumStatus.PlanetStatus;
import HubertRoszyk.company.service.*;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public interface AttackController {
    /** method executed while army movement is attack */
    default String attack(int attackId) {
        BattleService battleService = getBattleService();
        PlanetPointsService planetPointsService = getPlanetPointsService();
        PlanetService planetService = getPlanetService();
        ShipService shipService = getShipService();

        Attack attack = battleService.getBattleById(attackId);

        Planet defencePlanet = attack.getDefencePlanet();
        PlanetPoints defencePlanetPoints = planetPointsService.getPointsByPlanetId(defencePlanet.getId());

        //attack - army (Map)
        //defence - army from defence planet (Map) and defence as planet points (int) variable


        double attackArmySize = attack.getAttackArmySize();
        double defenceArmySize = ArmyController.getArmySize(defencePlanetPoints.getArmy());

        //army ratio is defined by number of army division (not their power)
        //TO ASK TODO army ratio at defence???
        double armyRatio = (attackArmySize / (attackArmySize + defenceArmySize)) * 100;

        Set<AttackShip> attackShips = attack.getAttackShips();
        Set<IndustryShip> industryShips = attack.getIndustryShips();

        Boolean attackWin = getBattleWin(defencePlanetPoints, attack);

        if (attackWin) {
            /** attack won */

            defencePlanet.asignUser(attack.getUser());
            defencePlanetPoints.setArmy(defencePlanetPoints.getEmptyArmy());
            defencePlanet.setPlanetStatus(PlanetStatus.AFTER_ATTACK);
            List<Ship> defencePlanetShip = shipService.getShipsByPlanetId(defencePlanet.getId());
            shipService.removeShipsList(defencePlanetShip);

            planetService.savePlanet(defencePlanet);

            Map<Integer, Integer> gotAttackArmy = attack.getArmy();
            Map<Integer, Integer> setAttackArmy;
            if(armyRatio > 0 && armyRatio < 30){
                double percentage = 0.9;
                setAttackArmy = subtractArmy(gotAttackArmy, attackArmySize, percentage);
                System.out.println("10:90");
                loadAndSendShips(setAttackArmy, attackShips, industryShips, defencePlanetPoints);

            } else if (armyRatio >= 30 && armyRatio < 50) {
                double percentage = 0.7;
                setAttackArmy = subtractArmy(gotAttackArmy, attackArmySize, percentage);
                System.out.println("30:70");
                loadAndSendShips(setAttackArmy, attackShips, industryShips, defencePlanetPoints);

            } else if (armyRatio >= 50 && armyRatio < 70){
                double percentage = 0.4;
                setAttackArmy = subtractArmy(gotAttackArmy, attackArmySize, percentage);
                System.out.println(attack.getArmy());
                System.out.println("50:50");
                loadAndSendShips(setAttackArmy, attackShips,industryShips, defencePlanetPoints);

            } else if (armyRatio >= 70 && armyRatio < 90){
                double percentage = 0.2;
                setAttackArmy = subtractArmy(gotAttackArmy, attackArmySize, percentage);
                System.out.println(attack.getArmy());
                System.out.println("70:30");
                loadAndSendShips(setAttackArmy, attackShips,industryShips, defencePlanetPoints);

            } else if (armyRatio >= 90 && armyRatio <= 100) {
                double percentage = 0.1;
                setAttackArmy = subtractArmy(gotAttackArmy, attackArmySize, percentage);
                System.out.println(attack.getArmy());
                System.out.println("90:10");
                loadAndSendShips(setAttackArmy, attackShips, industryShips, defencePlanetPoints);

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
            /** attack lost */
            attack.setArmy(ArmyController.getEmptyArmy());

            Map<Integer, Integer> gotDefenceArmy = defencePlanetPoints.getArmy();
            Map<Integer, Integer> setDefenceArmy;

            //deleting ships after lost battle
            //I can't cast it into Ship, so it's cause a problem while deleting list
            attack.setAttackShips(null);
            attack.setIndustryShips(null);
            for (AttackShip attackShip : attackShips) {
                shipService.deleteShipById(attackShip.getId());
            }
            for (IndustryShip industryShip : industryShips) {
                shipService.deleteShipById(industryShip.getId());
            }

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
    Map<Integer, Integer> subtractArmy(Map<Integer, Integer> army, double attackArmySize, double percentage);

    void loadAndSendShips(Map<Integer, Integer> army, Set<AttackShip> attackShipSet, Set<IndustryShip> industryShipsSet, PlanetPoints defencePlanetPoints);

    List<AttackShip> addingArmyToBarracksAndShips(PlanetPoints defencePlanetPoints, Map<Integer, Integer> army, List<AttackShip> ships);

    void shipManagement(PlanetPoints defencePlanetPoints, List<AttackShip> attackShips, List<IndustryShip> industryShips);

    boolean getBattleWin(PlanetPoints defencePlanetPoints, Attack attack);

    BattleService getBattleService();
    PlanetService getPlanetService();
    PlanetPointsService getPlanetPointsService();
    ShipService getShipService();
    TravelRouteService getTravelRouteService();
    TimerEntityService getTimerEntityService();
    TimerActionService getTimerActionService();
}
