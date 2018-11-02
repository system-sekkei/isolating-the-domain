package example.domain.type.date;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public String toString() {
        return String.format("%s-%s", year, month);
    }
}
