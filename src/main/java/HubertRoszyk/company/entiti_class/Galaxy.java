package HubertRoszyk.company.entiti_class;

import HubertRoszyk.company.enumTypes.PeriodType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Galaxy {
    //TODO 30 day countdown
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "galaxyId")
    private int id;
    private int userNumber = 0;
    @NonNull
    private int maximalUserNumber;
    @NonNull
    private String galaxyName;

    @Enumerated
    private PeriodType periodType;

    /*@JsonIgnore
    //@ManyToMany(mappedBy = "galaxies")
    @ManyToMany
    @JoinTable(
            name = "userGalaxies",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "galaxyId")
    )
    private Set<User> users = new HashSet<>();*/

    @JsonIgnore
    @OneToMany(mappedBy = "galaxy")
    private Set<Planet> enrolledPlanets = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "galaxy")
    private Set<GalaxyPoints> enrolledPoits = new HashSet<>();

    @JsonIgnore
    @OneToOne(mappedBy = "galaxy")
    private TimerEntity timerEntity;

    public Galaxy(int maximalUserNumber, String galaxyName, PeriodType periodType) {
        this.maximalUserNumber = maximalUserNumber;
        this.galaxyName = galaxyName;
        this.periodType = periodType;
    }

    public void addUser() {
        //users.add(user);
        userNumber += 1;
    }
}
