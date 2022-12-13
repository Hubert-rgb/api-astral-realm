package HubertRoszyk.company.entiti_class;

import HubertRoszyk.company.enumTypes.TimerActionType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class TimerAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated
    @NonNull
    private TimerActionType timerActionType;

    @NonNull
    private int endingCycle;
    @NonNull
    private int executionId;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany
    @ManyToOne
    @JoinColumn(name = "timer_entity_id")
    @NonNull
    private TimerEntity timerEntity;
}
