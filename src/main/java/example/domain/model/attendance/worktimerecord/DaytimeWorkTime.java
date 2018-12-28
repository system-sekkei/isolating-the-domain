package example.domain.model.attendance.worktimerecord;

import example.domain.type.time.HourAndMinute;
import example.domain.type.time.Minute;

/**
 * 日中労働時間
 */
public class DaytimeWorkTime {

    Minute value;

    public DaytimeWorkTime(WorkTimeRange workTimeRange, DaytimeBreakTime daytimeBreakTime) {
        value = daytimeBreakTime.subtractFrom(workTimeRange.daytimeBindingTime().minute());
    }

    public Minute minute() {
        return value;
    }

    @Override
    public String toString() {
        return HourAndMinute.from(value).toString();
    }
}
