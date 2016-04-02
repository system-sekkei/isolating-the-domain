package example.fundamentals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.IllegalFormatException;

/**
 * Created by Masuda on 2016/04/02.
 */
public class DateText {
    String source;

    public DateText(String source) {
        this.source = source;
    }

    static final DateTimeFormatter[] formatters = {
        DateTimeFormatter.ofPattern("yyyy/MM/dd"),
        DateTimeFormatter.ofPattern("yyyy-MM-dd"),
        DateTimeFormatter.ofPattern("yyyyMMdd")
    };

    public LocalDate toLocalDate() {
        for (DateTimeFormatter each : formatters) {
            try {
                return LocalDate.parse(source, each);
            } catch (DateTimeParseException e) {
                // 失敗したら、次のフォーマットで試す
            }
        }
        throw new IllegalArgumentException();
    }
}
