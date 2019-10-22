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

    QuarterRoundClockTime normalizedClockTime() {
        return value.quarterRoundUp();
    }

    public ClockTime clockTime() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
