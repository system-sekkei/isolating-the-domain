package example.domain.model.attendance.worktimerecord;

import example.domain.type.time.Minute;

/**
 * 休憩時間（深夜）
 */
public class MidnightBreakTime {
    Minute value;

    @Deprecated
    MidnightBreakTime() {
    }

    public MidnightBreakTime(Minute value) {
        this.value = value;
    }

    public Minute subtractFrom(MidnightBindingTime midnightBindingTime) {
        return midnightBindingTime.minute().subtract(value.quarterHourRoundUp());
    }

    public Minute toMinute() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
