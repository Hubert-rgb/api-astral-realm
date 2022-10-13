package HubertRoszyk.company.enumTypes;

import lombok.Getter;

@Getter
public enum ShipType {
    INDUSTRY_CARGO(20, 200, 10, 4),
    ATTACK_CARGO(15, 300, 15, 5);

    private int capacity;
    private int speed;
    private final int shipPrice;
    private final int levelNums;

    ShipType(int capacity, int speed, int shipPrice, int levelNums) {
        this.capacity = capacity;
        this.speed = speed;
        this.shipPrice = shipPrice;
        this.levelNums = levelNums;
    }
}
