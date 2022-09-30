package HubertRoszyk.company.entiti_class;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
public class ScienceCard {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Enumerated
    private CascadeType cardType;

    @ManyToOne
    @JoinColumn(name = "galaxy_points_id")
    private GalaxyPoints galaxyPoints;
}
