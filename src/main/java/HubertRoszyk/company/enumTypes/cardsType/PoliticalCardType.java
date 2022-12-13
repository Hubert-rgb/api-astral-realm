package HubertRoszyk.company.enumTypes.cardsType;

import lombok.Getter;

@Getter
public enum PoliticalCardType {
    POLITICS_BUILDING(0, 0, 0),
    NN(0, 0, 1),
    TRADE(0, 1, 1),
    PEACE(0 ,0, 2),
    ALLIANCE(0, 0, 3);

    private final int militaryCardsRequired;
    private final int economyCardsRequired;
    private final int politicalCardsRequired;
    PoliticalCardType(int militaryCardsRequired, int economyCardsRequired, int politicalCardsRequired){
        this.militaryCardsRequired = militaryCardsRequired;
        this.economyCardsRequired = economyCardsRequired;
        this.politicalCardsRequired = politicalCardsRequired;
    }
}
