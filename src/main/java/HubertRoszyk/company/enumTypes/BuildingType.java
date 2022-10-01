package HubertRoszyk.company.enumTypes;

import lombok.Getter;

@Getter
public enum BuildingType {
    INDUSTRY(11, 10, 1),
    SCIENCE(6, 20, 2),
    DEFENSE(4, 15, 1),
    ATTACK(4, 15, 2),
    STORAGE(10,15,30),
    SHIP_YARD(4, 25, 2),
    HARBOUR(5, 30, 3);

    private final int levelNums;
    private final int buildingPrice;
    private final int volume;
    BuildingType(int levelNums, int buildingPrice, int volume) {
        this.levelNums = levelNums;
        this.buildingPrice = buildingPrice;
        this.volume = volume;
    }
}
