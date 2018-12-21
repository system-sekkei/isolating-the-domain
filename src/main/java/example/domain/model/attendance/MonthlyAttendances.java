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
    }

    public WorkMonth month() {
        return month;
    }

    // TODO:メソッド名にMonthlyとかついていた方がいいかも
    public Attendances attendances() {
        List<WorkDay> days = month.days();

        List<Attendance> monthlyAttendances = days.stream()
                .map(attendances::at)
                .collect(Collectors.toList());

        return new Attendances(monthlyAttendances);
    }

}