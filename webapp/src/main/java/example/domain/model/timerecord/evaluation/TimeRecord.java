package example.domain.model.timerecord.evaluation;

import example.domain.model.employee.EmployeeNumber;
import example.domain.model.legislation.DaysOffStatus;
import example.domain.type.time.QuarterHour;

import javax.validation.Valid;
import java.util.Optional;

/**
 * 勤務実績
 */
public class TimeRecord {

    EmployeeNumber employeeNumber;

    @Valid
    ActualWorkDateTime actualWorkDateTime;

    // FIXME: 休日はシフトを元に判断するように変更する。
    DaysOffStatus daysOffStatus;

    @Deprecated
    TimeRecord() {
    }

    public TimeRecord(EmployeeNumber employeeNumber, ActualWorkDateTime actualWorkDateTime, DaysOffStatus daysOffStatus) {
        this.employeeNumber = employeeNumber;
        this.actualWorkDateTime = actualWorkDateTime;
        this.daysOffStatus = daysOffStatus;
    }

    public TimeRecord(EmployeeNumber employeeNumber, ActualWorkDateTime actualWorkDateTime) {
        this(employeeNumber, actualWorkDateTime, DaysOffStatus.労働日);
    }

    public WorkDate workDate() {
        return actualWorkDateTime.workDate();
    }

    public ActualWorkDateTime actualWorkDateTime() {
        return actualWorkDateTime;
    }

    public EmployeeNumber employeeNumber() {
        return employeeNumber;
    }

    public boolean isWorkedAt(WorkDate other) {
        return this.actualWorkDateTime.workDate().hasSameValue(other);
    }

    public boolean isOverlap(TimeRecord other) {
        return this.actualWorkDateTime.workRange.isOverlap(other.actualWorkDateTime.workRange);
    }

    public DaysOffStatus daysOffStatus() {
        return daysOffStatus;
    }

    public OverLegalHoursWorkTime overLegalHoursWorkTime(TimeRecords timeRecords) {
        return OverLegalHoursWorkTime.daily(actualWorkDateTime, timeRecords);
    }

    public LegalDaysOffWorkTime legalDaysOffWorkTime(TimeRecords timeRecords) {
        TimeRecords weeklyRecords = timeRecords.weeklyRecords(workDate());
        Optional<TimeRecord> lastDayOff = weeklyRecords.lastDayOff();

        if (lastDayOff.isPresent() && lastDayOff.get().workDate().hasSameValue(workDate())) {
            return new LegalDaysOffWorkTime(actualWorkDateTime.workTime().value);
        }

        return new LegalDaysOffWorkTime(new QuarterHour());
    }
}
