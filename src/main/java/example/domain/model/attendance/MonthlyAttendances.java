package example.domain.model.attendance;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 月次勤怠
 */
public class MonthlyAttendances {

    WorkMonth month;
    Attendances attendances;

    public MonthlyAttendances(WorkMonth month, Attendances attendances) {
        this.month = month;
        this.attendances = attendances;
        monthly();
    }

    public WorkMonth month() {
        return month;
    }

    public Attendances attendances() {
        return attendances;
    }

    private void monthly() {
        List<WorkDay> days = month.days();

        List<Attendance> notWorkedDays = days.stream().filter(day -> attendances.notWorked(day))
                .map(Attendance::new)
                .collect(Collectors.toList());

        // attendancesの中身は変えるべきではないかも
        attendances.list.addAll(notWorkedDays);

        attendances = new Attendances(attendances.list.stream()
                .sorted((attendance1, attendance2) -> attendance1.workDay.isBefore(attendance2.workDay) ? -1 : 1)
                .collect(Collectors.toList()));

    }

    public Attendance attendanceOf(WorkDay day) {
        return attendances.atWorkDay(day);
    }

}
