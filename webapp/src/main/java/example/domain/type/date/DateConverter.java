package example.domain.type.date;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String source) {
        if (source.isEmpty()) return null;

        return new Date(LocalDate.parse(source, DateTimeFormatter.ISO_DATE));
    }
}
