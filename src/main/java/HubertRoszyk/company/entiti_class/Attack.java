package HubertRoszyk.company.entiti_class;

import HubertRoszyk.company.entiti_class.ship.AttackShip;
import HubertRoszyk.company.enumTypes.AttackType;
import HubertRoszyk.company.enumTypes.BuildingType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
public class Attack {
    @Id
    @Column(name = "battleId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime startingTime;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /*@ElementCollection(targetClass = AttackShip.class)
    @JoinTable(name = "attackShips", joinColumns = @JoinColumn(name = "attackId"))
    @Column()
    *//*@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)*//*
    @ManyToOne
    @JoinColumn(name = "attack_Ship_id")*/
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            joinColumns = @JoinColumn(name = "attack_id"),
            inverseJoinColumns = @JoinColumn(name = "attack_ship_id")
    )
    private Set<AttackShip> attackShips;

    @ElementCollection
    @MapKeyColumn(name = "level_number")
    private Map<Integer, Integer> army = new HashMap<>();

    private int attackArmyValue;

    private int attackArmySize;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "defence_planet_id")
    private Planet defencePlanet;

    private AttackType attackType;
    private long battleTime;
    private String status;

    public Attack(Set<AttackShip> attackShips, Planet defencePlanet, User user, AttackType attackType) {
        startingTime = LocalDateTime.now();

        this.user = user;
        this.attackShips = attackShips;
        this.defencePlanet = defencePlanet;
        this.attackType = attackType;

        this.battleTime = 1;

        status = "army sent";
        this.army = getEmptyArmy();
        int attackValue = 0;
        for (AttackShip ship : attackShips){
            attackValue += getArmyValue(ship.getShipLoad());
            this.army = combineLoad(army, ship.getShipLoad());
        }
        this.attackArmyValue = attackValue;

        int attackSize = 0;
        for (AttackShip ship : attackShips){
            attackSize += getArmySize(ship.getShipLoad());
        }
        this.attackArmySize = attackSize;
    }
    public int getArmyValue(Map<Integer, Integer> army){
        System.out.println(army);
        int value = 0;
        for (int armyLevel = 1; armyLevel < army.size(); armyLevel++){
            int armyDivisionNumber = army.get(armyLevel);
            int armyLevelValue = armyLevel * armyDivisionNumber;
            value += armyLevelValue;
        }
        return value;
    }
    public int getArmySize(Map<Integer, Integer> army){
        System.out.println(army);
        int size = 0;
        for (int armyLevel = 1; armyLevel < army.size(); armyLevel++){
            size += army.get(armyLevel);
        }
        return size;
    }
    private Map<Integer, Integer> combineLoad(Map<Integer, Integer> shipLoad, Map<Integer, Integer> addedLoad){
        Map<Integer, Integer> combinedLoad = new HashMap<>();

        for (int i = 1; i <= shipLoad.size(); i++){
            combinedLoad.put(i, shipLoad.get(i) + addedLoad.get(i));
        }

        return combinedLoad;
    }
    public Map<Integer, Integer> getEmptyArmy(){
        for (int i = 1; i <= BuildingType.ATTACK.getLevelNums(); i++){
            army.put(i, 0);
        }
        return army;
    }
}
