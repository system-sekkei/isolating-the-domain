package example.domain.model.timerecord;

import example.domain.model.employee.EmployeeNumber;
import example.domain.type.date.Date;

/**
 * 勤務実績
 */
public class TimeRecord {

    EmployeeNumber employeeNumber;
    WorkDate workDate;
    ActualWorkTime actualWorkTime;

    @Deprecated
    TimeRecord() {
    }

    public TimeRecord(EmployeeNumber employeeNumber, WorkDate workDate, ActualWorkTime actualWorkTime) {
        this.employeeNumber = employeeNumber;
        this.workDate = workDate;
        this.actualWorkTime = actualWorkTime;
    }

    public WorkDate workDate() {
        return workDate;
    }

    public ActualWorkTime actualWorkTime() {
        return actualWorkTime;
    }

    public EmployeeNumber employeeNumber() {
        return employeeNumber;
    }

    public boolean isWorkedAt(WorkDate other) {
        return this.workDate.hasSameValue(other);
    }

    public Date date() {
        return workDate.toDate();
    }
}
