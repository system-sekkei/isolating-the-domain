package example.domain.model.timerecord;

import example.domain.model.employee.EmployeeNumber;
import example.domain.type.date.Date;

/**
 * 勤務実績
 */
public class TimeRecord {

    EmployeeNumber employeeNumber;
    // TODO:これはactualWorkTimeから返すようにすること
    WorkDate workDate;
    ActualWorkDateTime actualWorkDateTime;

    @Deprecated
    TimeRecord() {
    }

    public TimeRecord(EmployeeNumber employeeNumber, WorkDate workDate, ActualWorkDateTime actualWorkDateTime) {
        this.employeeNumber = employeeNumber;
        this.workDate = workDate;
        this.actualWorkDateTime = actualWorkDateTime;
    }

    public WorkDate workDate() {
        return workDate;
    }

    public ActualWorkDateTime actualWorkTime() {
        return actualWorkDateTime;
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
