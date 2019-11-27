package example.domain.type.date;

import org.springframework.core.convert.converter.Converter;

public class DateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String source) {
        if (source.isEmpty()) return null;

        return Date.from(source);
    }
}
