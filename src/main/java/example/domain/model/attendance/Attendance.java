package example.domain.model.attendance;

import example.domain.model.timerecord.TimeRecord;
import example.domain.model.timerecord.WorkDate;
import example.domain.model.employee.EmployeeNumber;
import example.domain.type.time.QuarterHour;

import java.util.List;

/**
 * 勤怠
 */
public class Attendance {

    EmployeeNumber employeeNumber;
    WorkMonth month;
    TimeRecords timeRecords;

    public Attendance(EmployeeNumber employeeNumber, WorkMonth month, TimeRecords timeRecords) {
        this.employeeNumber = employeeNumber;
        this.month = month;
        this.timeRecords = timeRecords;
    }

    public WorkMonth month() {
        return month;
    }

    public List<WorkDate> listWorkDates() {
        return month.days();
    }

    public TimeRecord at(WorkDate workDate) {
        return timeRecords.at(workDate);
    }

    public AttendanceStatus statusOf(WorkDate workDate) {
        return AttendanceStatus.from(timeRecords.recordedAt(workDate));
    }

    public AttendDates attendDates() {
        return timeRecords.attendDates();
    }

    public TotalWorkTime totalWorkTime() {
        return new TotalWorkTime(timeRecords.list().stream()
                .map(timeRecord -> timeRecord.actualWorkTime().workTime().quarterHour())
                .reduce(QuarterHour::add)
                .orElseGet(QuarterHour::new));
    }

    public TimeRecords timeRecords() {
        return timeRecords;
    }
}