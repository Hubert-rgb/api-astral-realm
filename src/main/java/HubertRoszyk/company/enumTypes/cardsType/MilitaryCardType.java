package HubertRoszyk.company.enumTypes.cardsType;

import lombok.Getter;

@Getter
public enum MilitaryCardType {
    COLONISATION(0, 0, 0),
    FASTER_ARMY(1, 0, 0),
    CHEAPER_ARMY(2, 0, 0),
    PILLAGE_USERS_PLANETS(2, 0, 0),
    BATTLE_USERS_PLANETS(3, 0 ,0);

    private final int militaryCardsRequired;
    private final int economyCardsRequired;
    private final int politicalCardsRequired;
    MilitaryCardType(int militaryCardsRequired, int economyCardsRequired, int politicalCardsRequired){
        this.militaryCardsRequired = militaryCardsRequired;
        this.economyCardsRequired = economyCardsRequired;
        this.politicalCardsRequired = politicalCardsRequired;
    }
}
