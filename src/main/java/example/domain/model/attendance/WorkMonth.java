package example.domain.model.attendance;

import example.domain.type.date.Date;
import example.domain.type.date.Month;
import example.domain.type.date.Year;
import example.domain.type.date.YearMonth;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 勤務月
 */
public class WorkMonth {
    YearMonth value;

    @Deprecated
    public WorkMonth() {
        this(new YearMonth());
    }

    public WorkMonth(int year, int month) {
        this(new YearMonth(year, month));
    }

    public WorkMonth(Year year, Month month) {
        this(new YearMonth(year, month));
    }

    public WorkMonth(String yearMonth) {
        this(new YearMonth(yearMonth));
    }

    public WorkMonth(YearMonth value) {
        this.value = value;
    }

    public List<WorkDay> days() {
        List<Date> days = value.days();
        List<WorkDay> workDays = days.stream().map(WorkDay::new)
                .collect(Collectors.toList());
        return workDays;
    }

    public String getEndingWithCondition() {
        return value.toEndingWithCondition();
    }

    public WorkMonth before() {
        return new WorkMonth(value.before());
    }

    public WorkMonth after() {
        return new WorkMonth(value.after());
    }

    public String toString() {
        return value.toString();
    }

    public String toStringWithUnit() {
        if (value.isThisYear()) {
            return String.format("%s月", value.month().toString());
        }
        return String.format("%s年%s月", value.year().toString(), value.month().toString());
    }

}
