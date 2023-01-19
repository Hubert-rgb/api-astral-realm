package HubertRoszyk.company.entiti_class;

import HubertRoszyk.company.enumTypes.cardsType.ScienceCardType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

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

    /** SCIENCE CARDS EXECUTION VARIABLES */
    /** Economy cards */

    @Getter
    @Accessors(fluent = true)
    @Setter
    private boolean canBuildIndustryShip;
    private int constructionTimeSubtrahend; //TO ASK TODO subtract or divide
    private int industryShipSpeedAddition;
    private double globalIndustryPointsMultiplier;
    private double globalSciencePointsMultiplier;

    /** Military cards */

    @Getter
    @Accessors(fluent = true)
    @Setter
    private boolean canColonise;
    private int armySpeedAddition;
    private int armyCostSubtrahend;

    @Getter
    @Accessors(fluent = true)
    @Setter
    private boolean canPillageUsersPlanets;

    @Getter
    @Accessors(fluent = true)
    @Setter
    private boolean canBattleUsersPlanets;

    /** Politician cards */

    @Getter
    @Accessors(fluent = true)
    @Setter
    private boolean canColoniseInhabitedPlanets;
    private int protectionPeriodAddition;

    private boolean peaceAvailable;
    private boolean allianceAvailable;



    public GalaxyPoints(User user, Galaxy galaxy, int userLookId) {
        this.galaxy = galaxy;
        this.user = user;

        this.userLookId = userLookId;

        sciencePoints = 0;

        economicCardsNumber = 0;
        militaryCardsNumber = 0;
        politicalCardsNumber = 0;

        /** Economy cards */

        canBuildIndustryShip = false;
        constructionTimeSubtrahend = 0;
        industryShipSpeedAddition = 0;
        globalIndustryPointsMultiplier = 1;
        globalSciencePointsMultiplier = 1;

        /** Military cards */

        canColonise = false;
        armySpeedAddition = 0;
        armyCostSubtrahend = 0;
        canPillageUsersPlanets = false;
        canBattleUsersPlanets = false;

        /** Politician cards */
    }
}
