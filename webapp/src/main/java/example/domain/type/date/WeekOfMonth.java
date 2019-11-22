package example.domain.type.date;

/**
 * 週番号
 */
public class WeekOfMonth {
    int value;

    public WeekOfMonth(int value) {
        this.value = value;
    }

    public String toString() {
        return String.valueOf(value);
    }

    public boolean hasSameValue(WeekOfMonth other) {
        return value == other.value;
    }
}
