package HubertRoszyk.company.entiti_class;

import HubertRoszyk.company.enumTypes.TimerActionType;
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

    @OneToMany
    @ManyToOne
    @JoinColumn(name = "timer_entity_id")
    @NonNull
    private TimerEntity timerEntity;
}
