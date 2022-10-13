package HubertRoszyk.company.Strategy.timerActionStategy;

import HubertRoszyk.company.entiti_class.TimerAction;
import HubertRoszyk.company.enumTypes.TimerActionType;

public interface TimerActionStrategy {
    public void executeAction(TimerAction timerAction);
}
