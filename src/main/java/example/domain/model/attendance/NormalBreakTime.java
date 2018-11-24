package example.domain.model.attendance;

import example.domain.type.time.Minute;

/**
 * 休憩時間
 */
public class NormalBreakTime {
    Minute value;

    public NormalBreakTime(String value) {
        this(new Minute(value));
    }

    public NormalBreakTime(Minute value) {
        this.value = value;
    }

    public Minute subtractFrom(Minute workMinute) {
        return workMinute.subtract(value.quarterHourRoundUp());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
