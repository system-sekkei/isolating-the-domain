package example._experimental.spa.controller.timerecord;

import example.domain.model.timerecord.timefact.EndDate;
import example.domain.model.timerecord.timefact.EndDateTime;
import example.domain.model.timerecord.timefact.EndTime;
import example.domain.type.date.Date;
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
        EndDate endDate = isOverFlow() ? new EndDate(date.plusDays(1)) : new EndDate(date);
        EndTime endTime = new EndTime(new ClockTime(hour % 24, minute));
        return new EndDateTime(endDate, endTime);
    }

    boolean isOverFlow() {
        return hour > 23;
    }
}
