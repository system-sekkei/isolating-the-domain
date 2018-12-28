package example.domain.model.attendance.worktimerecord;

import example.domain.type.time.QuarterHour;

/**
 * 日中労働時間
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
