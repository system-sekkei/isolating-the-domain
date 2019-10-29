package example.domain.type.time;

/**
 * 30時制表記の時間を表す
 */
public class ThirtyFormatHour {
    int value;

    ThirtyFormatHour(int hour) {
        value = hour;
    }

    @Override
    public String toString() {
        return String.format("%d", value);
    }

    int to24Hour() {
        return value % 24;
    }

    boolean isOverFlow() {
        return value > 23;
    }
}
