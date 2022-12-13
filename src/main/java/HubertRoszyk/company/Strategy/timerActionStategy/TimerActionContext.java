package HubertRoszyk.company.Strategy.timerActionStategy;

import HubertRoszyk.company.Strategy.update_points_produce_strategy.UpdatePointsProduceStrategy;
import HubertRoszyk.company.entiti_class.TimerAction;
import HubertRoszyk.company.enumTypes.BuildingType;
import HubertRoszyk.company.enumTypes.TimerActionType;

public class TimerActionContext {
    private TimerActionStrategy strategy;

    public void setStrategy(TimerActionStrategy strategy) {
        this.strategy = strategy;
    }

    public void executeStrategy(TimerAction timerAction) {
        strategy.executeAction(timerAction);
    }
}
