package HubertRoszyk.company.enumTypes;

import lombok.Getter;

@Getter
public enum ShipType {
    INDUSTRY_CARGO(20, 10, 4),
    ATTACK_CARGO(15, 15, 5);

    private int capacity;
    private final int shipPrice;
    private final int levelNums;

    ShipType(int capacity, int shipPrice, int levelNums) {
        this.capacity = capacity;
        this.shipPrice = shipPrice;
        this.levelNums = levelNums;
    }
}
