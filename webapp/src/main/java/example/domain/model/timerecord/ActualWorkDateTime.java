package example.domain.model.timerecord;

import example.domain.model.timerecord.breaktime.BreakTime;
import example.domain.model.timerecord.breaktime.DaytimeBreakTime;
import example.domain.model.timerecord.breaktime.NightBreakTime;

/**
 * 勤務日時実績
 */
public class ActualWorkDateTime {

    // TODO: WorkDateTimeRangeとかDateTimeRangeとかの方が妥当？
    WorkRange workRange;
    DaytimeBreakTime daytimeBreakTime;
    NightBreakTime nightBreakTime;

    @Deprecated
    public ActualWorkDateTime() {
    }

    public ActualWorkDateTime(WorkRange workRange, DaytimeBreakTime daytimeBreakTime, NightBreakTime nightBreakTime) {
        this.workRange = workRange;
        this.daytimeBreakTime = daytimeBreakTime;
        this.nightBreakTime = nightBreakTime;
    }

    public WorkRange workRange() {
        return workRange;
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
        return new DaytimeWorkTime(workRange, daytimeBreakTime);
    }

    public NightWorkTime nightWorkTime() {
        return new NightWorkTime(workRange, nightBreakTime);
    }

    public OverWorkTime overWorkTime() {
        return new OverWorkTime(workTime());
    }
}
