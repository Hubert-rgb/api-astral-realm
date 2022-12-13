package HubertRoszyk.company.entiti_class;

import HubertRoszyk.company.enumTypes.BuildingType;
import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.web.servlet.View;

import javax.persistence.*;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table
@Entity
public class Building { // dane budynku, są zależmne od typu i poziomu
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated
    @NonNull
    private BuildingType buildingType;
    private int buildingLevel = 0;
    //private int buildingPrice;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(cascade = CascadeType.ALL)
    @NonNull
    @JoinColumn(name = "planetId", referencedColumnName = "planetId")
    private Planet planet;

}
