package example.domain.model.attendance;

import example.domain.type.date.Date;
import example.domain.type.date.YearMonth;
import example.domain.type.time.HourAndMinute;
import example.domain.type.time.Minute;

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

    public List<AttendanceOfDay> list() {
        return attendances.list();
    }

    public HourAndMinute totalWorks() {
        return attendances.totalWorkTime();
    }

    @Deprecated
    public NormalBreakTime totalBreaks() {
        int breakMinute = 0;
        for (AttendanceOfDay attendanceOfDay : attendances.list()) {
            Minute minute = attendanceOfDay.normalBreakTime().value.quarterHourRoundUp();
            breakMinute += minute.value();
        }
        return new NormalBreakTime(new Minute(breakMinute));
    }

    public AttendanceOfDay attendanceOf(Date day) {
        return attendances.get(day);
    }
}
