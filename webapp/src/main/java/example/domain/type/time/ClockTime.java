package example.domain.type.time;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

/**
 * 時刻を時分単位で表す
 */
public class ClockTime {

    LocalTime value;

    @Deprecated
    ClockTime() {
    }

    public ClockTime(String value) {
        this.value = LocalTime.parse(value, DateTimeFormatter.ofPattern("H:m").withResolverStyle(ResolverStyle.LENIENT));
    }

    public ClockTime(Integer hour, Integer minute) {
        this.value = LocalTime.of(hour, minute);
    }

    // FIXME 時刻のClockTimeが時間のHourやMinuteを受け取るのはおかしい
    public ClockTime(Hour hour, Minute minute) {
        this(hour.value, minute.value);
    }

    public static ClockTime later(ClockTime a, ClockTime b) {
        return a.isAfter(b) ? a : b;
    }

    public static ClockTime faster(ClockTime a, ClockTime b) {
        return a.isBefore(b) ? a : b;
    }

    @Override
    public String toString() {
        return String.format("%d:%02d", this.value.getHour(), this.value.getMinute());
    }

    public QuarterRoundClockTime quarterRoundDown() {
        QuarterHour quarterHour = minute().quarterHourRoundDown();
        return new QuarterRoundClockTime(new ClockTime(hour(), quarterHour.minute()));
    }

    public QuarterRoundClockTime quarterRoundUp() {
        QuarterHour quarterHour = minute().quarterHourRoundUp();
        return new QuarterRoundClockTime(new ClockTime(hour(), quarterHour.minute()));
    }

    public boolean isAfter(ClockTime other) {
        Minute thisMinute = hour().toMinute().add(minute());
        Minute otherMinute = other.hour().toMinute().add(other.minute());
        return otherMinute.lessThan(thisMinute);
    }

    public boolean isBefore(ClockTime other) {
        Minute thisMinute = hour().toMinute().add(minute());
        Minute otherMinute = other.hour().toMinute().add(other.minute());
        return thisMinute.lessThan(otherMinute);
    }

    public Hour hour() {
        return new Hour(this.value.getHour());
    }

    public Minute minute() {
        return new Minute(this.value.getMinute());
    }

    public Minute betweenMinute(ClockTime other) {
        Duration duration = Duration.between(this.value, other.value);
        return new Minute((int) duration.toMinutes());
    }

    public boolean sameTime(ClockTime other) {
        return this.value.equals(other.value);
    }
}
