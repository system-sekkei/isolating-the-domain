package example.domain.model.attendance.worktimerecord;

import example.domain.type.time.Minute;
import example.domain.type.time.QuarterHour;

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

    public QuarterHour subtractFrom(MidnightBindingTime midnightBindingTime) {
        return midnightBindingTime.quarterHour().subtract(value.quarterHourRoundUp());
    }

    public Minute minute() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
