package example.domain.model.timerecord;

import example.domain.model.timerecord.breaktime.BreakTime;
import example.domain.model.timerecord.breaktime.DaytimeBreakTime;
import example.domain.model.timerecord.breaktime.NightBreakTime;

/**
 * 勤務時間実績
 */
public class ActualWorkTime {

    TimeRange timeRange;
    DaytimeBreakTime daytimeBreakTime;
    NightBreakTime nightBreakTime;

    @Deprecated
    public ActualWorkTime() {
    }

    public ActualWorkTime(TimeRange timeRange, DaytimeBreakTime daytimeBreakTime, NightBreakTime nightBreakTime) {
        this.timeRange = timeRange;
        this.daytimeBreakTime = daytimeBreakTime;
        this.nightBreakTime = nightBreakTime;
    }

    public TimeRange timeRange() {
        return timeRange;
    }

    public DaytimeBreakTime daytimeBreakTime() {
        return daytimeBreakTime;
    }

    public NightBreakTime nightBreakTime() {
        return nightBreakTime;
    }

    public WorkTime workTime() {
        return new WorkTime(daytimeWorkTime(), nightWorkTime());
    }

    public BreakTime breakTime() {
        return new BreakTime(daytimeBreakTime, nightBreakTime);
    }

    public DaytimeWorkTime daytimeWorkTime() {
        return new DaytimeWorkTime(timeRange, daytimeBreakTime);
    }

    public NightWorkTime nightWorkTime() {
        return new NightWorkTime(timeRange, nightBreakTime);
    }

    public OverWorkTime overWorkTime() {
        return new OverWorkTime(workTime());
    }
}
