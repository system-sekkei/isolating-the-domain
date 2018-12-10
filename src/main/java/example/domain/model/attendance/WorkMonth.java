package example.domain.model.attendance;

import example.domain.type.date.Date;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 勤務月
 */
public class WorkMonth {
    YearMonth value;

    public WorkMonth() {
        value = YearMonth.now();
    }

    public WorkMonth(int year, int month) {
        value = YearMonth.of(year, month);
    }

    public List<WorkDay> days() {
        IntStream days = IntStream.rangeClosed(1, endOfMonth());
        List<WorkDay> workDays = days.mapToObj(day -> new WorkDay(LocalDate.of(value.getYear(), value.getMonth(), day)))
                .collect(Collectors.toList());
        return workDays;
    }

    private int endOfMonth() {
        return value.atEndOfMonth().getDayOfMonth();
    }

}
