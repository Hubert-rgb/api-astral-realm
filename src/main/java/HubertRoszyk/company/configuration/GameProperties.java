package HubertRoszyk.company.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@PropertySource("game.properties")
public class GameProperties {
   /* @Value("${galaxyNum}")
    private int galaxyNum;*/

    @Value("${websiteLink}")
    private String websiteLink;

    @Value("${planetsNum}")
    private int planetsNum;
    @Value("${randomVariablesSum}")
    private int randomVariablesSum;
    @Value("${planetsSizes}")
    private int planetsSizes;
    @Value("${period}")
    private int period;
    @Value("${minDistanceBetweenPlanets}")
    private int minDistanceBetweenPlanets;
    @Value("${speed}")
    private int speed;

    @Value("${levelCostMultiplier}")
    private double levelCostMultiplier;

    @Value("${armyCost}")
    private int armyCost;

    @Value("${attackBattleMultiplier}")
    private double attackBattleMultiplier;
    @Value("${defenceBattleMultiplier}")
    private double defenceBattleMultiplier;

    @Value("${attackColonisationMultiplier}")
    private double attackColonisationMultiplier;
    @Value("${defenceColonisationMultiplier}")
    private double defenceColonisationMultiplier;

    @Value("${attackPillageMultiplier}")
    private double attackPillageMultiplier;
    @Value("${defencePillageMultiplier}")
    private double defencePillageMultiplier;
}
