package example.presentation.controller.timerecord;

import example.domain.Conversion;
import example.domain.FormatCheck;
import example.domain.FormatCheck2;
import example.domain.model.employee.EmployeeNumber;
import example.domain.model.timerecord.evaluation.*;
import example.domain.model.timerecord.timefact.EndDateTime;
import example.domain.model.timerecord.timefact.StartDateTime;
import example.domain.model.timerecord.timefact.WorkRange;
import example.domain.type.datetime.DateTime;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class AttendanceForm {
    @Valid
    EmployeeNumber employeeNumber;

    @Valid
    WorkDate workDate;

    @NotBlank(message = "開始時刻を入力してください", groups = FormatCheck.class)
    @Pattern(regexp = "^\\d{1,2}$", message = "開始時刻が不正です", groups = FormatCheck2.class)
    String startHour;

    @NotBlank(message = "開始時刻を入力してください", groups = FormatCheck.class)
    @Pattern(regexp = "^\\d{1,2}$", message = "開始時刻が不正です", groups = FormatCheck2.class)
    String startMinute;

    @NotBlank(message = "終了時刻を入力してください", groups = FormatCheck.class)
    @Pattern(regexp = "^\\d{1,2}$", message = "終了時刻が不正です", groups = FormatCheck2.class)
    String endHour;

    @NotBlank(message = "終了時刻を入力してください", groups = FormatCheck.class)
    @Pattern(regexp = "^\\d{1,2}$", message = "終了時刻が不正です", groups = FormatCheck2.class)
    String endMinute;

    @Valid
    DaytimeBreakTime daytimeBreakTime;

    @Valid
    NightBreakTime nightBreakTime;

    @Valid
    TimeRecord timeRecord; // バリデーションをうごかすための存在

    boolean overlapWithPreviousWorkRange;
    boolean overlapWithNextWorkRange;

    public AttendanceForm() {
    }

    @AssertTrue(groups = Conversion.class)
    boolean isConverted() {
        StartDateTime startDateTime = new StartDateTime(DateTime.parse(workDate.toString(), startHour, startMinute));
        InputEndTime inputEndTime = new InputEndTime(Integer.parseInt(endHour), Integer.parseInt(endMinute));
        EndDateTime endDateTime = inputEndTime.endDateTime(startDateTime);

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

    private ActualWorkDateTime toActualWorkDateTime() {
        return timeRecord.actualWorkDateTime();
    }

    private static ActualWorkDateTime toActualWorkDateTime(StartDateTime startDateTime, EndDateTime endDateTime, DaytimeBreakTime daytimeBreakTime, NightBreakTime nightBreakTime) {
        return new ActualWorkDateTime(
                new WorkRange(startDateTime, endDateTime),
                daytimeBreakTime,
                nightBreakTime);
    }

    // テストへの流出がキツイので一旦ここに集める。最終domainに持っていきたい。
    @Deprecated
    public static ActualWorkDateTime toActualWorkDateTime(String startDate, String startTime, String endTime, String daytimeBreak, String nightBreak) {
        StartDateTime startDateTime = new StartDateTime(DateTime.parse(startDate, startTime));
        EndDateTime endDateTime = InputEndTime.from(endTime).endDateTime(startDateTime);
        return toActualWorkDateTime(startDateTime, endDateTime, DaytimeBreakTime.from(daytimeBreak), NightBreakTime.from(nightBreak));
    }

    public void apply(TimeRecord timeRecord) {
        this.employeeNumber = timeRecord.employeeNumber();
        this.workDate = timeRecord.workDate();

        String[] startClockTime = timeRecord.actualWorkDateTime().workRange().start().toString().split(" ")[1].split(":");
        this.startHour = startClockTime[0];
        this.startMinute = startClockTime[1];

        String[] endClockTime = timeRecord.actualWorkDateTime().workRange().endTimeText().split(":");
        this.endHour = endClockTime[0];
        this.endMinute = endClockTime[1];

        this.daytimeBreakTime = timeRecord.actualWorkDateTime().daytimeBreakTime();
        this.nightBreakTime = timeRecord.actualWorkDateTime().nightBreakTime();

        this.timeRecord = timeRecord;
    }
}
