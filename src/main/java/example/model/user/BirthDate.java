package example.model.user;

import example.model.user.validation.OnRegister;
import example.model.user.validation.OnUpdate;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import java.time.DateTimeException;
import java.time.LocalDate;

public class BirthDate {
    Integer year;
    Integer month;
    Integer day;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public LocalDate getValue() {
        if (isEmpty()) {
            return null;
        }
        return LocalDate.of(year, month, day);
    }

    public void setValue(LocalDate date) {
        this.year = date.getYear();
        this.month = date.getMonthValue();
        this.day = date.getDayOfMonth();
    }

    @AssertFalse(message = "生年月日を入力してください", groups = OnUpdate.class)
    public boolean isEmpty() {
        if (year == null || month == null || day == null) {
            return true;
        }
        return false;
    }

    @AssertTrue(message = "生年月日が不正です", groups = {OnRegister.class, OnUpdate.class})
    public boolean isValid() {
        if (isEmpty()) {
            return true;
        }
        try {
            getValue();
        } catch (NumberFormatException | DateTimeException e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
