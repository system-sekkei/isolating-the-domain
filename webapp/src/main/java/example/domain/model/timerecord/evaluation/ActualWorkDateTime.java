package example.domain.model.timerecord.evaluation;

import example.domain.model.timerecord.bindingtime.BindingTime;
import example.domain.model.timerecord.bindingtime.DaytimeBindingTime;
import example.domain.model.timerecord.bindingtime.NightBindingTime;
import example.domain.model.timerecord.breaktime.BreakTime;
import example.domain.model.timerecord.breaktime.DaytimeBreakTime;
import example.domain.model.timerecord.breaktime.NightBreakTime;
import example.domain.model.timerecord.timefact.WorkDate;
import example.domain.model.timerecord.timefact.WorkRange;
import example.domain.type.time.QuarterHour;

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

    public WorkDate workDate() {
        return workRange.workDate();
    }

    public WorkTime workTime() {
        return new WorkTime(daytimeWorkTime(), nightWorkTime());
    }

    public BreakTime breakTime() {
        return new BreakTime(daytimeBreakTime, nightBreakTime);
    }

    public DaytimeWorkTime daytimeWorkTime() {
        DaytimeBindingTime daytimeBindingTime = daytimeBindingTime();
        QuarterHour quarterHour = daytimeBreakTime.subtractFrom(daytimeBindingTime);
        return new DaytimeWorkTime(quarterHour);
    }

    public DaytimeBindingTime daytimeBindingTime() {
        BindingTime bindingTime = new BindingTime(workRange);
        NightBindingTime nightBindingTime = nightBindingTime();
        return new DaytimeBindingTime(bindingTime, nightBindingTime);
    }

    public NightBindingTime nightBindingTime() {
        return new NightBindingTime(workRange);
    }

    public NightWorkTime nightWorkTime() {
        QuarterHour quarterHour = nightBreakTime.subtractFrom(nightBindingTime());
        return new NightWorkTime(quarterHour);
    }

    public OverWorkTime overWorkTime() {
        return new OverWorkTime(workTime());
    }
}
