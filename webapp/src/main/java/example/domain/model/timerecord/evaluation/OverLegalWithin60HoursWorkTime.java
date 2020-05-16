package example.domain.model.timerecord.evaluation;

import example.domain.model.legislation.DailyOvertimeWork;
import example.domain.type.time.QuarterHour;

/**
 * 法定時間外労働 月60時間以内 労働時間
 */
public class OverLegalWithin60HoursWorkTime {

    QuarterHour value;

    public OverLegalWithin60HoursWorkTime(WorkTime workTime) {
        this(workTime, DailyOvertimeWork.legal());
    }

    OverLegalWithin60HoursWorkTime(WorkTime workTime, DailyOvertimeWork dailyOvertimeWork) {
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
