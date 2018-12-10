package example.domain.model.attendance;

import example.domain.type.time.HourAndMinute;

import java.util.List;

/**
 * 月次勤怠
 */
public class MonthlyAttendances {
    WorkMonth month;
    Attendances attendances;

    public MonthlyAttendances(WorkMonth month, Attendances attendances) {
        this.month = month;
        this.attendances = attendances;
    }

    public List<Attendance> list() {
        return attendances.list();
    }

    public HourAndMinute totalWorks() {
        return attendances.totalWorkTime();
    }

    public Attendance attendanceOf(WorkDay day) {
        return attendances.atWorkDay(day);
    }
}
