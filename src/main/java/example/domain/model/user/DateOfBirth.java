package example.domain.model.user;

import java.time.LocalDate;

public class DateOfBirth {

    //@NotNull(message = "誕生日を入力してください。")
    LocalDate value ;

    @Override
    public String toString() {
        if(value == null) return "";
        return value.toString();
    }
}
