package example.domain.model.timerecord.evaluation;

import example.domain.type.date.DayOfWeek;
import example.domain.validation.Required;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate value;

    @Deprecated
    public WorkDate() {
    }

    public WorkDate(LocalDate date) {
        value = date;
    }

    public static WorkDate from(String value) {
        return new WorkDate(LocalDate.parse(value, DateTimeFormatter.ISO_DATE));
    }

    public LocalDate value() {
        return value;
    }

    public int dayOfMonth() {
        return value.getDayOfMonth();
    }

    public DayOfWeek dayOfWeek() {
        return DayOfWeek.of(value.getDayOfWeek());
    }

    public boolean hasSameValue(WorkDate other) {
        return value.equals(other.value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public LocalDate toDate() {
        return value;
    }

    public boolean isBefore(WorkDate workDate) {
        return value.isBefore(workDate.value);
    }

    public boolean sameWeek(LocalDate date) {
        return weekBasedYear(value()) == weekBasedYear(date) && weekOfWeekBasedYear(value()) == weekOfWeekBasedYear(date);
    }

    int weekBasedYear(LocalDate date) {
        return date.get(WeekFields.of(Locale.JAPANESE).weekBasedYear());
    }

    int weekOfWeekBasedYear(LocalDate date) {
        return date.get(WeekFields.of(Locale.JAPANESE).weekOfWeekBasedYear());
    }
}
