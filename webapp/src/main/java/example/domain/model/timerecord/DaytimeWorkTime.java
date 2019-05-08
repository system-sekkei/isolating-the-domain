package example.domain.model.timerecord;

import example.domain.model.timerecord.breaktime.DaytimeBreakTime;
import example.domain.type.time.QuarterHour;

/**
 * 日中勤務時間
 */
public class DaytimeWorkTime {

    QuarterHour value;

    public DaytimeWorkTime(TimeRange timeRange, DaytimeBreakTime daytimeBreakTime) {
        value = daytimeBreakTime.subtractFrom(timeRange.daytimeBindingTime());
    }

    public QuarterHour quarterHour() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
