package HubertRoszyk.company.entiti_class;

import lombok.Getter;

@Getter
final public class PlanetLocation {
    private final int xLocation,
    yLocation;
    public PlanetLocation(int xLocation, int yLocation) {
        this.xLocation = xLocation;
        this.yLocation = yLocation;
    }
}
