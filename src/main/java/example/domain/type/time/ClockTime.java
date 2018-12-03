package example.domain.type.time;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 時刻を時分単位で表す
 */
public class ClockTime {
    LocalTime value;

    public ClockTime(LocalTime value) {
        this.value = value;
    }

    public ClockTime(String value) {
        this(LocalTime.parse(value, DateTimeFormatter.ofPattern("H:mm")));
    }

    public ClockTime(int hour, int minute) {
        value = LocalTime.of(hour, minute);
    }

    public LocalTime value() {
        return value;
    }

    @Override
    public String toString() {
        return value.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public Minute until(ClockTime other) {
        return new ClockTimeRange(this, other).between().toMinute();
    }

    public ClockTime quarterRoundDown() {
        int tickPeriod = tickPeriod().value;
        int normalMinute = value.getMinute() / tickPeriod * tickPeriod;
        return new ClockTime(value().withMinute(normalMinute));
    }

    public static Minute tickPeriod() {
        return new Minute(15);
    }
}
