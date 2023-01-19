package HubertRoszyk.company.entiti_class;

import HubertRoszyk.company.controller.GalaxyPointsController;
import HubertRoszyk.company.entiti_class.ship.Ship;
import HubertRoszyk.company.service.GalaxyPointsService;
import HubertRoszyk.company.service.TimerEntityService;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class TravelRoute {
    @Autowired
    @Transient
    private GalaxyPointsService galaxyPointsService;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "departure_planet_planet_id")
    private Planet departurePlanet;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "arrival_planet_planet_id")
    private Planet arrivalPlanet;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
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

        TimerEntity timerEntity = timerEntityService.getTimerEntityByGalaxyId(this.arrivalPlanet.getGalaxy().getId());
        routeStartingCycle = timerEntity.getCyclesNum();

        double distance = getDistance();

        System.out.println("dist");
        System.out.println(distance);

        GalaxyPoints galaxyPoints = galaxyPointsService.getPointsByUserIdAndGalaxyId(ship.getUser().getId(), arrivalPlanet.getGalaxy().getId());

        int speed = ship.getShipType().getSpeed() + galaxyPoints.getIndustryShipSpeedAddition();
        int routeCyclesDuration =  ((int)distance / speed);

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
    private double getDistance(){
        double distance = Math.sqrt(
                Math.pow ((
                        arrivalPlanet.getPlanetLocationX() - departurePlanet.getPlanetLocationX()
                ), 2)  + Math.pow ((
                        arrivalPlanet.getPlanetLocationY() - departurePlanet.getPlanetLocationY()
                ), 2)
        );
        return distance;
    }
}
