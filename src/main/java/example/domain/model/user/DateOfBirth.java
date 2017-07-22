package example.domain.model.user;

import example.domain.fundamentals.DateText;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * Created by Masuda on 2016/04/02.
 */
public class DateOfBirth {

    //@NotNull(message = "誕生日を入力してください。")
    LocalDate value ;

    @Override
    public String toString() {
        if(value == null) return "";
        return value.toString();
    }
}
