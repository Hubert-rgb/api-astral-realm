package HubertRoszyk.company.Strategy.timerActionStategy;

import HubertRoszyk.company.entiti_class.Planet;
import HubertRoszyk.company.entiti_class.TimerAction;
import HubertRoszyk.company.enumStatus.PlanetStatus;
import HubertRoszyk.company.service.PlanetService;

public class TimerActionPlanetStatusAfterAttack implements TimerActionStrategy{
    private final PlanetService planetService;

    public TimerActionPlanetStatusAfterAttack(PlanetService planetService){
        this.planetService = planetService;
    }
    @Override
    public void executeAction(TimerAction timerAction) {
        int planetId = timerAction.getExecutionId();
        Planet planet = planetService.getPlanetById(planetId);
        planet.setPlanetStatus(PlanetStatus.CLAIMED);
        planetService.savePlanet(planet);
    }
}
