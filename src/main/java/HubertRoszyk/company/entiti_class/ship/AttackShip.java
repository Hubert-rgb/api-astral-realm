package HubertRoszyk.company.entiti_class.ship;

import HubertRoszyk.company.controller.ArmyController;
import HubertRoszyk.company.entiti_class.Attack;
import HubertRoszyk.company.entiti_class.User;
import HubertRoszyk.company.enumTypes.BuildingType;
import HubertRoszyk.company.enumTypes.ShipType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToMany(mappedBy = "attackShips")
    private Set<Attack> attackSet = new HashSet<>();

    @ElementCollection
    @MapKeyColumn(name = "level_number")
    private Map<Integer, Integer> shipLoad = new HashMap<>();
    int industryPointsShipLoad = 0;
    public AttackShip(ShipType shipType, int capacityLevel, User user){
        super(shipType, capacityLevel, user);
        shipLoad = ArmyController.getEmptyArmy();
    }
    public int getShipLoadSize(){
        int shipLoadSize = industryPointsShipLoad;
        for (int i = 1; i <= shipLoad.size(); i++){
            int divisionNumber = shipLoad.get(i);
            shipLoadSize += divisionNumber;
        }
        return shipLoadSize;
    }
}
