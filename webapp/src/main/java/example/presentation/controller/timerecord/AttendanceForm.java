package example.presentation.controller.timerecord;

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
    StartTimeForm startTimeForm;

    @Valid
    EndTimeForm endTimeForm;

    @Valid
    DaytimeBreakTime daytimeBreakTime;

    @Valid
    NightBreakTime nightBreakTime;

    @Valid
    TimeRecord timeRecord;

    boolean overlapWithPreviousWorkRange;
    boolean overlapWithNextWorkRange;

    public AttendanceForm() {
    }

    @AssertTrue(groups = Conversion.class)
    boolean isConverted() {
        StartDateTime startDateTime = startTimeForm.startDateTime(workDate);
        EndDateTime endDateTime = endTimeForm.endDateTime(startDateTime);

        ActualWorkDateTime actualWorkDateTime = new ActualWorkDateTime(
                new WorkRange(startDateTime, endDateTime),
                daytimeBreakTime,
                nightBreakTime);

        this.timeRecord = new TimeRecord(employeeNumber, actualWorkDateTime);

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
        this.startTimeForm = new StartTimeForm(new StartHour(startClockTime[0]), new StartMinute(startClockTime[1]));

        String[] endClockTime = timeRecord.actualWorkDateTime().workRange().endTimeText().split(":");
        this.endTimeForm = new EndTimeForm(new EndHour(endClockTime[0]), new EndMinute(endClockTime[1]));

        this.daytimeBreakTime = timeRecord.actualWorkDateTime().daytimeBreakTime();
        this.nightBreakTime = timeRecord.actualWorkDateTime().nightBreakTime();

        this.timeRecord = timeRecord;
    }
}
