package HubertRoszyk.company;

import HubertRoszyk.company.entiti_class.PlanetLocation;
import HubertRoszyk.company.enumTypes.PlanetType;

import java.util.Random;

public class RandomDraw {
    private static Random random = new Random();

    /** REDO */
    public static PlanetLocation locationDraw() {

        int xLocation = random.nextInt(1920) + 1;
        int yLocation = random.nextInt(1080) + 1;

        PlanetLocation planetLocation = new PlanetLocation(xLocation, yLocation);
        return planetLocation;
    }
    public static int sizeDraw(PlanetType planetType) {
        int size;
        size = random.nextInt(planetType.getMaximalSize() - 1) + 2; //do config

        return size;
    }
    public static int industryPointsMultiplierDraw(int totalPoints) {
        return random.nextInt(totalPoints - 2) + 2;
    }
    public static double attackBattleMultiplierDraw() {
        double battleMultiplierInt = random.nextInt(5) + 8; //range 8 - 12
        return battleMultiplierInt / 10;
    }
    public static double defenceBattleMultiplierDraw() {
        double battleMultiplierInt = random.nextInt(3) + 1; //range 1 - 3
        double battleMultiplierDecimal = battleMultiplierInt / 10;
        return battleMultiplierDecimal + 1;
    }
    public static double armyDivisionNumberDraw(){
        double armyDivisions = random.nextInt(3) + 1; //range 1 - 3
        double armyDivisionsPercentage = armyDivisions / 10;
        return (1 + armyDivisionsPercentage);
    }
}
