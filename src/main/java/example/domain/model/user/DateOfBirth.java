package example.domain.model.user;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class DateOfBirth {

    @NotNull(message = "誕生日を入力してください。")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate value;

    @Override
    public String toString() {
        if (value == null) return "";
        return value.toString();
    }
}
