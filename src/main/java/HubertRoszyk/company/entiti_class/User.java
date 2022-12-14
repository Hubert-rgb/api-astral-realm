package HubertRoszyk.company.entiti_class;

import HubertRoszyk.company.entiti_class.ship.Ship;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users") //user nie działą w h2
public class User {

    @Id
    @Column(name = "userId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    @Column(name = "userName")
    private String displayName;
    @NonNull
    @Column(name = "userPassword")
    private String firebaseUId;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Planet> enrolledPlanets = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<GalaxyPoints> points = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Attack> attacks = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Ship> ships = new HashSet<>();
}
