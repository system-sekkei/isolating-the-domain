package example.domain.model.workrecord;

import example.domain.type.time.ClockTime;
import example.domain.type.time.QuarterRoundClockTime;

/**
 * 勤務終了時刻
 */
public class WorkEndTime {

    ClockTime value;

    @Deprecated
    WorkEndTime() {
    }

    public WorkEndTime(ClockTime value) {
        this.value = value;
    }

    public WorkEndTime(String value) {
        this(new ClockTime(value));
    }

    QuarterRoundClockTime normalizedClockTime() {
        return value.quarterRoundDown();
    }

    public ClockTime clockTime() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
