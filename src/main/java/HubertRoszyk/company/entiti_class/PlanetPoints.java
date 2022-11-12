package HubertRoszyk.company.entiti_class;

import HubertRoszyk.company.enumTypes.BuildingType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.mapping.Collection;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class PlanetPoints {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "planetPointsId")
    private int id;

    private int defensePoints;
    private double industryPoints;

    private double industryPointsIncome;

    private int totalStorageSize;
    private int totalHarbourSize;
    private int totalHarbourLoad;

    private int shipYardLevel;
    private int totalAttackBuildingSize;
    private int totalAttackBuildingLoad;

    @ElementCollection
    @MapKeyColumn(name = "level_number")
    private Map<Integer, Integer> army = new HashMap<>(); //level number - number of army divisions on this level

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "planet_id")
    private Planet planet;

    public PlanetPoints(Planet planet) {
        this.planet = planet;

        defensePoints = planet.getPlanetType().getDefaultDefencePoints();
        industryPoints = 0;

        industryPointsIncome = planet.getIndustryPointsMultiplier() * planet.getIndustryPointsProduce();

        totalStorageSize = planet.getPlanetType().getDefaultStorageSize();
        totalHarbourSize = planet.getPlanetType().getDefaultHarbourSize();

        shipYardLevel = 1;
        totalAttackBuildingSize = planet.getPlanetType().getDefaultArmyBuildingSize();

        this.army = getEmptyArmy();
    }
    public Map<Integer, Integer> getEmptyArmy(){
        Map<Integer, Integer> army = new HashMap<>();
        for (int i = 1; i <= BuildingType.ATTACK.getLevelNums(); i++){
            army.put(i, 0);
        }
        return army;
    }

}
