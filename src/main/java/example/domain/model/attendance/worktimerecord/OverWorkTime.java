package example.domain.model.attendance.worktimerecord;

import example.domain.model.labour_standards_law.DailyOvertimeWork;
import example.domain.type.time.HourAndMinute;
import example.domain.type.time.Minute;

/**
 * 時間外労働時間
 */
public class OverWorkTime {

    Minute value;

    public OverWorkTime(Minute value) {
        this.value = value;
    }

    public OverWorkTime(TotalWorkTime totalWorkTime) {
        this(totalWorkTime, DailyOvertimeWork.legal());
    }

    OverWorkTime(TotalWorkTime totalWorkTime, DailyOvertimeWork dailyOvertimeWork) {
        this(dailyOvertimeWork.overMinute(totalWorkTime.minute()));
    }

    public Minute minute() {
        return value;
    }

    @Override
    public String toString() {
        return HourAndMinute.from(value).toString();
    }
}
