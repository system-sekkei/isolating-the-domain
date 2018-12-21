package example.domain.model.attendance;

import example.domain.type.time.Minute;

/**
 * 休憩時間
 */
public class NormalBreakTime {
    Minute value;

    @Deprecated
    NormalBreakTime() {
    }

    public NormalBreakTime(Minute value) {
        this.value = value;
    }

    public Minute subtractFrom(Minute workMinute) {
        return workMinute.subtract(value.quarterHourRoundUp());
    }

    public Minute toMinute() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
