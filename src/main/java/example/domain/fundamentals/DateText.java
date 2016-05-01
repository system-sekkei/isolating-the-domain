package example.domain.fundamentals;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
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
                "uuuu/M/d",
                "uuuu-M-d",
                "uuuuMMdd",
                "uuuu M d"
                //uuuu for STRICT Resolve. yyyy causes parse error
        };
    }

    private static final HashSet<DateTimeFormatter> formatters;
    private static final ResolverStyle style = ResolverStyle.STRICT;

    static {
        formatters = new HashSet<DateTimeFormatter>();
        for( String each : patterns ) formatters.add(
                DateTimeFormatter.ofPattern(each).withResolverStyle(style));
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
