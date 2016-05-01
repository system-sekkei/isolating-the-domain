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
    LocalDate date ;
    String source;

    boolean valid = true;

    public DateOfBirth(@NotNull String source) {
        this.source = source;

        DateText dateText = new DateText(source);
        try {
            date = dateText.toLocalDate();
        } catch ( DateTimeException exception) {
            valid = false;
        }
    }

    public DateOfBirth() {
        date = null; // XXX 未指定の表現方法を検討する
        source = "";
    }

    @AssertTrue(message = "日付が正しくありません。")
    public boolean isValid() {
        if(source.equals("")) return true;
        return valid;
    }

    @Override
    public String toString() {
        if(valid) return date.toString();
        return source;
    }
}
