package HubertRoszyk.company.entiti_class.ship;

import HubertRoszyk.company.controller.ArmyController;
import HubertRoszyk.company.entiti_class.Attack;
import HubertRoszyk.company.entiti_class.User;
import HubertRoszyk.company.enumTypes.BuildingType;
import HubertRoszyk.company.enumTypes.ShipType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    @ManyToMany(mappedBy = "attackShips")
    private Set<Attack> attackSet = new HashSet<>();

    @ElementCollection
    @MapKeyColumn(name = "level_number")
    private Map<Integer, Integer> shipLoad = new HashMap<>();
    public AttackShip(ShipType shipType, int capacityLevel, User user){
        super(shipType, capacityLevel, user);
        shipLoad = ArmyController.getEmptyArmy();
    }
}
