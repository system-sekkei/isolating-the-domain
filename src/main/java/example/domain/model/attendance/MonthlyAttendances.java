package example.domain.model.attendance;

import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.DateRange;
import example.domain.type.time.HourAndMinute;

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

    public List<WorkDay> listWorkDays() {
        return month.days();
    }

    public WorkerAttendance at(WorkDay workDay) {
        return new WorkerAttendance(workerNumber, attendances.at(workDay));
    }

    public AttendanceStatus statusOf(WorkDay workDay) {
        return attendances.statusOf(workDay);
    }

    public HourAndMinute totalWorkTime() {
        return attendances.summarize().totalWorkTime();
    }

    public WorkTime workTimeWithin(DateRange period) {
        return attendances.rangeOf(period).summarize();
    }

    public boolean notWorking() {
        return attendances.list().isEmpty();
    }

    public WorkDay firstWorkDay() {
        List<Attendance> list = attendances.list();
        return list.get(0).workDay();
    }
}