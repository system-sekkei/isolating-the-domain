package example.domain.type.date;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 日付
 */
public class Date {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate value;

    public Date(String value) {
        this(LocalDate.parse(value, DateTimeFormatter.ISO_DATE));
    }

    public Date(LocalDate value) {
        this.value = value;
    }

    public LocalDate value() {
        return value;
    }

    public Year year() {
        return new Year(value.getYear());
    }

    public Month month() {
        return Month.of(value.getMonth().getValue());
    }

    public int dayOfMonth() {
        return value.getDayOfMonth();
    }

    public DayOfWeek dayOfWeek() {
        return DayOfWeek.of(value.getDayOfWeek());
    }

    public boolean hasSameValue(Date other) {
        return value.equals(other.value);
    }

    public int compareTo(Date other) {
        return value.compareTo(other.value);
    }

    @Override
    public String toString() {
        return value.format(DateTimeFormatter.ISO_DATE);
    }

}
