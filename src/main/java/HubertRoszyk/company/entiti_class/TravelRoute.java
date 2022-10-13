package HubertRoszyk.company.entiti_class;

import HubertRoszyk.company.service.TimerEntityService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class TravelRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "departure_planet_planet_id")
    private Planet departurePlanet;
    @ManyToOne
    @JoinColumn(name = "arrival_planet_planet_id")
    private Planet arrivalPlanet;

    @ManyToOne
    @JoinColumn(name = "ship_id")
    private Ship ship;

    private int routeStartingCycle;
    private int routeCyclesDuration;
    private int routeEndingCycle;

    public TravelRoute(Planet departurePlanet, Planet arrivalPlanet, Ship ship, TimerEntityService timerEntityService) {
        this.departurePlanet = departurePlanet;
        this.arrivalPlanet = arrivalPlanet;
        this.ship = ship;

        TimerEntity timerEntity = timerEntityService.getTimerEntityByGalaxyId(this.departurePlanet.getGalaxy().getId());
        routeStartingCycle = timerEntity.getCyclesNum();

        double distance = Math.sqrt(
                Math.pow ((
                        arrivalPlanet.getPlanetLocationX() - departurePlanet.getPlanetLocationX()
                ), 2)  + Math.pow ((
                        arrivalPlanet.getPlanetLocationY() - departurePlanet.getPlanetLocationY()
                ), 2)
        );

        System.out.println("dist");
        System.out.println(distance);

        int routeCyclesDuration =  ((int)distance / ship.getSpeed());

        System.out.println(routeCyclesDuration);

        this.routeCyclesDuration = routeCyclesDuration;
        this.routeEndingCycle = routeCyclesDuration + routeStartingCycle;
    }
    public TravelRoute(Planet departurePlanet, Planet arrivalPlanet, Ship ship, int routeCyclesDuration, TimerEntityService timerEntityService) {
        this.departurePlanet = departurePlanet;
        this.arrivalPlanet = arrivalPlanet;
        this.ship = ship;

        TimerEntity timerEntity = timerEntityService.getTimerEntityByGalaxyId(this.departurePlanet.getGalaxy().getId());
        routeStartingCycle = timerEntity.getCyclesNum();

        this.routeCyclesDuration = routeCyclesDuration;
        this.routeEndingCycle = routeCyclesDuration + routeStartingCycle;
    }
}
