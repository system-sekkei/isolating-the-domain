package example.domain.type.date;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 日付
 */
// TODO: LocalDateでいいのではないかを検討する
public class Date {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public LocalDate value;

    @Deprecated
    public Date() {
    }

    public Date(LocalDate value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.format(DateTimeFormatter.ISO_DATE);
    }

    public String yyyyMMdd() {
        return value.format(DateTimeFormatter.ofPattern("uuuuMMdd"));
    }

}
