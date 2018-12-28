package example.domain.model.attendance.worktimerecord;

import example.domain.type.time.HourAndMinute;
import example.domain.type.time.Minute;

/**
 * 休憩時間合計
 */
public class TotalBreakTime {

    Minute value;

    public TotalBreakTime(NormalBreakTime normalBreakTime, MidnightBreakTime midnightBreakTime) {
        value = normalBreakTime.toMinute().add(midnightBreakTime.toMinute());
    }

    public Minute minute() {
        return value;
    }

    @Override
    public String toString() {
        return HourAndMinute.from(value).toString();
    }
}
