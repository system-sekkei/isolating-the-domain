package example.domain.model.attendance.worktimerecord;

import example.domain.type.time.Minute;

/**
 * 深夜労働時間
 */
public class MidnightWorkTime {

    Minute value;

    public MidnightWorkTime(Minute value) {
        this.value = value;
    }

    public MidnightWorkTime(WorkTimeRange workTimeRange, MidnightBreakTime midnightBreakTime) {
        this(midnightBreakTime.subtractFrom(workTimeRange.midnightWorkMinute()));
    }

    public Minute minute() {
        return value;
    }
}
