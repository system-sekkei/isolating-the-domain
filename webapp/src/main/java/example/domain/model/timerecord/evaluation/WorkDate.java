package example.domain.model.timerecord.evaluation;

import example.domain.validation.Required;
import example.domain.type.date.Date;
import example.domain.type.date.DayOfWeek;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;

/**
 * 勤務日付
 */
public class WorkDate {

    @Valid
    @NotNull(message = "勤務日を入力してください", groups = Required.class)
    Date value;

    @Deprecated
    public WorkDate() {
    }

    public WorkDate(Date date) {
        value = date;
    }

    public static WorkDate from(String value) {
        return new WorkDate(new Date(LocalDate.parse(value, DateTimeFormatter.ISO_DATE)));
    }

    public Date value() {
        return value;
    }

    public int dayOfMonth() {
        return value.value.getDayOfMonth();
    }

    public DayOfWeek dayOfWeek() {
        return DayOfWeek.of(value.value.getDayOfWeek());
    }

    public boolean hasSameValue(WorkDate other) {
        return value.value.equals(other.value.value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public Date toDate() {
        return value;
    }

    public boolean isBefore(WorkDate workDate) {
        return value.value.isBefore(workDate.value.value);
    }

    public boolean sameWeek(Date date) {
        return weekBasedYear(value()) == weekBasedYear(date) && weekOfWeekBasedYear(value()) == weekOfWeekBasedYear(date);
    }

    int weekBasedYear(Date date) {
        return date.value.get(WeekFields.of(Locale.JAPANESE).weekBasedYear());
    }

    int weekOfWeekBasedYear(Date date) {
        return date.value.get(WeekFields.of(Locale.JAPANESE).weekOfWeekBasedYear());
    }
}
