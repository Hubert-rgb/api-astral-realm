package HubertRoszyk.company.controller.movingResources;

import HubertRoszyk.company.RandomDraw;
import HubertRoszyk.company.configuration.GameProperties;
import HubertRoszyk.company.entiti_class.Attack;
import HubertRoszyk.company.entiti_class.Planet;
import HubertRoszyk.company.entiti_class.PlanetPoints;
import HubertRoszyk.company.enumStatus.PlanetStatus;
import HubertRoszyk.company.service.BattleService;
import HubertRoszyk.company.service.PlanetPointsService;
import HubertRoszyk.company.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

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


    /** mniej więcej działą */
    //TODO zmieniać statusy
    //TODO wykonywać to w czasie
    //TODO ustawiać armię też na statku
    //TODO statek powinien albo wpływać do portu jak jest miejsce albo wracać
    //TO DISCUSS TODO jak armia jest pakowana z powrotem na statek / do baraków na planecie po wygranej
    public String battle(int attackId) {
        Attack attack = battleService.getBattleById(attackId);

        //attack - ships and their load
        //defence - from planet points

        Planet defencePlanet = attack.getDefencePlanet();
        PlanetPoints defencePlanetPoints = planetPointsService.getPointsByPlanetId(defencePlanet.getId());

        int attackPoints = attack.getAttackArmyValue();
        int defencePoints = defencePlanetPoints.getDefensePoints();
        int defencePlanetArmyValue = attack.getArmyValue(defencePlanetPoints.getArmy());

        double attackMultiplier = RandomDraw.battleMultiplierDraw();
        double defenceMultiplier = RandomDraw.battleMultiplierDraw();

        double battleAttackPoints = attackPoints * gameProperties.getAttackMultiplier() * attackMultiplier;
        double battleDefencePoints = defencePoints + defencePlanetArmyValue * gameProperties.getDefenceMultiplier() * defenceMultiplier;

        double attackArmySize = attack.getAttackArmySize();
        double defenceArmySize = attack.getArmySize(defencePlanetPoints.getArmy());

        double pointsRatio = (attackArmySize / (attackArmySize + defenceArmySize)) * 100;

        if (battleAttackPoints > battleDefencePoints) {
            defencePlanet.asignUser(attack.getUser());
            defencePlanetPoints.setArmy(defencePlanetPoints.getEmptyArmy());
            defencePlanet.setPlanetStatus(PlanetStatus.AFTER_ATTACK);

            Map<Integer, Integer> gotAttackArmy = attack.getArmy();
            Map<Integer, Integer> setAttackArmy;
            if(pointsRatio > 0 && pointsRatio < 30){
                double percentage = 0.9;
                setAttackArmy = subtractArmy(gotAttackArmy, attackArmySize, percentage);
                System.out.println("10:90");

            } else if (pointsRatio >= 30 && pointsRatio < 50) {
                double percentage = 0.7;
                setAttackArmy = subtractArmy(gotAttackArmy, attackArmySize, percentage);
                System.out.println("30:70");

            } else if (pointsRatio >= 50 && pointsRatio < 70){
                double percentage = 0.4;
                setAttackArmy = subtractArmy(gotAttackArmy, attackArmySize, percentage);
                System.out.println(attack.getArmy());
                System.out.println("50:50");

            } else if (pointsRatio >= 70 && pointsRatio < 90){
                double percentage = 0.2;
                setAttackArmy = subtractArmy(gotAttackArmy, attackArmySize, percentage);
                System.out.println(attack.getArmy());
                System.out.println("70:30");

            } else if (pointsRatio >= 90 && pointsRatio <= 100) {
                double percentage = 0.1;
                setAttackArmy = subtractArmy(gotAttackArmy, attackArmySize, percentage);
                System.out.println(attack.getArmy());
                System.out.println("90:10");
            } else if (pointsRatio > 100){
                setAttackArmy = new HashMap<>();
                System.out.println("ERR:wywaliło poza skalę");
            } else {
                setAttackArmy = new HashMap<>();
                System.out.println("ERR:nic nie działa");
            }

            attack.setArmy(setAttackArmy);
            attack.setStatus("attack won");
            battleService.saveBattle(attack);
            return "attack won";
        } else {
            attack.setArmy(attack.getEmptyArmy());

            Map<Integer, Integer> gotDefenceArmy = defencePlanetPoints.getArmy();
            Map<Integer, Integer> setDefenceArmy;
            if (pointsRatio <= 100 && pointsRatio > 70){
                double percentage = 0.45;
                setDefenceArmy = subtractArmy(gotDefenceArmy, defenceArmySize, percentage);
                System.out.println("10:90");

            } else if (pointsRatio <= 70 && pointsRatio > 50) {
                double percentage = 0.35;
                setDefenceArmy = subtractArmy(gotDefenceArmy, defenceArmySize, percentage);
                System.out.println("30:70");

            } else if (pointsRatio <= 50 && pointsRatio > 30){
                double percentage = 0.2;
                setDefenceArmy = subtractArmy(gotDefenceArmy, defenceArmySize, percentage);
                System.out.println("50:50");

            } else if (pointsRatio <= 30 && pointsRatio > 10){
                double percentage = 0.1;
                setDefenceArmy = subtractArmy(gotDefenceArmy, defenceArmySize, percentage);
                System.out.println("70:30");

            } else if (pointsRatio <= 10 && pointsRatio >= 0) {
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
}
