package example.domain.model.attendance.worktimerecord;

import example.domain.model.labour_standards_law.DailyOvertimeWork;
import example.domain.type.time.QuarterHour;

/**
 * 時間外労働時間
 */
public class OverWorkTime {

    QuarterHour value;

    public OverWorkTime(WorkTime workTime) {
        this(workTime, DailyOvertimeWork.legal());
    }

    OverWorkTime(WorkTime workTime, DailyOvertimeWork dailyOvertimeWork) {
        this.value = new QuarterHour(dailyOvertimeWork.overMinute(workTime.quarterHour().minute()));
    }

    public QuarterHour quarterHour() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
