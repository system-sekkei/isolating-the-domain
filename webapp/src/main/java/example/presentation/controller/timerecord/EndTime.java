package example.presentation.controller.timerecord;

import example.domain.model.timerecord.timefact.EndDateTime;
import example.domain.model.timerecord.timefact.StartDateTime;
import example.domain.type.date.Date;
import example.domain.type.datetime.DateTime;
import example.domain.validation.FormatCheck;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 勤務終了日時
 */
public class EndTime {
    @Valid
    EndHour hour;

    @Valid
    EndMinute minute;

    public EndTime() {
    }

    EndTime(EndHour hour, EndMinute minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public static EndTime from(String time) {
        String[] s = time.split(":");
        return new EndTime(new EndHour(s[0]), new EndMinute(s[1]));
    }

    public EndDateTime endDateTime(StartDateTime startDateTime) {
        Date date = startDateTime.date();
        // TODO 1日を超える扱い
        Date endDate = isOverFlow() ? new Date(date.value.plusDays(1)) : date;
        LocalTime endTime = LocalTime.of(hour.toInt() % 24, minute.toInt());
        return new EndDateTime(new DateTime(LocalDateTime.of(endDate.value, endTime)));
    }

    boolean isOverFlow() {
        return hour.toInt() > 23;
    }

    boolean valid;

    @AssertTrue(message = "終了時刻が不正です", groups = FormatCheck.class)
    public boolean isValid() {
        try {
            // とりあえず、数値かどうかだけチェック
            int h = hour.toInt();
            int m = minute.toInt();
        } catch (NumberFormatException ex) {
            return false;
        }

        return true;
    }
}
