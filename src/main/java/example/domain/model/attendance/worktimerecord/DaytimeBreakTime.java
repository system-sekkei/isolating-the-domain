package example.domain.model.attendance.worktimerecord;

import example.domain.type.time.Minute;
import example.domain.type.time.QuarterHour;

/**
 * 休憩時間
 */
public class DaytimeBreakTime {
    Minute value;

    @Deprecated
    DaytimeBreakTime() {
    }

    public DaytimeBreakTime(Minute value) {
        this.value = value;
    }

    public QuarterHour subtractFrom(QuarterHour quarterHour) {
        return quarterHour.subtract(value.quarterHourRoundUp());
    }

    public Minute minute() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
