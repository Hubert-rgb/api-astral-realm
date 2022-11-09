package HubertRoszyk.company.controller.movingResources;

import HubertRoszyk.company.RandomDraw;
import HubertRoszyk.company.configuration.GameProperties;
import HubertRoszyk.company.entiti_class.Attack;
import HubertRoszyk.company.entiti_class.Planet;
import HubertRoszyk.company.entiti_class.PlanetPoints;
import HubertRoszyk.company.service.BattleService;
import HubertRoszyk.company.service.PlanetPointsService;
import HubertRoszyk.company.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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

    public String battle(int attackId) {
        Attack attack = battleService.getBattleById(battleId);
        System.out.println(attack);
        System.out.println(battleId);

        Planet attackPlanet = planetService.getPlanetById(attackPlanetId);
        Planet defensePlanet = planetService.getPlanetById(defensePlanetId);

        PlanetPoints attackPlanetPoints = planetPointsService.getPointsByPlanetId(attackPlanetId);
        PlanetPoints defensePlanetPoints = planetPointsService.getPointsByPlanetId(defensePlanetId);

        double attackPoints = attackPlanetPoints.getAttackPoints();
        double defensePoints = defensePlanetPoints.getDefensePoints();
        double defencePlanetArmy = defensePlanetPoints.getAttackPoints();

        double attackMultiplier = RandomDraw.battleMultiplierDraw();
        double defenceMultiplier = RandomDraw.battleMultiplierDraw();

        double battleAttackPoints = attackPoints * gameProperties.getAttackMultiplier() * attackMultiplier;
        double battleDefensePoints = defensePoints + defencePlanetArmy * gameProperties.getDefenceMultiplier() * defenceMultiplier;

        if (battleAttackPoints > battleDefensePoints) {
            defensePlanet.asignUser(attackPlanet.getUser());

            double setDefencePlanetAttackPoints = attackPoints - (defensePoints * 2);

            attackPlanetPoints.setAttackPoints(0);
            defensePlanetPoints.setDefensePoints(0); //to chyba nie będzie za bardzo zrównoważone
            defensePlanetPoints.setAttackPoints(setDefencePlanetAttackPoints);

            planetPointsService.savePoints(attackPlanetPoints);
            planetPointsService.savePoints(defensePlanetPoints);

            attack.setStatus("attack won");
            battleService.saveBattle(attack);
            return "attack won";
        } else {
            double setDefencePoints =  defensePoints - (attackPoints / 2);
            defensePlanetPoints.setDefensePoints(setDefencePoints);

            attackPlanetPoints.setAttackPoints(0);

            planetPointsService.savePoints(attackPlanetPoints);
            planetPointsService.savePoints(defensePlanetPoints);

            attack.setStatus("attack lost");
            battleService.saveBattle(attack);
            return  "attack lost";
        }
    }
}
