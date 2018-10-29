package example.domain.type.time;

import java.time.DateTimeException;

/**
 * 分(数)
 */
public class Minute {
    int value;

    public Minute (String time) { value = Integer.parseInt(time); }

    public Minute (int time) {
        value = time;
    }

    Minute add(Minute minute) {
        value += minute.value;
        return new Minute(value);
    }

    public Minute subtract(Minute minute) {
        if (value - minute.value < 0) {
            // FIXME エラーメッセージをわかりやすく
            throw new DateTimeException("Error of minus time.");
        }
        value -= minute.value;
        return new Minute(value);
    }

    @Override
    public String toString() {
        return String.format("%d", value);
    }

    public int value() {
        return value;
    }
}
