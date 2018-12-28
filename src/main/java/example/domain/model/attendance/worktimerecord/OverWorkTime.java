package example.domain.model.attendance.worktimerecord;

import example.domain.model.labour_standards_law.DailyOvertimeWork;
import example.domain.type.time.HourAndMinute;
import example.domain.type.time.Minute;

/**
 * 時間外労働時間
 */
public class OverWorkTime {

    Minute value;

    public OverWorkTime(WorkTime workTime) {
        this(workTime, DailyOvertimeWork.legal());
    }

    OverWorkTime(WorkTime workTime, DailyOvertimeWork dailyOvertimeWork) {
        this.value = dailyOvertimeWork.overMinute(workTime.minute());
    }

    public Minute minute() {
        return value;
    }

    @Override
    public String toString() {
        return HourAndMinute.from(value).toString();
    }
}
