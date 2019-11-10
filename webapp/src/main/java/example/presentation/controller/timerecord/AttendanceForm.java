package example.presentation.controller.timerecord;

import example.domain.model.employee.EmployeeNumber;
import example.domain.model.timerecord.evaluation.*;
import example.domain.model.timerecord.timefact.EndDateTime;
import example.domain.model.timerecord.timefact.StartDateTime;
import example.domain.model.timerecord.timefact.WorkRange;
import example.domain.type.date.Date;
import example.domain.type.datetime.DateTime;
import example.domain.type.time.ClockTime;
import example.domain.type.time.Minute;

import javax.validation.constraints.AssertTrue;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceForm {

    EmployeeNumber employeeNumber;
    String workDate = "";

    String startHour = "";
    String startMinute = "";
    String endHour = "";
    String endMinute = "";

    DaytimeBreakTime daytimeBreakTime;
    NightBreakTime nightBreakTime;

    public AttendanceForm() {
    }

    public TimeRecord toTimeRecord() {
        ActualWorkDateTime actualWorkDateTime = toActualWorkDateTime();
        return new TimeRecord(employeeNumber, actualWorkDateTime);
    }

    private ActualWorkDateTime toActualWorkDateTime() {
        Date workDate = new Date(this.workDate);
        LocalDateTime startLocalDateTime = LocalDateTime.of(workDate.value(), LocalTime.of(Integer.valueOf(startHour), Integer.valueOf(startMinute)));
        StartDateTime startDateTime = new StartDateTime(new DateTime(startLocalDateTime));
        EndDateTime endDateTime = (new InputEndTime(Integer.valueOf(endHour), Integer.valueOf(endMinute))).endDateTime(workDate);

        return toActualWorkDateTime(startDateTime, endDateTime, daytimeBreakTime, nightBreakTime);
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
        EndDateTime endDateTime = InputEndTime.from(endTime).endDateTime(new Date(startDate));
        return toActualWorkDateTime(startDateTime, endDateTime, new DaytimeBreakTime(daytimeBreak), new NightBreakTime(nightBreak));
    }

    public void apply(TimeRecord timeRecord) {
        this.employeeNumber = timeRecord.employeeNumber();
        this.workDate = timeRecord.workDate().toString();

        String[] startClockTime = timeRecord.actualWorkDateTime().workRange().start().toString().split(" ")[1].split(":");
        this.startHour = startClockTime[0];
        this.startMinute = startClockTime[1];

        String[] endClockTime = timeRecord.actualWorkDateTime().workRange().endTimeText().split(":");
        this.endHour = endClockTime[0];
        this.endMinute = endClockTime[1];

        this.daytimeBreakTime = timeRecord.actualWorkDateTime().daytimeBreakTime();
        this.nightBreakTime = timeRecord.actualWorkDateTime().nightBreakTime();
    }

    boolean workDateComplete;

    @AssertTrue(message = "勤務日を入力してください")
    boolean isWorkDateComplete() {
        return !workDate.isEmpty();
    }

    boolean workDateValid;

    @AssertTrue(message = "勤務日が不正です")
    boolean isWorkDateValid() {
        if (!isWorkDateComplete()) return true;
        try {
            new WorkDate(this.workDate);
        } catch (DateTimeException ex) {
            return false;
        }
        return true;
    }

    boolean startTimeComplete;

    @AssertTrue(message = "開始時刻を入力してください")
    boolean isStartTimeComplete() {
        if (startHour.isEmpty() || startMinute.isEmpty()) return false;
        return true;
    }

    boolean startTimeValid;

    @AssertTrue(message = "開始時刻が不正です")
    public boolean isStartTimeValid() {
        if (!isStartTimeComplete()) return true;

        try {
            workStartTime();
        } catch (NumberFormatException | DateTimeException ex) {
            return false;
        }

        return true;
    }

    boolean endTimeComplete;

    @AssertTrue(message = "終了時刻を入力してください")
    boolean isEndTimeComplete() {
        if (endHour.isEmpty() || endMinute.isEmpty()) return false;
        return true;
    }

    boolean endTimeValid;

    @AssertTrue(message = "終了時刻が不正です")
    public boolean isEndTimeValid() {
        if (!isEndTimeComplete()) return true;

        try {
            workEndTime();
        } catch (NumberFormatException ex) {
            return false;
        }

        return true;
    }

    private ClockTime workEndTime() {
        int endHour = Integer.parseInt(this.endHour) % 24;
        int endMinute = Integer.parseInt(this.endMinute);
        return new ClockTime(endHour, endMinute);
    }

    boolean workTimeValid;

    @AssertTrue(message = "終了時刻には開始時刻よりあとの時刻を入力してください")
    public boolean isWorkTimeValid() {
        if (unnecessaryCalculate()) return true;

        StartDateTime startDateTime = workStartDateTime();
        EndDateTime endDateTime = workEndDateTime();
        if (endDateTime.isAfter(startDateTime)) return true;

        return false;
    }

    private boolean unnecessaryCalculate() {
        return !isStartTimeComplete() || !isStartTimeValid()
                || !isEndTimeComplete() || !isEndTimeValid()
                || !isWorkDateComplete() || !isWorkDateValid();
    }

    private ClockTime workStartTime() {
        return new ClockTime(Integer.valueOf(startHour), Integer.valueOf(this.startMinute));
    }

    private StartDateTime workStartDateTime() {
        return new StartDateTime(DateTime.parse(workDate, startHour, startMinute));
    }

    private EndDateTime workEndDateTime() {
        InputEndTime time = new InputEndTime(Integer.valueOf(endHour), Integer.valueOf(endMinute));
        return time.endDateTime(new Date(workDate));
    }

    boolean daytimeBreakTimeValid;

    @AssertTrue(message = "休憩時間が不正です")
    public boolean isDaytimeBreakTimeValid() {
        if (daytimeBreakTime == null) return false;
        if (unnecessaryCalculate() || !isWorkTimeValid()) return true;

        try {
            Minute daytimeBindingMinute = toActualWorkDateTime().daytimeBindingTime().quarterHour().minute();
            if (daytimeBindingMinute.lessThan(daytimeBreakTime.minute())) {
                return false;
            }
        } catch (NumberFormatException | DateTimeException ex) {
            return false;
        }
        return true;
    }

    boolean nightBreakTimeValid;

    @AssertTrue(message = "休憩時間（深夜）が不正です")
    public boolean isNightBreakTimeValid() {
        if (nightBreakTime == null) return false;
        if (unnecessaryCalculate() || !isWorkTimeValid()) return true;

        try {
            Minute nightBindingMinute = toActualWorkDateTime().nightBindingTime().quarterHour().minute();
            if (nightBindingMinute.lessThan(nightBreakTime.minute())) {
                return false;
            }
        } catch (NumberFormatException | DateTimeException ex) {
            return false;
        }
        return true;
    }
}
