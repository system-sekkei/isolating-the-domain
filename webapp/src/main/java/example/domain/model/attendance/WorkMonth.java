package example.domain.model.attendance;

import example.domain.model.timerecord.WorkDate;
import example.domain.type.date.Date;
import example.domain.type.date.Month;
import example.domain.type.date.Year;
import example.domain.type.date.YearMonth;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 勤務月
 */
public class WorkMonth {
    YearMonth value;

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

    public static WorkMonth from(WorkDate workDate) {
        return new WorkMonth(workDate.toDate().year(), workDate.toDate().month());
    }

    public List<WorkDate> days() {
        List<Date> days = value.days();
        List<WorkDate> workDates = days.stream().map(WorkDate::new)
                .collect(Collectors.toList());
        return workDates;
    }

    public WorkMonth before() {
        return new WorkMonth(value.before());
    }

    public WorkMonth after() {
        return new WorkMonth(value.after());
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public String toStringWithUnit() {
        if (value.isThisYear()) {
            return String.format("%s月", value.month().toString());
        }
        return String.format("%s年%s月", value.year().toString(), value.month().toString());
    }

    public String yyyyMM() {
        return value.value().format(DateTimeFormatter.ofPattern("uuuuMM"));
    }
}
