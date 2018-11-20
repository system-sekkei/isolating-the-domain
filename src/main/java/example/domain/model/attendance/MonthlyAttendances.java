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
    Attendances list;

    public MonthlyAttendances(YearMonth yearMonth, Attendances list) {
        this.list = list;
    }

    public Attendances list() {
        return list;
    }

    public HourAndMinute totalWorks() {
        return list.workTime();
    }

    @Deprecated
    public Break totalBreaks() {
        int breakMinute = 0;
        for (AttendanceOfDay attendanceOfDay : list.list()) {
            Minute minute = attendanceOfDay.breaks().normalizeValue();
            breakMinute += minute.value();
        }
        return new Break(new Minute(breakMinute));
    }
}
