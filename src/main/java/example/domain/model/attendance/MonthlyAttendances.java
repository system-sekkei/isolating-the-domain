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
    List<AttendanceOfDay> list;

    public MonthlyAttendances(YearMonth yearMonth, List<AttendanceOfDay> list) {
        this.yearMonth = yearMonth;
        this.list = list;
    }

    public List<AttendanceOfDay> list() {
        return list;
    }

    public HourAndMinute totalWorks() {
        int minute = 0;
        for (AttendanceOfDay attendanceOfDay : list) {
            minute += attendanceOfDay.workTime().toMinute().value();
        }
        return HourAndMinute.from(new Minute(minute));
    }

    public AttendanceOfDay get(Date date) {
        return list.stream().filter(
                w -> w.date().value().equals(date.value())).findFirst().orElseThrow(() -> new RuntimeException());
    }

    @Deprecated
    public Break totalBreaks() {
        int breakMinute = 0;
        for (AttendanceOfDay attendanceOfDay : list) {
            Minute minute = attendanceOfDay.breaks().normalizeValue();
            breakMinute += minute.value();
        }
        return new Break(new Minute(breakMinute));
    }
}
