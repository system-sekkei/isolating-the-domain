package example.domain.type.time;

/**
 * 時刻を30時制で表す
 */
public class ThirtyHourFormatTime {
    ThirtyFormatHour hour;
    FormatMinute minute;

    ThirtyHourFormatTime(ThirtyFormatHour hour, FormatMinute minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public static ThirtyHourFormatTime from(Integer hour, Integer minute) {
        return new ThirtyHourFormatTime(new ThirtyFormatHour(hour), new FormatMinute(minute));
    }

    public static ThirtyHourFormatTime from(String time) {
        String[] s = time.split(":");
        return from(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
    }

    public ClockTime toClockTime() {
        return new ClockTime(hour.to24Hour(), minute.value());
    }

    public boolean isOverFlow() {
        return hour.isOverFlow();
    }

    @Override
    public String toString() {
        return hour + ":" + minute;
    }
}
