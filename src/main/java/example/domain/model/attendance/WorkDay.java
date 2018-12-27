package example.domain.model.attendance;

import example.domain.type.date.Date;
import example.domain.type.date.DateRange;
import example.domain.type.date.DayOfWeek;

import java.time.LocalDate;

/**
 * 勤務日
 */
public class WorkDay {

    Date value;

    public WorkDay() {
        this(new Date(LocalDate.now()));
    }

    public WorkDay(Date date) {
        value = date;
    }

    public WorkDay(String value) {
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

    public boolean hasSameValue(WorkDay other) {
        return value.hasSameValue(other.value);
    }

    public WorkMonth month() {
        return new WorkMonth(value.year(), value.month());
    }

    public int compareTo(WorkDay other) {
        return value.compareTo(other.value);
    }

    public boolean inRange(DateRange range) {
        return value.inRange(range);
    }

    @Override
    public String toString() {
        return value.toString();
    }


}
