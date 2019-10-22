package example.domain.model.timerecord.evaluation;

import example.domain.model.timerecord.breaktime.DaytimeBreakTime;
import example.domain.model.timerecord.timefact.WorkRange;
import example.domain.type.time.QuarterHour;

/**
 * 日中勤務時間
 */
public class DaytimeWorkTime {

    QuarterHour value;

    public DaytimeWorkTime(WorkRange workRange, DaytimeBreakTime daytimeBreakTime) {
        value = daytimeBreakTime.subtractFrom(workRange.daytimeBindingTime());
    }

    public QuarterHour quarterHour() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
