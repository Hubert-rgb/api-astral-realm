package HubertRoszyk.company.entiti_class;

import HubertRoszyk.company.enumStatus.ShipStatus;
import HubertRoszyk.company.enumTypes.ShipType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Ship")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Setter(AccessLevel.NONE)
    private int id;

    @Enumerated
    private ShipType shipType;

    private int speedLevel; //developed by science cards
    private int speed;
    private int capacityLevel; //developed by industry

    private int shipCapacity;
    private double shipLoad;

    @Enumerated
    private ShipStatus shipStatus;

    /*@ManyToOne
    @JoinColumn(name = "planet_id")
    private Planet currentPlanet;*/

    @JsonIgnore
    @OneToMany(mappedBy = "ship")
    private List<TravelRoute> travelRoute;


    @ManyToOne
    @JoinColumn(name = "user_user_id")
    private User user;

    public Ship(ShipType shipType, int speedLevel, int capacityLevel, User user) {
        this.shipType = shipType;
        this.speedLevel = speedLevel;
        this.capacityLevel = capacityLevel;
        this.user = user;

        getCapacity();

        shipLoad = 0;
        shipStatus = ShipStatus.DOCKED;

        this.speed = speedLevel * shipType.getSpeed();
    }

    public void getCapacity() {
        shipCapacity = capacityLevel * shipType.getCapacity();
    }
}