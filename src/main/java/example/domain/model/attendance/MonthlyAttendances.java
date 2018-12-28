package example.domain.model.attendance;

import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.DateRange;

import java.util.List;

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

    public List<WorkDate> listWorkDates() {
        return month.days();
    }

    public Attendance at(WorkDate workDate) {
        return attendances.at(workDate);
    }

    public AttendanceStatus statusOf(WorkDate workDate) {
        return attendances.statusOf(workDate);
    }

    public TotalWorkTime totalWorkTime() {
        return attendances.summarize().totalWorkTime();
    }

    public WorkTimeSummary workTimeWithin(DateRange period) {
        return attendances.rangeOf(period).summarize();
    }

    public boolean notWorking() {
        return attendances.list().isEmpty();
    }

    public WorkDate firstWorkDate() {
        List<Attendance> list = attendances.list();
        return list.get(0).workDate();
    }
}