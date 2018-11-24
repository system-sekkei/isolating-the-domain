package example.domain.type.time;

import java.time.DateTimeException;

/**
 * 分(数)
 */
public class Minute {
    int value;

    public Minute(String time) {
        value = Integer.parseInt(time);
    }

    public Minute(int time) {
        value = time;
    }

    public Minute subtract(Minute minute) {
        if (value < minute.value) {
            // FIXME エラーメッセージをわかりやすく
            throw new DateTimeException("Error of minus time.");
        }
        return add(0 - minute.value);
    }

    public Minute add(Minute minute) {
        return add(minute.value);
    }

    private Minute add(int value) {
        return new Minute(this.value + value);
    }

    @Override
    public String toString() {
        return String.format("%d", value);
    }

    public int value() {
        return value;
    }

    public Minute quarterHourRoundUp() {
        return (value % 15 == 0) ? this : new Minute((value / 15 + 1) * 15);
    }
}
