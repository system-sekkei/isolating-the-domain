package example.presentation.controller.timerecord;

import example.domain.model.legislation.DaysOffStatus;
import example.domain.validation.Conversion;
import example.domain.model.employee.EmployeeNumber;
import example.domain.model.timerecord.evaluation.*;
import example.domain.model.timerecord.timefact.EndDateTime;
import example.domain.model.timerecord.timefact.StartDateTime;
import example.domain.model.timerecord.timefact.WorkRange;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;

public class AttendanceForm {
    @Valid
    EmployeeNumber employeeNumber;

    @Valid
    WorkDate workDate;

    @Valid
    StartTime startTime;

    @Valid
    EndTime endTime;

    @Valid
    DaytimeBreakTime daytimeBreakTime;

    @Valid
    NightBreakTime nightBreakTime;

    boolean isDaysOff;

    @Valid
    TimeRecord timeRecord;

    public AttendanceForm() {
    }

    @AssertTrue(groups = Conversion.class)
    boolean isConverted() {
        StartDateTime startDateTime = startTime.startDateTime(workDate);
        EndDateTime endDateTime = endTime.endDateTime(startDateTime);

        ActualWorkDateTime actualWorkDateTime = new ActualWorkDateTime(
                new WorkRange(startDateTime, endDateTime),
                daytimeBreakTime,
                nightBreakTime);

        if (isDaysOff) {
            this.timeRecord = new TimeRecord(employeeNumber, actualWorkDateTime, DaysOffStatus.休日);
        } else {
            this.timeRecord = new TimeRecord(employeeNumber, actualWorkDateTime, DaysOffStatus.労働日);
        }

        // 形式チェックが通ればオブジェクトは必ず生成できるはずなので常にtrue。
        return true;
    }

    public TimeRecord toTimeRecord() {
        return timeRecord;
    }

    public void apply(TimeRecord timeRecord) {
        this.employeeNumber = timeRecord.employeeNumber();
        this.workDate = timeRecord.workDate();

        String[] startClockTime = timeRecord.actualWorkDateTime().workRange().start().toString().split(" ")[1].split(":");
        this.startTime = new StartTime(new StartHour(startClockTime[0]), new StartMinute(startClockTime[1]));

        String[] endClockTime = timeRecord.actualWorkDateTime().workRange().endTimeText().split(":");
        this.endTime = new EndTime(new EndHour(endClockTime[0]), new EndMinute(endClockTime[1]));

        this.daytimeBreakTime = timeRecord.actualWorkDateTime().daytimeBreakTime();
        this.nightBreakTime = timeRecord.actualWorkDateTime().nightBreakTime();

        this.isDaysOff = timeRecord.daysOffStatus() == DaysOffStatus.休日;

        this.timeRecord = timeRecord;
    }
}
