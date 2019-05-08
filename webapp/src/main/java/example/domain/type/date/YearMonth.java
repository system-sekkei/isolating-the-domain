package example.domain.type.date;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 年月
 */
public class YearMonth {
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM");
    java.time.YearMonth value;

    public YearMonth() {
        value = java.time.YearMonth.now();
    }

    public YearMonth(Year year, Month month) {
        this.value = java.time.YearMonth.of(year.value(), month.value());
    }

    public YearMonth(Integer year, Integer month) {
        this(new Year(year), Month.of(month));
    }

    public YearMonth(String yearMonth) {
        try {
            this.value = java.time.YearMonth.parse(yearMonth, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("月のフォーマットが誤っています", e);
        }
    }

    public YearMonth(java.time.YearMonth month) {
        value = month;
    }

    public Year year() {
        return new Year(value.getYear());
    }

    public Month month() {
        return Month.of(value.getMonthValue());
    }

    public Date start() {
        return new Date(value.atDay(1));
    }

    public Date end() {
        return new Date(value.atEndOfMonth());
    }

    public List<Date> days() {
        IntStream intStream = IntStream.rangeClosed(start().dayOfMonth(), end().dayOfMonth());
        return intStream.mapToObj(i -> new Date(start().value().plusDays((long) i - 1))).collect(Collectors.toList());
    }

    public YearMonth before() {
        return new YearMonth(value.minusMonths(1));
    }

    public YearMonth after() {
        return new YearMonth(value.plusMonths(1));
    }

    @Override
    public String toString() {
        return value.format(FORMATTER);
    }

    public boolean isThisYear() {
        return value.getYear() == java.time.YearMonth.now().getYear();
    }

    public java.time.YearMonth value() {
        return value;
    }
}
