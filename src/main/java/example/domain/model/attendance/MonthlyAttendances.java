package example.domain.model.attendance;

import example.domain.type.date.Date;
import example.domain.type.date.YearMonth;
import example.domain.type.time.HourAndMinute;

import java.util.List;

/**
 * 月次勤怠
 */
public class MonthlyAttendances {
    YearMonth yearMonth;
    Attendances attendances;

    public MonthlyAttendances(YearMonth yearMonth, Attendances attendances) {
        this.yearMonth = yearMonth;
        this.attendances = attendances;
    }

    public List<Attendance> list() {
        return attendances.list();
    }

    public HourAndMinute totalWorks() {
        return attendances.totalWorkTime();
    }

    public Attendance attendanceOf(Date day) {
        return attendances.get(day);
    }
}
