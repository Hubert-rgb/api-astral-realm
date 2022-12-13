package HubertRoszyk.company.entiti_class;

import HubertRoszyk.company.enumStatus.PlanetStatus;
import HubertRoszyk.company.enumStatus.ShipStatus;
import HubertRoszyk.company.enumTypes.PlanetType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class Planet {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "planetId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated
    private PlanetType planetType;
    @Enumerated
    private PlanetStatus planetStatus;

    private int industryPointsMultiplier;
    private int sciencePointsMultiplier;

    private int size;

    private double industryPointsProduce;
    private double sciencePointsProduce;

    private int planetLocationX;
    private int planetLocationY;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "galaxyId", referencedColumnName = "galaxyId")
    @Setter(AccessLevel.NONE)
    private Galaxy galaxy;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @Transient
    @JsonIgnore
    private PlanetLocation planetLocation;

    @JsonIgnore
    @OneToMany(mappedBy = "planet")
    private Set<Building> buildingList = new HashSet<>();

    @JsonIgnore
    @OneToOne(mappedBy = "planet")
    private PlanetPoints planetPoints;

    /*@JsonIgnore
    @OneToMany(mappedBy = "planet")
    private Set<Ship> ships;*/

    @JsonIgnore
    @OneToMany(mappedBy = "arrivalPlanet")
    private Set<TravelRoute> arrivalTravelRoutes;

    @JsonIgnore
    @OneToMany(mappedBy = "departurePlanet")
    private Set<TravelRoute> departureTravelRoutes;

    public Planet(PlanetType planetType, int industryPointsMultiplier, int sciencePointsMultiplier, int size, int xLocation, int yLocation, Galaxy galaxy) {
        this.industryPointsMultiplier = industryPointsMultiplier;
        this.sciencePointsMultiplier = sciencePointsMultiplier;
        this.planetLocationX = xLocation;
        this.planetLocationY = yLocation;
        this.size = size;
        this.planetType = planetType;
        this.galaxy = galaxy;

        this.industryPointsProduce = planetType.getDefaultIndustryProduce();
        this.sciencePointsProduce = planetType.getDefaultScienceProduce();

        PlanetLocation location = new PlanetLocation(xLocation, yLocation);
        planetLocation = location;

        planetStatus = PlanetStatus.UNCLAIMED;
    }

    public void asignUser(User user) {
        this.user = user;
    }
}
