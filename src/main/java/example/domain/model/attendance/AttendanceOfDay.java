package example.domain.model.attendance;

import example.domain.model.user.User;
import example.domain.type.date.Date;
import example.domain.type.time.HourTime;
import example.domain.type.time.Minute;

import java.time.LocalDate;

/**
 * 日次勤怠
 */
public class AttendanceOfDay {
    Date date;
    TimeRecord timeRecord;

    public AttendanceOfDay() {
        this.date = new Date(LocalDate.now());
        this.timeRecord = new TimeRecord(
                new HourTime("09:00"),
                new HourTime("15:00"),
                new Minute(30)
        );
    }

    public AttendanceOfDay(User user, Date day, TimeRecord timeRecord) {
        this.date = day;
        this.timeRecord = timeRecord;
    }

    public Date dayOfMonth() {
        return date;
    }

    public TimeRecord timeRecord() {
        return timeRecord;
    }
}
