package HubertRoszyk.company.entiti_class;

import lombok.*;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TimerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timerId")
    private int id;

    @NonNull
    private int cyclesNum;
    @NonNull
    private LocalDateTime timerStartTime;

    @NonNull
    @OneToOne
    @JoinColumn(name = "galaxy_id")
    private Galaxy galaxy; //or galaxyPoints

    @OneToMany(mappedBy = "timerEntity", fetch = FetchType.EAGER)
    private List<TimerAction> timerActionList;

    public void addTimerAction(TimerAction timerAction) {
        timerActionList.add(timerAction);
    }
}
