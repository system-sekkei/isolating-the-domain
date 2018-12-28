package example.domain.type.time;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 時刻を時分単位で表す
 */
public class ClockTime {

    String value;

    @Deprecated
    ClockTime() {
    }

    public ClockTime(LocalTime value) {
        // TODO 廃止
        this(value.format(DateTimeFormatter.ofPattern("H:mm")));
    }

    public ClockTime(String value) {
        if (!value.matches("\\d{1,2}:\\d{2}(:\\d{2})?")) throw new IllegalArgumentException(value);
        String[] split = value.split(":");
        this.value = String.format("%d:%02d", Integer.valueOf(split[0]), Integer.valueOf(split[1]));
    }

    public ClockTime(Integer hour, Integer minute) {
        this.value = String.format("%d:%02d", hour, minute);
    }

    public LocalTime value() {
        // TODO 廃止
        return LocalTime.parse(value, DateTimeFormatter.ofPattern("H:mm"));
    }

    @Override
    public String toString() {
        return value;
    }

    public Minute until(ClockTime other) {
        return new ClockTimeRange(this, other).between().toMinute();
    }

    public ClockTime quarterRoundDown() {
        int tickPeriod = tickPeriod().value;
        int normalMinute = value().getMinute() / tickPeriod * tickPeriod;
        return new ClockTime(value().withMinute(normalMinute));
    }

    public static Minute tickPeriod() {
        return new Minute(15);
    }

    public boolean isAfter(ClockTime other) {
        Minute thisMinute = hour().toMinute().add(minute());
        Minute otherMinute = other.hour().toMinute().add(other.minute());
        return otherMinute.lessThan(thisMinute);
    }

    public Hour hour() {
        return new Hour(Integer.valueOf(value.split(":")[0]));
    }

    public Minute minute() {
        return new Minute(Integer.valueOf(value.split(":")[1]));
    }
}
