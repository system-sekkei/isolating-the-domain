package example.presentation.controller.timerecord;

import example.domain.model.timerecord.timefact.EndDateTime;
import example.domain.type.date.Date;
import example.domain.type.datetime.DateTime;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 勤務終了日時
 */
public class InputEndTime {
    int hour;
    int minute;

    InputEndTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public static InputEndTime from(String time) {
        String[] s = time.split(":");
        return new InputEndTime(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
    }

    public EndDateTime endDateTime(Date date) {
        Date endDate = isOverFlow() ? date.plusDays(1) : date;
        LocalTime endTime = LocalTime.of(hour % 24, minute);
        return new EndDateTime(new DateTime(LocalDateTime.of(endDate.value(), endTime)));
    }

    boolean isOverFlow() {
        return hour > 23;
    }
}
