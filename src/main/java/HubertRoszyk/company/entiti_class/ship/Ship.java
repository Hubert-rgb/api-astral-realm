package HubertRoszyk.company.entiti_class.ship;

import HubertRoszyk.company.entiti_class.Planet;
import HubertRoszyk.company.entiti_class.PlanetPoints;
import HubertRoszyk.company.entiti_class.TravelRoute;
import HubertRoszyk.company.entiti_class.User;
import HubertRoszyk.company.enumStatus.ShipStatus;
import HubertRoszyk.company.enumTypes.ShipType;
import HubertRoszyk.company.service.PlanetPointsService;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@NamedEntityGraph(
        name = "ship",
        attributeNodes = {
                @NamedAttributeNode(value = "travelRoute", subgraph = "ship.travelRoute")
        },
        subgraphs = {
                @NamedSubgraph(
                name = "ship.travelRoute",
                attributeNodes = {@NamedAttributeNode("arrivalPlanet")})
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
abstract public class Ship{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated
    private ShipType shipType;

    private int speedLevel; //developed by science cards, global
    private int speed;
    private int capacityLevel; //developed by industry

    private int shipCapacity;

    @Enumerated
    private ShipStatus shipStatus;

    @JsonIgnore
    @OneToMany(mappedBy = "ship")
    private List<TravelRoute> travelRoute;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "user_user_id")
    private User user;

    public Ship(ShipType shipType, int capacityLevel, User user) {
        this.shipType = shipType;
        this.speedLevel = 1; //TODO cards
        this.capacityLevel = capacityLevel;
        this.user = user;

        getCapacity();

        this.speed = speedLevel * shipType.getSpeed();
    }

    public void getCapacity() {
        shipCapacity = capacityLevel * shipType.getCapacity();
    }

    public Planet getCurrentPlanet(){
        return travelRoute.get(travelRoute.size() - 1).getArrivalPlanet();
    }

}
