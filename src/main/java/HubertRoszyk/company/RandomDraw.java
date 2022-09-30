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
        int industryPointsMultiplier =  random.nextInt(totalPoints - 1) + 1;
        return industryPointsMultiplier;
    }
    public static double battleMultiplierDraw() {
        double battleMultiplierInt = random.nextInt(4) + 8; //range 8 - 11
        double battleMultiplier = battleMultiplierInt / 10;
        return battleMultiplier;
    }
}
