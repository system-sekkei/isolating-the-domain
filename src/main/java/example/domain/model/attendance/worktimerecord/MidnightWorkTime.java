package example.domain.model.attendance.worktimerecord;

import example.domain.type.time.HourAndMinute;
import example.domain.type.time.Minute;

/**
 * 深夜労働時間
 */
public class MidnightWorkTime {

    Minute value;

    public MidnightWorkTime(WorkTimeRange workTimeRange, MidnightBreakTime midnightBreakTime) {
        this.value = midnightBreakTime.subtractFrom(workTimeRange.midnightBindingTime());
    }

    public Minute minute() {
        return value;
    }

    @Override
    public String toString() {
        return HourAndMinute.from(value).toString();
    }
}
