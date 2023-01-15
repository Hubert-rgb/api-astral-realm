package HubertRoszyk.company.entiti_class;

import HubertRoszyk.company.enumTypes.cardsType.ScienceCardType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    private int userLookId;

    @ElementCollection(targetClass = ScienceCardType.class)
    @JoinTable(name = "scienceCards", joinColumns = @JoinColumn(name = "galaxyPointsId"))
    @Column()
    @Enumerated()
    private Set<ScienceCardType> scienceCards;

    private int economicCardsNumber;
    private int militaryCardsNumber;
    private int politicalCardsNumber;

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

    public GalaxyPoints(User user, Galaxy galaxy, int userLookId) {
        this.galaxy = galaxy;
        this.user = user;

        this.userLookId = userLookId;

        sciencePoints = 0;

        economicCardsNumber = 0;
        militaryCardsNumber = 0;
        politicalCardsNumber = 0;
    }
}
