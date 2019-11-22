package example.domain.type.time;

/**
 * x時間y分
 */
public class HourAndMinute {
    Hour hour;
    Minute minute;

    HourAndMinute(Hour hour, Minute minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public static HourAndMinute from(Minute minute) {
        return from(minute.value);
    }

    static HourAndMinute from(int minute) {
        Hour quotient = new Hour(minute / 60);
        Minute remainder = new Minute(minute % 60);
        return new HourAndMinute(quotient, remainder);
    }

    public Minute toMinute() {
        return minute.add(hour.toMinute());
    }

    @Override
    public String toString() {
        return String.format("%d時間%d分", hour.value, minute.value);
    }
}
