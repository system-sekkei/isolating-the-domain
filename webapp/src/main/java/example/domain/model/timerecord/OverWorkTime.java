package example.domain.model.timerecord;

import example.domain.model.legislation.DailyOvertimeWork;
import example.domain.type.time.QuarterHour;

/**
 * 時間外勤務時間
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
