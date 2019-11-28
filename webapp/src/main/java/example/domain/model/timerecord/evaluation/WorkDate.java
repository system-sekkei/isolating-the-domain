package example.domain.model.timerecord.evaluation;

import example.domain.type.date.Date;
import example.domain.type.date.DayOfWeek;
import example.domain.type.date.WeekOfMonth;

/**
 * 勤務日付
 */
public class WorkDate {

    Date value;

    @Deprecated
    public WorkDate() {
    }

    public WorkDate(Date date) {
        value = date;
    }

    public static WorkDate from(String value) {
        return new WorkDate(Date.from(value));
    }

    public Date value() {
        return value;
    }

    public int dayOfMonth() {
        return value.dayOfMonth();
    }

    public DayOfWeek dayOfWeek() {
        return value.dayOfWeek();
    }

    public WeekOfMonth weekOfMonth() {
        return value.weekOfMonth();
    }

    public boolean hasSameValue(WorkDate other) {
        return value.hasSameValue(other.value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public Date toDate() {
        return value;
    }
}
