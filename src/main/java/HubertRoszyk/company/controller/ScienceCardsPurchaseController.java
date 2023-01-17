package HubertRoszyk.company.controller;

import HubertRoszyk.company.entiti_class.GalaxyPoints;
import HubertRoszyk.company.enumStatus.IndustryPurchaseStatus;
import HubertRoszyk.company.enumStatus.SciencePurchaseStatus;
import HubertRoszyk.company.enumTypes.BuildingType;
import HubertRoszyk.company.enumTypes.cardsType.CardType;
import HubertRoszyk.company.enumTypes.cardsType.ScienceCardType;
import HubertRoszyk.company.service.GalaxyPointsService;
import HubertRoszyk.company.strategy.scienceCardExecutionStrategy.IndustryCargoShipCardExecution;
import HubertRoszyk.company.strategy.scienceCardExecutionStrategy.ScienceCardExecutionContext;
import org.json.simple.JSONObject;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;
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

        double price = scienceCardType.getPrice();
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
