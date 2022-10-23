package HubertRoszyk.company.entiti_class.ship;

import HubertRoszyk.company.entiti_class.TravelRoute;
import HubertRoszyk.company.entiti_class.User;
import HubertRoszyk.company.enumStatus.ShipStatus;
import HubertRoszyk.company.enumTypes.ShipType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
abstract public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Setter(AccessLevel.NONE)
    private int id;

    @Enumerated
    private ShipType shipType;

    private int speedLevel; //developed by science cards, global
    private int speed;
    private int capacityLevel; //developed by industry

    private int shipCapacity;

    @Enumerated
    private ShipStatus shipStatus;

    /*@ManyToOne
    @JoinColumn(name = "planet_id")
    private Planet currentPlanet;*/

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
        //shipStatus;

        this.speed = speedLevel * shipType.getSpeed();
    }

    public void getCapacity() {
        shipCapacity = capacityLevel * shipType.getCapacity();
    }
}
