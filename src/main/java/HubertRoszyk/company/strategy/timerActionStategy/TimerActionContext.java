package HubertRoszyk.company.strategy.timerActionStategy;

import HubertRoszyk.company.entiti_class.TimerAction;

public class TimerActionContext {
    private TimerActionStrategy strategy;

    public void setStrategy(TimerActionStrategy strategy) {
        this.strategy = strategy;
    }

    public void executeStrategy(TimerAction timerAction) {
        strategy.executeAction(timerAction);
    }
}
