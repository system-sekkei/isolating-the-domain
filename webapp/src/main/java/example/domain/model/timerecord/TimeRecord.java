package example.domain.model.timerecord;

import example.domain.model.employee.EmployeeNumber;
import example.domain.type.date.Date;

/**
 * 勤務実績
 */
public class TimeRecord {

    EmployeeNumber employeeNumber;
    ActualWorkDateTime actualWorkDateTime;

    @Deprecated
    TimeRecord() {
    }

    // TODO: あとで workDate を消す
    public TimeRecord(EmployeeNumber employeeNumber, WorkDate workDate, ActualWorkDateTime actualWorkDateTime) {
        this.employeeNumber = employeeNumber;
        this.actualWorkDateTime = actualWorkDateTime;
    }

    public WorkDate workDate() {
        return actualWorkDateTime.workDate();
    }

    public ActualWorkDateTime actualWorkTime() {
        return actualWorkDateTime;
    }

    public EmployeeNumber employeeNumber() {
        return employeeNumber;
    }

    public boolean isWorkedAt(WorkDate other) {
        return this.actualWorkDateTime.workDate().hasSameValue(other);
    }

    public Date date() {
        return actualWorkDateTime.workDate().toDate();
    }
}
