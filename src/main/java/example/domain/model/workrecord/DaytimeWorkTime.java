package example.domain.model.workrecord;

import example.domain.type.time.QuarterHour;

/**
 * 日中勤務時間
 */
public class DaytimeWorkTime {

    QuarterHour value;

    public DaytimeWorkTime(WorkTimeRange workTimeRange, DaytimeBreakTime daytimeBreakTime) {
        value = daytimeBreakTime.subtractFrom(workTimeRange.daytimeBindingTime());
    }

    public QuarterHour quarterHour() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
