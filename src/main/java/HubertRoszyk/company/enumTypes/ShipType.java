package HubertRoszyk.company.enumTypes;

import lombok.Getter;

@Getter
public enum ShipType {
    INDUSTRY_CARGO(10, 4),
    ATTACK_CARGO(15, 5);

    private final int shipPrice;
    private final int levelNums;

    ShipType(int shipPrice, int levelNums) {
        this.shipPrice = shipPrice;
        this.levelNums = levelNums;
    }
}
