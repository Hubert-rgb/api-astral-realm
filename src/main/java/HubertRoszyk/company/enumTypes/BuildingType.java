package HubertRoszyk.company.enumTypes;

import HubertRoszyk.company.converters.serialize.BuildingTypeSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

@JsonSerialize(using = BuildingTypeSerialize.class)
@Getter
public enum BuildingType {
    INDUSTRY(11, 10, 1, 5), //volume is points produce
    SCIENCE(6, 20, 2, 8),
    DEFENSE(4, 15, 1,7), //volume is capacity
    ATTACK(4, 15, 2, 7),
    STORAGE(10,15,30,3),
    SHIP_YARD(4, 25, 2, 10),
    HARBOUR(5, 30, 3, 10);

    private final int levelNums;
    private final int buildingPrice;
    private final int volume;
    private final int constructionCycles;

    BuildingType( int levelNums,
                 int buildingPrice,
                  int volume,
                 int constructionCycles) {
        this.levelNums = levelNums;
        this.buildingPrice = buildingPrice;
        this.volume = volume;
        this.constructionCycles = constructionCycles;
    }
}
