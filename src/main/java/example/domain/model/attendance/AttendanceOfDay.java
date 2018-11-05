package example.domain.model.attendance;

import example.domain.model.user.User;
import example.domain.type.date.DayOfMonth;
import example.domain.type.time.HourTime;
import example.domain.type.time.Minute;

import java.time.LocalDate;

/**
 * 日次勤怠
 */
public class AttendanceOfDay {
    User user;
    DayOfMonth dayOfMonth;
    TimeRecord timeRecord;

    public AttendanceOfDay(User user) {
        this.user = user;

        // TODO DUMMY
        this.dayOfMonth = new DayOfMonth(LocalDate.now());
        this.timeRecord = new TimeRecord(
                new HourTime("09:00"),
                new HourTime("15:00"),
                new Minute(30)
        );
    }

    public User user() {
        return user;
    }

    public DayOfMonth dayOfMonth() {
        return dayOfMonth;
    }

    public TimeRecord timeRecord() {
        return timeRecord;
    }
}
