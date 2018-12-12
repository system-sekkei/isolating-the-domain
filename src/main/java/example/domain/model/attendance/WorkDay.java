package example.domain.model.attendance;

import example.domain.type.date.Date;
import example.domain.type.date.DayOfWeek;

import java.time.LocalDate;

/**
 * 勤務日
 */
public class WorkDay {

    Date value;

    public WorkDay() {
        this(LocalDate.now());
    }

    public WorkDay(LocalDate value) {
        this.value = new Date(value);
    }

    public WorkDay(Date date) {
        value = date;
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

    public boolean hasSameValue(WorkDay other){
        return value.hasSameValue(other.value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
