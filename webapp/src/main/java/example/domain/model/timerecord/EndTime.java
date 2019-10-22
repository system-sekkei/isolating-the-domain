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

    QuarterRoundClockTime normalizedClockTime() {
        return value.quarterRoundDown();
    }

    public boolean isAfter(StartTime startTime) {
        return value.isAfter(startTime.value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
