package example.domain.model.attendance;

import example.domain.type.date.YearMonth;
import example.domain.type.time.HourAndMinute;
import example.domain.type.time.Minute;

import java.util.List;

/**
 * 月次勤怠
 */
public class MonthlyAttendances {
    YearMonth yearMonth;
    List<AttendanceOfDay> list;

    public MonthlyAttendances(YearMonth yearMonth, List<AttendanceOfDay> list) {
        this.yearMonth = yearMonth;
        this.list = list;
    }

    public List<AttendanceOfDay> list() {
        return list;
    }

    public String totalTime() {
        int minute = 0;
        for (AttendanceOfDay attendanceOfDay : list) {
            minute += attendanceOfDay.workTime().toMinute().value();
        }
        return HourAndMinute.from(new Minute(minute)).toString();
    }
}
