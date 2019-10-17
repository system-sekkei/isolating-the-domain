package example.domain.model.timerecord;

import example.domain.type.date.Date;
import example.domain.type.date.DayOfWeek;

/**
 * 勤務開始日付
 */
public class StartDate {

    Date value;

    @Deprecated
    public StartDate() {
    }

    public StartDate(Date date) {
        value = date;
    }

    public StartDate(String value) {
        this(new Date(value));
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

    public boolean hasSameValue(StartDate other) {
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
