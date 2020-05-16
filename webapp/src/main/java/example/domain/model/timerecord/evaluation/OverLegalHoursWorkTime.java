package example.domain.model.timerecord.evaluation;

import example.domain.model.legislation.DailyOvertimeWork;
import example.domain.type.time.QuarterHour;

/**
 * 法定時間外労働 労働時間
 */
public class OverLegalHoursWorkTime {
    QuarterHour value;

    public OverLegalHoursWorkTime(WorkTime workTime) {
        this(workTime, DailyOvertimeWork.legal());
    }

    OverLegalHoursWorkTime(WorkTime workTime, DailyOvertimeWork dailyOvertimeWork) {
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
