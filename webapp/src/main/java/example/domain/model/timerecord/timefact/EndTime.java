package example.domain.model.timerecord.timefact;

import example.domain.type.time.ClockTime;
import example.domain.type.time.QuarterRoundClockTime;

import java.time.Duration;
import java.time.LocalTime;

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

    @Override
    public String toString() {
        return value.toString();
    }

    public String clockTimeTextOverDays(int days) {
        LocalTime value = this.value.value();
        Duration duration = Duration.ofDays(days)
                .plusHours(value.getHour())
                .plusMinutes(value.getMinute());

        return String.format("%02d:%02d",
                duration.toHours(),
                // duration.toMinutesPart() はJava9から
                duration.toMinutes() % 60
        );
    }
}
