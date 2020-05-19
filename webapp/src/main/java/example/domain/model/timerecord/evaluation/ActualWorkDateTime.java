package example.domain.model.timerecord.evaluation;

import example.domain.model.timerecord.timefact.WorkRange;
import example.domain.type.time.Minute;
import example.domain.validation.BusinessLogic;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;

/**
 * 勤務日時実績
 */
public class ActualWorkDateTime {

    @Valid
    WorkRange workRange;
    DaytimeBreakTime daytimeBreakTime;
    NightBreakTime nightBreakTime;

    boolean daytimeBreakTimeValid;
    boolean nightBreakTimeValid;

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
        return new WorkDate(workRange.start().date());
    }

    public WorkTime workTime() {
        return new WorkTime(daytimeWorkTime(), nightWorkTime());
    }

    public BreakTime breakTime() {
        return new BreakTime(daytimeBreakTime, nightBreakTime);
    }

    public DaytimeWorkTime daytimeWorkTime() {
        DaytimeBindingTime daytimeBindingTime = daytimeBindingTime();
        return daytimeBindingTime.subtract(daytimeBreakTime);
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
        return nightBindingTime().subtract(nightBreakTime);
    }


    @AssertTrue(message = "休憩時間が不正です", groups = BusinessLogic.class)
    public boolean isDaytimeBreakTimeValid() {
        Minute daytimeBindingMinute = daytimeBindingTime().quarterHour().minute();
        if (daytimeBindingMinute.lessThan(daytimeBreakTime.minute())) {
            return false;
        }

        return true;
    }

    @AssertTrue(message = "休憩時間（深夜）が不正です", groups = BusinessLogic.class)
    public boolean isNightBreakTimeValid() {
        Minute nightBindingMinute = nightBindingTime().quarterHour().minute();
        if (nightBindingMinute.lessThan(nightBreakTime.minute())) {
            return false;
        }

        return true;
    }
}
