package HubertRoszyk.company.enumTypes.cardsType;

import HubertRoszyk.company.converters.serialize.ScienceCardsSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

@JsonSerialize(using = ScienceCardsSerializer.class)
@Getter
public enum ScienceCardType {
    /** Economy cards */
    INDUSTRY_CARGO_SHIP(0, 0, 0, 10, CardType.ECONOMY),
    BUILDS_FASTER(0, 1, 0, 15, CardType.ECONOMY),
    FASTER_INDUSTRY_CARGO_SHIP(0, 1, 0, 17, CardType.ECONOMY),
    INDUSTRY_PRODUCE_INCREASE(0, 2, 0, 20, CardType.ECONOMY),
    SCIENCE_PRODUCE_INCREASE(0, 2, 0, 25, CardType.ECONOMY),

    /** Military cards */

    COLONISATION(0, 0, 0, 10, CardType.MILITARY),
    FASTER_ARMY(1, 0, 0, 15, CardType.MILITARY),
    CHEAPER_ARMY(2, 0, 0, 17, CardType.MILITARY),
    PILLAGE_USERS_PLANETS(2, 0, 0, 20, CardType.MILITARY),
    BATTLE_USERS_PLANETS(3, 0 ,0, 25, CardType.MILITARY),

    /** Politician cards */

    POLITICS_BUILDING(0, 0, 0, 10, CardType.POLITICAL),
    NN(0, 0, 1, 15, CardType.POLITICAL),
    TRADE(0, 1, 1, 17, CardType.POLITICAL),
    PEACE(0 ,0, 2, 20, CardType.POLITICAL),
    ALLIANCE(0, 0, 3, 25, CardType.POLITICAL);

    private final int militaryCardsRequired;
    private final int economyCardsRequired;
    private final int politicalCardsRequired;
    private final double price;
    private final CardType cardType;
    ScienceCardType(
            int militaryCardsRequired,
            int economyCardsRequired,
            int politicalCardsRequired,
            double price,
            CardType cardType
    ){
        this.militaryCardsRequired = militaryCardsRequired;
        this.economyCardsRequired = economyCardsRequired;
        this.politicalCardsRequired = politicalCardsRequired;
        this.price = price;
        this.cardType = cardType;
    }
}
