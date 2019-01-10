package example.domain.model.timerecord;

import example.domain.type.time.ClockTime;
import example.domain.type.time.QuarterRoundClockTime;

/**
 * 勤務終了時刻
 */
public class EndTime {

    ClockTime value;

    @Deprecated
    EndTime() {
    }

    public EndTime(ClockTime value) {
        this.value = value;
    }

    public EndTime(String value) {
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
