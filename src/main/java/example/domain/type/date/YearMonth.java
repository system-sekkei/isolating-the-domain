package example.domain.type.date;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
        if(m.matches()) {
            this.year = new Year(m.group(1));
            this.month = Month.of(m.group(2));
        } else {
            throw new IllegalArgumentException("月のフォーマットが誤っています");
        }
    }

    public Year year() { return year; }
    public Month month() { return month;}

    public DayOfMonth start() {
        return new DayOfMonth(LocalDate.of(year().value(), month().value(), 1));
    }

    public DayOfMonth end() {
        LocalDate tmp = LocalDate.of(year().value(), month().value() + 1, 1);
        return new DayOfMonth(tmp.minusDays(1L));
    }

    public Stream<DayOfMonth> days() {
        Spliterator<DayOfMonth> spliterator = Spliterators.spliteratorUnknownSize(
                new DaysIterator(), 0);
        return StreamSupport.stream(spliterator, false);
    }

    public String toString() {
        return String.format("%s-%s", year, month);
    }

    class DaysIterator implements Iterator<DayOfMonth> {
        private DayOfMonth current = start();
        @Override
        public boolean hasNext() {
            return current.value().compareTo(end().value()) <= 0;
        }

        @Override
        public DayOfMonth next() {
            if(!hasNext()) throw new NoSuchElementException();
            DayOfMonth ret = current;
            current = new DayOfMonth(current.value().plusDays(1L));
            return ret;
        }
    }
}
