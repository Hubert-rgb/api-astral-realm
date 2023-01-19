package HubertRoszyk.company.controller;

import HubertRoszyk.company.entiti_class.GalaxyPoints;
import HubertRoszyk.company.enumStatus.SciencePurchaseStatus;
import HubertRoszyk.company.enumTypes.cardsType.CardType;
import HubertRoszyk.company.enumTypes.cardsType.ScienceCardType;
import HubertRoszyk.company.service.GalaxyPointsService;
import HubertRoszyk.company.strategy.scienceCardExecutionStrategy.economyCards.*;
import HubertRoszyk.company.strategy.scienceCardExecutionStrategy.ScienceCardExecutionContext;
import HubertRoszyk.company.strategy.scienceCardExecutionStrategy.militaryCards.*;
import HubertRoszyk.company.strategy.scienceCardExecutionStrategy.politicalCards.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/science_cards_purchase")
public class ScienceCardsPurchaseController {
    @Autowired
    GalaxyPointsService galaxyPointsService;

    @GetMapping()
    public List<Enum> getAllScienceCardTypes(){
        List<Enum> scienceCardTypeEnumValues = new ArrayList<Enum>(EnumSet.allOf(ScienceCardType.class));
        return scienceCardTypeEnumValues;
    }

    @PostMapping()
    public SciencePurchaseStatus executePurchase(@RequestBody JSONObject jsonInput){
        int userId = (int) jsonInput.get("userId");
        int galaxyId = (int) jsonInput.get("galaxyId");
        String scienceCardTypeString = (String) jsonInput.get("scienceCardType");

        ScienceCardType scienceCardType = ScienceCardType.valueOf(scienceCardTypeString);
        GalaxyPoints galaxyPoints = galaxyPointsService.getPointsByUserIdAndGalaxyId(userId, galaxyId);

        double price = 15 + (4 * (galaxyPoints.getEconomicCardsNumber() + galaxyPoints.getPoliticalCardsNumber() + galaxyPoints.getMilitaryCardsNumber()));
        double gotSciencePoints = galaxyPoints.getSciencePoints();

        //getting conditions
        boolean isEnoughPoints = gotSciencePoints >= price;
        boolean isCardNotOwned = getIsCardNotOwned(scienceCardType, galaxyPoints);
        boolean isAllRequiredCards = getIsAllRequiredCards(scienceCardType, galaxyPoints);

        //checking if all conditions are true
        if (isEnoughPoints && isCardNotOwned && isAllRequiredCards){
            purchaseOk(price, scienceCardType, galaxyPoints, galaxyId, userId);
            return SciencePurchaseStatus.OK;
        } else if (!isEnoughPoints){
            return SciencePurchaseStatus.NOT_ENOUGH_POINTS;
        } else if (!isAllRequiredCards) {
            return SciencePurchaseStatus.LACK_OF_REQUIRED_CARDS;
        } else {
            return SciencePurchaseStatus.ALREADY_OWNS_THE_CARS;
        }
    }
    private void purchaseOk(double price, ScienceCardType scienceCardType, GalaxyPoints galaxyPoints, int galaxyId, int userId){
        //charging science points after the payment
        double gotSciencePoints = galaxyPoints.getSciencePoints();
        double setSciencePoints = gotSciencePoints - price;
        galaxyPoints.setSciencePoints(setSciencePoints);

        //adding card to set of cards
        Set<ScienceCardType> scienceCardTypeSet = galaxyPoints.getScienceCards();
        scienceCardTypeSet.add(scienceCardType);
        galaxyPoints.setScienceCards(scienceCardTypeSet);

        //adding card to total number of cards of certain type
        CardType cardType = scienceCardType.getCardType();
        switch (cardType){
            case ECONOMY -> galaxyPoints.setEconomicCardsNumber(galaxyPoints.getEconomicCardsNumber() + 1);
            case MILITARY -> galaxyPoints.setMilitaryCardsNumber(galaxyPoints.getMilitaryCardsNumber() + 1);
            case POLITICAL -> galaxyPoints.setPoliticalCardsNumber(galaxyPoints.getPoliticalCardsNumber() + 1);
        }
        executeCard(scienceCardType, galaxyId, userId);
        //saving galaxy Points
        galaxyPointsService.savePoints(galaxyPoints);
    }
    private void executeCard(ScienceCardType scienceCardType, int galaxyId, int userId){
        ScienceCardExecutionContext context = new ScienceCardExecutionContext();

        switch (scienceCardType){
            case INDUSTRY_CARGO_SHIP -> context.setStrategy(new IndustryCargoShipCardExecution());
            case PEACE -> context.setStrategy(new PeaceExecution());
            case TRADE -> context.setStrategy(new TradeExecution());
            case ALLIANCE -> context.setStrategy(new AllianceExecution());
            case CHEAPER_ARMY -> context.setStrategy(new CheaperArmyExecution());
            case BUILDS_FASTER -> context.setStrategy(new BuildsFasterExecution());
            case FASTER_ARMY_SHIP -> context.setStrategy(new FasterArmyShipExecution());
            case BATTLE_USERS_PLANETS -> context.setStrategy(new BattleUsersPlanetsExecution());
            case FASTER_INDUSTRY_SHIP -> context.setStrategy(new FasterIndustryShipExecution());
            case PILLAGE_USERS_PLANETS -> context.setStrategy(new PillageUsersPlanetsExecution());
            case LONGER_PROTECTION_PERIOD -> context.setStrategy(new LongerProtectionPeriodExecution());
            case SCIENCE_PRODUCE_INCREASE -> context.setStrategy(new ScienceProduceIncreaseExecution());
            case INDUSTRY_PRODUCE_INCREASE -> context.setStrategy(new IndustryProduceIncreaseExecution());
            case COLONISATION_INHABITED_PLANETS -> context.setStrategy(new ColonisationInhabitedPlanets());
            case COLONISATION_UNINHABITED_PLANETS -> context.setStrategy(new ColonisationUninhabitedPlanetsExecution());
        }
        context.executeStrategy(galaxyPointsService, galaxyId, userId);
    }
    private boolean getIsCardNotOwned(ScienceCardType scienceCardType, GalaxyPoints galaxyPoints){
        if (galaxyPoints.getScienceCards().contains(scienceCardType)) {
            return false;
        }
        return true;
    }
    private boolean getIsAllRequiredCards(ScienceCardType scienceCardType, GalaxyPoints galaxyPoints){
        CardType cardType = scienceCardType.getCardType();

        boolean isAllRequiredEconomyCards = galaxyPoints.getEconomicCardsNumber() >= scienceCardType.getEconomyCardsRequired();
        boolean isAllRequiredMilitaryCards = galaxyPoints.getMilitaryCardsNumber() >= scienceCardType.getMilitaryCardsRequired();
        boolean isAllRequiredPoliticalCards = galaxyPoints.getPoliticalCardsNumber() >= scienceCardType.getPoliticalCardsRequired();

        if (isAllRequiredEconomyCards && isAllRequiredPoliticalCards && isAllRequiredMilitaryCards){
            return true;
        }
        return false;
    }

}
