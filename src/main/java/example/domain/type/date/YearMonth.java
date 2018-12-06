package example.domain.type.date;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 年月
 */
public class YearMonth {
    Year year;
    Month month;

    public YearMonth(Year year, Month month) {
        this.year = year;
        this.month = month;
    }

    public YearMonth(Integer year, Integer month) {
        this(new Year(year), Month.of(month));
    }

    static Pattern yearMonthPattern = Pattern.compile("(\\d{4})-(\\d{1,2})");

    public YearMonth(String yearMonth) {
        Matcher m = yearMonthPattern.matcher(yearMonth);
        if (m.matches()) {
            this.year = new Year(m.group(1));
            this.month = Month.of(m.group(2));
        } else {
            throw new IllegalArgumentException("月のフォーマットが誤っています");
        }
    }

    public Year year() {
        return year;
    }

    public Month month() {
        return month;
    }

    public Date start() {
        return new Date(LocalDate.of(year().value(), month().value(), 1));
    }

    public Date end() {
        LocalDate endOfMonth = java.time.YearMonth.of(year.value(), month().value()).atEndOfMonth();
        return new Date(endOfMonth);
    }

    public List<Date> days() {
        IntStream intStream = IntStream.rangeClosed(start().dayOfMonth(), end().dayOfMonth());
        return intStream.mapToObj(i -> new Date(start().value().plusDays((long) i - 1))).collect(Collectors.toList());
    }

    public String toString() {
        return String.format("%s-%s", year, month);
    }
}
