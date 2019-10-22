package example.domain.type.time;

/**
 * 画面から入力された時刻 (24時以降を扱う)
 */
public class InputTime {

    Integer hour;
    Integer minute;

    @Deprecated
    InputTime() {
    }

    public InputTime(Integer hour, Integer minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public InputTime(String time) {
        String[] s = time.split(":");
        this.hour = Integer.parseInt(s[0]);
        this.minute = Integer.parseInt(s[1]);
    }

    public ClockTime toClockTime() {
        return new ClockTime(hour % 24, minute);
    }

    public boolean isOverFlow() {
        return hour > 23;
    }

    @Override
    public String toString() {
        return String.format("%d:%02d", hour, minute);
    }
}
