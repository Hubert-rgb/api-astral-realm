package HubertRoszyk.company.entiti_class;

import HubertRoszyk.company.controller.ArmyController;
import HubertRoszyk.company.entiti_class.ship.AttackShip;
import HubertRoszyk.company.entiti_class.ship.IndustryShip;
import HubertRoszyk.company.enumTypes.AttackType;
import HubertRoszyk.company.enumTypes.BuildingType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.EntityGraph;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@NamedEntityGraph(
        name = "attack",
        attributeNodes = {
                @NamedAttributeNode(value = "army"),
                @NamedAttributeNode(value = "defencePlanet"),
                @NamedAttributeNode(value = "attackShips", subgraph = "attackShip"),
                @NamedAttributeNode(value = "industryShips", subgraph = "industryShip"),
                @NamedAttributeNode(value = "attackType")

        }, subgraphs = {
        @NamedSubgraph(
                name = "attackShip",
                attributeNodes = {
                        @NamedAttributeNode(value = "travelRoute", subgraph = "travelRoute"),
                        @NamedAttributeNode(value = "shipLoad")
                }

        ),
        @NamedSubgraph(
                name = "industryShip",
                attributeNodes = {
                        @NamedAttributeNode(value = "travelRoute", subgraph = "travelRoute")
                }

        ),
        @NamedSubgraph(
                name = "travelRoute",
                attributeNodes = {
                        @NamedAttributeNode(value = "arrivalPlanet", subgraph = "planet"),
                        @NamedAttributeNode(value = "departurePlanet", subgraph = "planet")
                }
        ),
        @NamedSubgraph(
                name = "planet",
                attributeNodes = {
                        @NamedAttributeNode("galaxy")
                }
        )
})
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            joinColumns = @JoinColumn(name = "attack_id"),
            inverseJoinColumns = @JoinColumn(name = "attack_ship_id")
    )
    private Set<AttackShip> attackShips;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            joinColumns = @JoinColumn(name = "attack_id"),
            inverseJoinColumns = @JoinColumn(name = "industry_ship_id")
    )
    private Set<IndustryShip> industryShips;

    @ElementCollection
    @MapKeyColumn(name = "level_number")
    private Map<Integer, Integer> army = new HashMap<>();

    private int attackArmyValue;
    private int attackPlanetId;

    private int attackArmySize;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "defence_planet_id")
    private Planet defencePlanet;

    private AttackType attackType;
    private long battleTime;
    private String status;

    public Attack(Set<AttackShip> attackShips, Set<IndustryShip> industryShip, int attackPlanetId, Planet defencePlanet, User user, AttackType attackType) {
        startingTime = LocalDateTime.now();

        this.user = user;
        this.attackShips = attackShips;
        this.industryShips = industryShip;
        this.defencePlanet = defencePlanet;
        this.attackType = attackType;
        this.attackPlanetId = attackPlanetId;

        this.battleTime = 1;

        status = "army sent";
        this.army = ArmyController.getEmptyArmy();
        int attackValue = 0;
        for (AttackShip ship : attackShips){
            attackValue += ArmyController.getArmyValue(ship.getShipLoad());
            this.army = ArmyController.combineArmy(army, ship.getShipLoad());
        }
        this.attackArmyValue = attackValue;

        int attackSize = 0;
        for (AttackShip ship : attackShips){
            attackSize += ArmyController.getArmySize(ship.getShipLoad());
        }
        this.attackArmySize = attackSize;
    }

}
