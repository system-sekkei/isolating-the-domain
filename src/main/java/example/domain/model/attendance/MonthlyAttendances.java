package example.domain.model.attendance;

import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.DateRange;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 月次勤怠
 */
public class MonthlyAttendances {

    WorkerNumber workerNumber;
    WorkMonth month;
    Attendances attendances;

    public MonthlyAttendances(WorkerNumber workerNumber, WorkMonth month, Attendances attendances) {
        this.workerNumber = workerNumber;
        this.month = month;
        this.attendances = attendances;
    }

    public WorkMonth month() {
        return month;
    }

    // TODO:メソッド名にMonthlyとかついていた方がいいかも
    public Attendances attendances() {
        List<WorkDay> days = listWorkDays();

        List<Attendance> monthlyAttendances = days.stream()
                .map(attendances::at)
                .collect(Collectors.toList());

        return new Attendances(monthlyAttendances);
    }

    public List<WorkDay> listWorkDays() {
        return month.days();
    }

    public Attendances attendancesOf(DateRange range) {
        return attendances.rangeOf(range);
    }

    public WorkerAttendance at(WorkDay workDay) {
        return new WorkerAttendance(workerNumber, attendances.at(workDay));
    }

    public AttendanceStatus statusOf(WorkDay workDay) {
        return attendances.statusOf(workDay);
    }
}