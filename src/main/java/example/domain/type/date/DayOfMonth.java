package example.domain.type.date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DayOfMonth {
    private LocalDate value;
    public DayOfMonth(String dayOfMonth) {
        value = LocalDate.parse(dayOfMonth, DateTimeFormatter.ofPattern("yyyy-M-d"));
    }
    public DayOfMonth(LocalDate dayOfMonth) {
        value = dayOfMonth;
    }

    public LocalDate value() {return value;}
    public YearMonth yearMonth() {
        return new YearMonth(value.getYear(), value.getYear());
    }
    public Year year() {
        return new Year(value.getYear());
    }
    public Month month() {
        return Month.of(value.getMonth().getValue());
    }
}
