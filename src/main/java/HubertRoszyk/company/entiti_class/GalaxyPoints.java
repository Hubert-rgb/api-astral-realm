package HubertRoszyk.company.entiti_class;

import HubertRoszyk.company.enumTypes.cardsType.EconomyCardType;
import HubertRoszyk.company.enumTypes.cardsType.MilitaryCardType;
import HubertRoszyk.company.enumTypes.cardsType.PoliticalCardType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table
public class GalaxyPoints {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "galaxyPointsId")
    private int id;

    private double sciencePoints;

    private double sciencePointsIncome;

    @ElementCollection(targetClass = MilitaryCardType.class)
    @JoinTable(name = "militaryCards", joinColumns = @JoinColumn(name = "galaxyPointsId"))
    @Column()
    @Enumerated()
    private Set<MilitaryCardType> militaryCards;

    @ElementCollection(targetClass = PoliticalCardType.class)
    @JoinTable(name = "politicalCards", joinColumns = @JoinColumn(name = "galaxyPointsId"))
    @Column()
    @Enumerated()
    private Set<PoliticalCardType> politicalCards;

    @ElementCollection(targetClass = EconomyCardType.class)
    @JoinTable(name = "economyCards", joinColumns = @JoinColumn(name = "galaxyPointsId"))
    @Column()
    @Enumerated()
    private Set<EconomyCardType> economyCards;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "galaxyId", referencedColumnName = "galaxyId")
    private Galaxy galaxy;

    public GalaxyPoints(User user, Galaxy galaxy) {
        this.galaxy = galaxy;
        this.user = user;

        sciencePoints = 0;
    }

    /*public void assignUser(User user) {
        this.user = user;
    }
    public void assignGalaxy(Galaxy galaxy) {
        this.galaxy = galaxy;
    }*/
}
