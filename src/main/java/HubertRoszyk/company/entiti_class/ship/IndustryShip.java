package HubertRoszyk.company.entiti_class.ship;

import HubertRoszyk.company.entiti_class.User;
import HubertRoszyk.company.enumTypes.ShipType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Ship")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IndustryShip extends Ship{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Setter(AccessLevel.NONE)
    private int id;

    private double shipLoad;
    public IndustryShip(ShipType shipType, int capacityLevel, User user){
        super(shipType, capacityLevel, user);
    }
}
