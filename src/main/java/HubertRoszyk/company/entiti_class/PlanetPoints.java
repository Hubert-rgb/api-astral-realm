package HubertRoszyk.company.entiti_class;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    private double defensePoints;
    private double attackPoints;
    private double industryPoints;

    private double defensePointsIncome;
    private double attackPointsIncome;
    private double industryPointsIncome;

    private int totalStorageSize;
    private int totalHarbourSize;
    private int totalHarbourLoad;
    private int shipYardLevel;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "planet_id")
    private Planet planet;

    public PlanetPoints(Planet planet) {
        this.planet = planet;

        defensePoints = planet.getPlanetType().getDefaultDefencePoints();
        attackPoints = 0;
        industryPoints = 0;

        defensePointsIncome = 0;
        attackPointsIncome = 0;
        industryPointsIncome = 0;

        totalStorageSize = planet.getPlanetType().getDefaultStorageSize();
        totalHarbourSize = planet.getPlanetType().getDefaultHarbourSize();
        shipYardLevel = 1;
    }

}
