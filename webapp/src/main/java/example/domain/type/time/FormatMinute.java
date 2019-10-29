package example.domain.type.time;

/**
 * 表記用の時間(分)
 */
public class FormatMinute {
    int value;

    FormatMinute(int minute) {
        value = minute;
    }

    @Override
    public String toString() {
        return String.format("%02d", value);
    }

    public int value() {
        return value;
    }
}
