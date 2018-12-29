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

    @Deprecated
    public Date() {
    }

    public Date(String value) {
        this(LocalDate.parse(value, DateTimeFormatter.ISO_DATE));
    }

    public Date(LocalDate value) {
        this.value = value;
    }

    public static Date distantPast() {
        return new Date(LocalDate.of(1, 1, 1));
    }

    public static Date distantFuture() {
        return new Date(LocalDate.of(9999, 12, 31));
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

    public Date previousDay() {
        return new Date(value.minusDays(1));
    }

    public boolean isAfter(Date date) {
        return value.isAfter(date.value);
    }
}
