package example.domain.type.time;

/**
 * 時刻を時分単位で表す
 */
public class ClockTime {

    String value;

    @Deprecated
    ClockTime() {
    }

    public ClockTime(String value) {
        if (!value.matches("\\d{1,2}:\\d{2}(:\\d{2})?")) throw new IllegalArgumentException(value);
        String[] split = value.split(":");
        this.value = String.format("%d:%02d", Integer.valueOf(split[0]), Integer.valueOf(split[1]));
    }

    public ClockTime(Integer hour, Integer minute) {
        this.value = String.format("%d:%02d", hour, minute);
    }

    public ClockTime(Hour hour, Minute minute) {
        this(hour.value, minute.value);
    }

    @Override
    public String toString() {
        return value;
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
        return new Hour(Integer.valueOf(value.split(":")[0]));
    }

    public Minute minute() {
        return new Minute(Integer.valueOf(value.split(":")[1]));
    }

    public Minute betweenMinute(ClockTime other) {
        Minute thisMinute = hour().toMinute().add(minute());
        Minute otherMinute = other.hour().toMinute().add(other.minute());

        if (thisMinute.lessThan(otherMinute)) {
            return otherMinute.subtract(thisMinute);
        }
        return thisMinute.subtract(otherMinute);
    }
}
