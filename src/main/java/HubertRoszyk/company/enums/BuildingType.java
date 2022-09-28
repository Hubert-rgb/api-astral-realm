package HubertRoszyk.company.enums;

import lombok.Getter;

@Getter
public enum BuildingType {
    INDUSTRY(11, 10, 1),
    SCIENCE(6, 20, 2),
    DEFENSE(4, 15, 1),
    ATTACK(4, 15, 2),
    STORAGE(10,15,25),
    SHIPYARD(3, 25, 0);

    private final int levelNums;
    private int buildingPrice;
    private int volume;
    BuildingType(int levelNums, int buildingPrice, int volume) {
        this.levelNums = levelNums;
        this.buildingPrice = buildingPrice;
        this.volume = volume;
    }
}
