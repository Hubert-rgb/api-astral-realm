package HubertRoszyk.company.enumTypes.cardsType;

import lombok.Getter;

@Getter
public enum EconomyCardType {
    INDUSTRY_CARGO_SHIP(0, 0, 0),
    BUILDS_FASTER(0, 1, 0),
    FASTER_INDUSTRY_CARGO_SHIP(0, 1, 0),
    INDUSTRY_PRODUCE_INCREASE(0, 2, 0),
    SCIENCE_PRODUCE_INCREASE(0, 2, 0);

    private final int militaryCardsRequired;
    private final int economyCardsRequired;
    private final int politicalCardsRequired;
    EconomyCardType(int militaryCardsRequired, int economyCardsRequired, int politicalCardsRequired){
        this.militaryCardsRequired = militaryCardsRequired;
        this.economyCardsRequired = economyCardsRequired;
        this.politicalCardsRequired = politicalCardsRequired;
    }
}
