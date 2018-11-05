package example.domain.type.date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 日付
 */
public class DayOfMonth {
    private LocalDate value;

    public DayOfMonth(String dayOfMonth) {
        this(LocalDate.parse(dayOfMonth, DateTimeFormatter.ISO_DATE));
    }

    public DayOfMonth(LocalDate value) {
        this.value = value;
    }

    public LocalDate value() {
        return value;
    }

    public YearMonth yearMonth() {
        return new YearMonth(value.getYear(), value.getYear());
    }

    public Year year() {
        return new Year(value.getYear());
    }

    public Month month() {
        return Month.of(value.getMonth().getValue());
    }

    @Override
    public String toString() {
        return value.format(DateTimeFormatter.ISO_DATE);
    }
}
