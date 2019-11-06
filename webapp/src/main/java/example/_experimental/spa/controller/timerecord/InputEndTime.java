package example._experimental.spa.controller.timerecord;

import example.domain.model.timerecord.timefact.EndDateTime;
import example.domain.type.date.Date;
import example.domain.type.datetime.DateTime;
import example.domain.type.time.ClockTime;

/**
 * 勤務終了日時
 */
public class InputEndTime {
    Integer hour;
    Integer minute;

    InputEndTime(Integer hour, Integer minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public static InputEndTime from(String time) {
        String[] s = time.split(":");
        return new InputEndTime(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
    }

    public EndDateTime endDateTime(Date date) {
        Date endDate = isOverFlow() ? date.plusDays(1) : date;
        ClockTime endTime = new ClockTime(hour % 24, minute);
        return new EndDateTime(new DateTime(endDate, endTime));
    }

    boolean isOverFlow() {
        return hour > 23;
    }
}
