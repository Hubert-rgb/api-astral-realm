package HubertRoszyk.company.entiti_class.ship;

import HubertRoszyk.company.entiti_class.User;
import HubertRoszyk.company.enumTypes.BuildingType;
import HubertRoszyk.company.enumTypes.ShipType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "Ship")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AttackShip extends Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Setter(AccessLevel.NONE)
    private int id;

    @ElementCollection
    @MapKeyColumn(name = "level_number")
    Map<Integer, Integer> shipLoad = new HashMap<>();
    public AttackShip(ShipType shipType, int capacityLevel, User user){
        super(shipType, capacityLevel, user);
        for (int i = 1; i <= BuildingType.ATTACK.getLevelNums(); i++){
            shipLoad.put(i, 0);
        }
    }
}
