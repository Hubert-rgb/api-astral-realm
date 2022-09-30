package HubertRoszyk.company.entiti_class;

import HubertRoszyk.company.enumStatus.ShipStatus;
import HubertRoszyk.company.enumTypes.ShipType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;

    @Enumerated
    private ShipType shipType;
    private int speed; //developed by science cards
    private int capacity; //developed by industry

    private int load;

    @Enumerated
    private ShipStatus shipStatus;

    /*@ManyToOne
    @JoinColumn(name = "planet_id")
    private Planet currentPlanet;*/

    @JsonIgnore
    @OneToMany(mappedBy = "ship")
    private Set<TravelRoute> travelRoute;
}
