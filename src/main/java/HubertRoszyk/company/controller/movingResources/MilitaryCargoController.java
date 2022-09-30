package HubertRoszyk.company.controller.movingResources;

import HubertRoszyk.company.entiti_class.Battle;
import HubertRoszyk.company.entiti_class.PlanetPoints;
import HubertRoszyk.company.service.BattleService;
import HubertRoszyk.company.service.PlanetPointsService;
import org.springframework.beans.factory.annotation.Autowired;

public class MilitaryCargoController {
    @Autowired
    BattleService battleService;

    @Autowired
    PlanetPointsService planetPointsService;
    public String changeArmyPlanet(int giverPlanetId, int receiverPlanetId, int battleId) {
        Battle battle = battleService.getBattleById(battleId);

        PlanetPoints giverPlanetPoints = planetPointsService.getPointsByPlanetId(giverPlanetId);
        PlanetPoints receiverPlanetPoints = planetPointsService.getPointsByPlanetId(receiverPlanetId);

        double gotGiverAttackPoints = giverPlanetPoints.getAttackPoints();
        double gotReceiverAttackPoints = receiverPlanetPoints.getAttackPoints();

        double setGiverAttackPoints = 0;
        double setReceiverAttackPoints = gotReceiverAttackPoints + gotGiverAttackPoints;

        giverPlanetPoints.setAttackPoints(setGiverAttackPoints);
        receiverPlanetPoints.setAttackPoints(setReceiverAttackPoints);

        planetPointsService.savePoints(giverPlanetPoints);
        planetPointsService.savePoints(receiverPlanetPoints);

        battle.setStatus("army changed planet");
        battleService.saveBattle(battle);
        return "army changed planet";
    }
}
