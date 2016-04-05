package example.fundamentals;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;

/**
 * Created by Masuda on 2016/04/02.
 */
public class DateText {
    String source;

    public DateText(String source) {
        assert source != null ;
        this.source = source;
    }

    private static final String[] patterns;

    static {
        patterns = new String[]{
                "yyyy/MM/dd",
                "yyyy-MM-dd",
                "yyyyMMdd"
        };
    }

    private static final HashSet<DateTimeFormatter> formatters;

    static {
        formatters = new HashSet<DateTimeFormatter>();
        for( String each : patterns ) formatters.add(DateTimeFormatter.ofPattern(each));
    }

    public LocalDate toLocalDate() {
        for (DateTimeFormatter each : formatters) {
            try {
                return LocalDate.parse(source, each);
            } catch (DateTimeParseException e) {
                // 失敗したら次のパターンを試す
            }
        }
        throw new DateTimeException("日付の形式が正しくありません");
    }
}
