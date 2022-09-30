package HubertRoszyk.company.controller.movingResources;

import HubertRoszyk.company.RandomDraw;
import HubertRoszyk.company.entiti_class.Battle;
import HubertRoszyk.company.entiti_class.Planet;
import HubertRoszyk.company.entiti_class.PlanetPoints;
import HubertRoszyk.company.service.BattleService;
import HubertRoszyk.company.service.PlanetPointsService;
import HubertRoszyk.company.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;

public class BattleController {
    @Autowired
    BattleService battleService;

    @Autowired
    PlanetService planetService;

    @Autowired
    PlanetPointsService planetPointsService;

    public String battle(int attackPlanetId, int defensePlanetId, int battleId) {
        Battle battle = battleService.getBattleById(battleId);
        System.out.println(battle);
        System.out.println(battleId);

        Planet attackPlanet = planetService.getPlanetById(attackPlanetId);
        Planet defensePlanet = planetService.getPlanetById(defensePlanetId);

        PlanetPoints attackPlanetPoints = planetPointsService.getPointsByPlanetId(attackPlanetId);
        PlanetPoints defensePlanetPoints = planetPointsService.getPointsByPlanetId(defensePlanetId);

        double attackPoints = attackPlanetPoints.getAttackPoints();
        double defensePoints = defensePlanetPoints.getDefensePoints();

        double battleMultiplier = RandomDraw.battleMultiplierDraw();

        double battleAttackPoints = attackPoints * battleMultiplier;
        double battleDefensePoints = defensePoints * 2;

        if (battleAttackPoints > battleDefensePoints) {
            defensePlanet.asignUser(attackPlanet.getUser());

            double setDefencePlanetAttackPoints = attackPoints - (defensePoints * 2);

            attackPlanetPoints.setAttackPoints(0);
            defensePlanetPoints.setDefensePoints(0); //to chyba nie będzie za bardzo zrównoważone
            defensePlanetPoints.setAttackPoints(setDefencePlanetAttackPoints);

            planetPointsService.savePoints(attackPlanetPoints);
            planetPointsService.savePoints(defensePlanetPoints);

            battle.setStatus("attack won");
            battleService.saveBattle(battle);
            return "attack won";
        } else {
            double setDefencePoints =  defensePoints - (attackPoints / 2);
            defensePlanetPoints.setDefensePoints(setDefencePoints);

            attackPlanetPoints.setAttackPoints(0);

            planetPointsService.savePoints(attackPlanetPoints);
            planetPointsService.savePoints(defensePlanetPoints);

            battle.setStatus("attack lost");
            battleService.saveBattle(battle);
            return  "attack lost";
        }
    }
}
