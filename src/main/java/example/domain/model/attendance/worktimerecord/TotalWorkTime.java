package example.domain.model.attendance.worktimerecord;

import example.domain.type.time.HourAndMinute;
import example.domain.type.time.Minute;

/**
 * 労働時間合計
 */
public class TotalWorkTime {

    Minute value;

    public TotalWorkTime(WorkTimeRange workTimeRange, TotalBreakTime totalBreakTime) {
        this(workTimeRange.bindingTime().minute().subtract(totalBreakTime.minute()));
    }

    public TotalWorkTime(Minute value) {
        this.value = value;
    }

    @Deprecated
    public Minute minute() {
        // TODO １箇所しかつかわれてないしMinuteに変換せずにできるはず
        return value;
    }

    @Override
    public String toString() {
        return HourAndMinute.from(value).toString();
    }
}
