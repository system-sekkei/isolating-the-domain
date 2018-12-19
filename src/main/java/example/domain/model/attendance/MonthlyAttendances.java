package example.domain.model.attendance;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 月次勤怠
 */
public class MonthlyAttendances {

    WorkMonth month;
    Attendances attendances;

    public MonthlyAttendances(WorkMonth month, Attendances attendances) {
        this.month = month;
        this.attendances = toMonthly(attendances);
    }

    public WorkMonth month() {
        return month;
    }

    public Attendances attendances() {
        return attendances;
    }

    private Attendances toMonthly(Attendances attendances){
        List<WorkDay> days = month.days();

        List<Attendance> notWorkedDays = days.stream()
                .filter(attendances::notWorked)
                .map(Attendance::new)
                .collect(Collectors.toList());

        // Attendancesのしごとかなあ・・・
        return new Attendances(Stream.concat(attendances.list.stream(), notWorkedDays.stream())
                .sorted(Attendance::compareTo)
                .collect(Collectors.toList()));

    }

}
