package HubertRoszyk.company.enumTypes.cardsType;

import HubertRoszyk.company.converters.serialize.ScienceCardsSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

@JsonSerialize(using = ScienceCardsSerializer.class)
@Getter
public enum ScienceCardType {
    /** Economy cards */
    INDUSTRY_CARGO_SHIP(0, 0, 0, CardType.ECONOMY),
    BUILDS_FASTER(0, 1, 0, CardType.ECONOMY),
    FASTER_INDUSTRY_SHIP(0, 1, 0, CardType.ECONOMY),
    INDUSTRY_PRODUCE_INCREASE(0, 2, 0, CardType.ECONOMY),
    SCIENCE_PRODUCE_INCREASE(0, 2, 0, CardType.ECONOMY),
   // HIGHER_INDUSTRY_SHIPS_MAXIMAL_LEVEL(1, 2, 0, CardType.ECONOMY),

    /** Military cards */

    COLONISATION_UNINHABITED_PLANETS(0, 0, 0, CardType.MILITARY),
    FASTER_ARMY_SHIP(1, 0, 0, CardType.MILITARY),
    CHEAPER_ARMY(2, 0, 0, CardType.MILITARY),
    PILLAGE_USERS_PLANETS(2, 0, 1, CardType.MILITARY),
    BATTLE_USERS_PLANETS(3, 0 ,1, CardType.MILITARY),
  //  HIGHER_ARMY_SHIPS_MAXIMAL_LEVEL(2, 1, 0, CardType.MILITARY),

    /** Politician cards */

    COLONISATION_INHABITED_PLANETS(1, 0, 0, CardType.POLITICAL),
    LONGER_PROTECTION_PERIOD(0, 0, 1, CardType.POLITICAL),
    TRADE(0, 1, 1, CardType.POLITICAL),
    PEACE(0 ,1, 2, CardType.POLITICAL),
    ALLIANCE(1, 0, 3, CardType.POLITICAL);

    private final int militaryCardsRequired;
    private final int economyCardsRequired;
    private final int politicalCardsRequired;
    private final CardType cardType;
    ScienceCardType(
            int militaryCardsRequired,
            int economyCardsRequired,
            int politicalCardsRequired,
            CardType cardType
    ){
        this.militaryCardsRequired = militaryCardsRequired;
        this.economyCardsRequired = economyCardsRequired;
        this.politicalCardsRequired = politicalCardsRequired;
        this.cardType = cardType;
    }
}
