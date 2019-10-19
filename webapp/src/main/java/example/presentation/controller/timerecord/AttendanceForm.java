package example.presentation.controller.timerecord;

import example.domain.model.employee.EmployeeNumber;
import example.domain.model.timerecord.*;
import example.domain.model.timerecord.breaktime.DaytimeBreakTime;
import example.domain.model.timerecord.breaktime.NightBreakTime;
import example.domain.type.time.ClockTime;
import example.domain.type.time.Minute;

import javax.validation.constraints.AssertTrue;
import java.time.DateTimeException;
import java.time.LocalDate;

public class AttendanceForm {

    EmployeeNumber employeeNumber;
    String workDate;

    String startHour;
    String startMinute;
    String endHour;
    String endMinute;

    String daytimeBreakTime;
    String nightBreakTime;

    public AttendanceForm() {
        this.workDate = LocalDate.now().toString();
        this.startHour = "9";
        this.startMinute = "0";
        this.endHour = "17";
        this.endMinute = "30";
        this.daytimeBreakTime = "60";
        this.nightBreakTime = "0";
    }

    public TimeRecord toAttendance() {
        WorkDate workDate = new WorkDate(this.workDate);
        ClockTime startTime = new ClockTime(Integer.valueOf(startHour), Integer.valueOf(startMinute));

        StartDateTime startDateTime = new StartDateTime(workDate, new StartTime(startTime));
        EndDateTime endDateTime = new EndDateTime(workDate, Integer.valueOf(endHour), Integer.valueOf(endMinute));
        Minute minute = new Minute(daytimeBreakTime);
        Minute nightMinute = new Minute(nightBreakTime);
        ActualWorkDateTime actualWorkDateTime = new ActualWorkDateTime(
                new WorkRange(startDateTime, endDateTime),
                new DaytimeBreakTime(minute),
                new NightBreakTime(nightMinute));
        return new TimeRecord(employeeNumber, actualWorkDateTime);
    }

    public void apply(TimeRecord timeRecord) {
        this.employeeNumber = timeRecord.employeeNumber();
        this.workDate = timeRecord.workDate().toString();

        String[] startClockTime = timeRecord.actualWorkDateTime().workRange().start().clockTime().toString().split(":");
        this.startHour = startClockTime[0];
        this.startMinute = startClockTime[1];

        String[] endClockTime = timeRecord.actualWorkDateTime().workRange().end().toString().split(":");
        this.endHour = endClockTime[0];
        this.endMinute = endClockTime[1];

        this.daytimeBreakTime = timeRecord.actualWorkDateTime().daytimeBreakTime().toString();
        this.nightBreakTime = timeRecord.actualWorkDateTime().nightBreakTime().toString();
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

    private EndTime workEndTime() {
        int endHour = Integer.parseInt(this.endHour) % 24;
        int endMinute = Integer.parseInt(this.endMinute);
        ClockTime clockTime = new ClockTime(endHour, endMinute);
        return new EndTime(clockTime);
    }

    boolean workTimeValid;

    @AssertTrue(message = "終了時刻には開始時刻よりあとの時刻を入力してください")
    public boolean isWorkTimeValid() {
        if (!isStartTimeComplete()) return true;
        if (!isEndTimeComplete()) return true;
        if (!isStartTimeValid() || !isEndTimeValid()) return true;

        // FIXME 日跨ぎを判定して大小比較したいがとりあえず文字列比較で動作を満たしておく
        String startTime = String.format("%02d:%02d", Integer.parseInt(startHour), Integer.parseInt(startMinute));
        String endTime = String.format("%02d:%02d", Integer.parseInt(endHour), Integer.parseInt(endMinute));

        if (startTime.compareTo(endTime) > 0) return false;

        return true;
    }

    private StartTime workStartTime() {
        ClockTime clockTime = new ClockTime(Integer.valueOf(startHour), Integer.valueOf(this.startMinute));
        return new StartTime(clockTime);
    }

    boolean daytimeBreakTimeValid;

    @AssertTrue(message = "休憩時間が不正です")
    public boolean isDaytimeBreakTimeValid() {
        if (daytimeBreakTime.isEmpty()) return true;

        try {
            DaytimeBreakTime daytimeBreakTime = new DaytimeBreakTime(new Minute(this.daytimeBreakTime));

            TimeRecord timeRecord = toAttendance();
            Minute daytimeBindingMinute = timeRecord.actualWorkDateTime().workRange().daytimeBindingTime().quarterHour().minute();
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
        if (nightBreakTime.isEmpty()) return true;

        try {
            NightBreakTime nightBreakTime = new NightBreakTime(new Minute(this.nightBreakTime));

            TimeRecord timeRecord = toAttendance();
            Minute nightBindingMinute = timeRecord.actualWorkDateTime().workRange().nightBindingTime().quarterHour().minute();
            if (nightBindingMinute.lessThan(nightBreakTime.minute())) {
                return false;
            }
        } catch (NumberFormatException | DateTimeException ex) {
            return false;
        }
        return true;
    }
}
