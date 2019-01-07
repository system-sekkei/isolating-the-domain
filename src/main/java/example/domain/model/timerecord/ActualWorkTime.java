package example.domain.model.timerecord;

import example.domain.model.timerecord.breaktime.BreakTime;
import example.domain.model.timerecord.breaktime.DaytimeBreakTime;
import example.domain.model.timerecord.breaktime.MidnightBreakTime;

/**
 * 勤務時間実績
 */
public class ActualWorkTime {

    TimeRange timeRange;
    DaytimeBreakTime daytimeBreakTime;
    MidnightBreakTime midnightBreakTime;

    @Deprecated
    public ActualWorkTime() {
    }

    public ActualWorkTime(TimeRange timeRange, DaytimeBreakTime daytimeBreakTime, MidnightBreakTime midnightBreakTime) {
        this.timeRange = timeRange;
        this.daytimeBreakTime = daytimeBreakTime;
        this.midnightBreakTime = midnightBreakTime;
    }

    public TimeRange timeRange() {
        return timeRange;
    }

    public DaytimeBreakTime daytimeBreakTime() {
        return daytimeBreakTime;
    }

    public MidnightBreakTime midnightBreakTime() {
        return midnightBreakTime;
    }

    public WorkTime workTime() {
        return new WorkTime(daytimeWorkTime(), midnightWorkTime());
    }

    public BreakTime breakTime() {
        return new BreakTime(daytimeBreakTime, midnightBreakTime);
    }

    public DaytimeWorkTime daytimeWorkTime() {
        return new DaytimeWorkTime(timeRange, daytimeBreakTime);
    }

    public MidnightWorkTime midnightWorkTime() {
        return new MidnightWorkTime(timeRange, midnightBreakTime);
    }

    public OverWorkTime overWorkTime() {
        return new OverWorkTime(workTime());
    }
}
