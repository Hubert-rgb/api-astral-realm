package HubertRoszyk.company.enumTypes;

import lombok.Getter;

@Getter
public enum PeriodType {
    FAST(1),
    SLOW(24),
    MODERATE(5);

    private int gamePeriodHours;

    PeriodType(int gamePeriodHours){
        this.gamePeriodHours = gamePeriodHours;
    }
}
