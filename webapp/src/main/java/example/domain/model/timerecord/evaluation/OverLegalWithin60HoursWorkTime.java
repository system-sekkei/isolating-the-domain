package example.domain.model.timerecord.evaluation;

import example.domain.model.attendance.Attendance;
import example.domain.type.time.QuarterHour;

/**
 * 法定時間外労働 月60時間以内 労働時間
 */
public class OverLegalWithin60HoursWorkTime {

    QuarterHour value;

    public OverLegalWithin60HoursWorkTime(QuarterHour value) {
        this.value = value;
    }

    public static OverLegalWithin60HoursWorkTime daily(ActualWorkDateTime actualWorkDateTime, Attendance attendance) {
        // TODO:
        return null;
    }

    public QuarterHour quarterHour() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
