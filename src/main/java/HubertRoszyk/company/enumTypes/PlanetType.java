package HubertRoszyk.company.enumTypes;

import lombok.Getter;

@Getter
public enum PlanetType {
    SMALL(3, 5, 10, 1, 0.5, 3, 25),
    MEDIUM(4, 7, 15, 1.5, 0.75, 4, 30),
    BIG(6, 9, 20, 2, 1, 5, 35);

    private final int maximalSize;
    private final int randomVariablesSum;
    private final int defaultDefencePoints;
    private final double defaultIndustryProduce;
    private final double defaultScienceProduce;
    private final int defaultHarbourSize;

    private final int defaultStorageSize;

    PlanetType(int maximalSize,
               int randomVariablesSum,
               int defaultDefencePoints,
               double defaultIndustryProduce,
               double defaultScienceProduce,
               int defaultHarbourSize,
               int defaultStorageSize) {

        this.maximalSize = maximalSize;
        this.randomVariablesSum = randomVariablesSum;
        this.defaultDefencePoints = defaultDefencePoints;
        this.defaultIndustryProduce = defaultIndustryProduce;
        this.defaultScienceProduce = defaultScienceProduce;
        this.defaultHarbourSize = defaultHarbourSize;
        this.defaultStorageSize = defaultStorageSize;
    }
}