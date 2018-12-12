package example.domain.model.attendance;

import example.domain.type.date.Date;
import example.domain.type.date.YearMonth;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 勤務月
 */
public class WorkMonth {
    YearMonth value;

    public WorkMonth() {
        value = new YearMonth();
    }

    public WorkMonth(int year, int month) {
        value = new YearMonth(year, month);
    }

    public List<WorkDay> days(){
        List<Date> days = value.days();
        List<WorkDay> workDays = days.stream().map(WorkDay::new)
                .collect(Collectors.toList());
        return workDays;
    }

    public String getEndingWithCondition(){
        return value.toEndingWithCondition();
    }

}
