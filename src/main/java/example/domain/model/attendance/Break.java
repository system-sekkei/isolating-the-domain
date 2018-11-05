package example.domain.model.attendance;

import example.domain.type.time.Minute;

/**
 * 休憩時間
 */
public class Break {
    Minute value;

    public Break(Minute value) {
        this.value = value;
    }

    Minute normalizeValue() {
        return (value.value() % 15 == 0) ? value : new Minute((value.value() / 15 + 1) * 15);
    }

    public Minute subtractFrom(Minute workMinute) {
        return workMinute.subtract(normalizeValue());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
