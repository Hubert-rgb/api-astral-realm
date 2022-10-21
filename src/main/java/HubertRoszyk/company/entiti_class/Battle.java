package HubertRoszyk.company.entiti_class;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
public class Battle {
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

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "attack_planet_id")
    private Planet attackPlanet;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "defence_planet_id")
    private Planet defencePlanet;

    private long battleTime;
    private String status;

    public Battle(Planet attackPlanet, Planet defencePlanet, User user, long battleTime) {
        startingTime = LocalDateTime.now();

        this.user = user;
        this.attackPlanet = attackPlanet;
        this.defencePlanet = defencePlanet;

        this.battleTime = battleTime;

        status = "army sent";
    }
}
