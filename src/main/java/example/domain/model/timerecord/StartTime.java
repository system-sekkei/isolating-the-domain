package example.domain.model.timerecord;

import example.domain.type.time.ClockTime;
import example.domain.type.time.QuarterRoundClockTime;

/**
 * 勤務開始時刻
 */
public class StartTime {

    ClockTime value;

    @Deprecated
    StartTime() {
    }

    public StartTime(ClockTime value) {
        this.value = value;
    }

    public StartTime(String value) {
        this(new ClockTime(value));
    }

    QuarterRoundClockTime normalizedClockTime() {
        return value.quarterRoundUp();
    }

    public boolean isAfter(EndTime endTime) {
        return value.isAfter(endTime.value);
    }

    public ClockTime clockTime() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
