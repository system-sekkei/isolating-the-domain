package example.domain.model.attendance;

import example.domain.type.date.YearMonth;

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
}
