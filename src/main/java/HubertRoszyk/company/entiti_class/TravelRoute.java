package HubertRoszyk.company.entiti_class;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    TravelRoute(Planet departurePlanetId, Planet arrivalPlanetId, Ship ship) {
        this.departurePlanet = departurePlanetId;
        this.arrivalPlanet = arrivalPlanetId;
        this.ship = ship;

        
    }
}
