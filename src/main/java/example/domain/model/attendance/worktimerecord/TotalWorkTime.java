package example.domain.model.attendance.worktimerecord;

import example.domain.type.time.Minute;

/**
 * 労働時間合計
 */
public class TotalWorkTime {

    Minute value;

    public TotalWorkTime(WorkTimeRange workTimeRange, TotalBreakTime totalBreakTime) {
        BindingTime bindingTime = workTimeRange.bindingTime();
        this.value = bindingTime.minute().subtract(totalBreakTime.minute());
    }

    @Deprecated
    public Minute minute() {
        // TODO １箇所しかつかわれてないしMinuteに変換せずにできるはず
        return value;
    }
}
