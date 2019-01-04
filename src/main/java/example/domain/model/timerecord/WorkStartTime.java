package example.domain.model.timerecord;

import example.domain.type.time.ClockTime;
import example.domain.type.time.QuarterRoundClockTime;

/**
 * 勤務開始時刻
 */
public class WorkStartTime {

    ClockTime value;

    @Deprecated
    WorkStartTime() {
    }

    public WorkStartTime(ClockTime value) {
        this.value = value;
    }

    public WorkStartTime(String value) {
        this(new ClockTime(value));
    }

    QuarterRoundClockTime normalizedClockTime() {
        return value.quarterRoundUp();
    }

    public boolean isAfter(WorkEndTime workEndTime) {
        return value.isAfter(workEndTime.value);
    }

    public ClockTime clockTime() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
