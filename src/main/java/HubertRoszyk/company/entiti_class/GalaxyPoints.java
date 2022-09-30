package HubertRoszyk.company.entiti_class;

import com.fasterxml.jackson.annotation.JsonIgnore;
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


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

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
