package example.presentation.controller.attendance;

import example.domain.model.attendance.Attendance;
import example.domain.model.attendance.WorkDate;
import example.domain.model.attendance.worktimerecord.*;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.time.ClockTime;
import example.domain.type.time.Minute;

import javax.validation.constraints.AssertTrue;
import java.time.DateTimeException;
import java.time.LocalDate;

public class AttendanceForm {

    WorkerNumber workerNumber;
    String workDate;

    String startHour;
    String startMinute;
    String endHour;
    String endMinute;

    String daytimeBreakTime;
    String midnightBreakTime;

    public AttendanceForm() {
        this.workDate = LocalDate.now().toString();
        this.startHour = "9";
        this.startMinute = "0";
        this.endHour = "17";
        this.endMinute = "30";
        this.daytimeBreakTime = "60";
        this.midnightBreakTime = "0";
    }

    public Attendance toAttendance() {
        WorkDate workDate = new WorkDate(this.workDate);
        ClockTime startTime = new ClockTime(Integer.valueOf(startHour), Integer.valueOf(startMinute));
        ClockTime endTime = new ClockTime(Integer.valueOf(endHour), Integer.valueOf(endMinute));
        Minute minute = new Minute(daytimeBreakTime);
        Minute midnightMinute = new Minute(midnightBreakTime);
        WorkTimeRecord workTimeRecord = new WorkTimeRecord(
                new WorkTimeRange(new WorkStartTime(startTime), new WorkEndTime(endTime)),
                new DaytimeBreakTime(minute),
                new MidnightBreakTime(midnightMinute));
        return new Attendance(workerNumber, workDate, workTimeRecord);
    }

    public void apply(Attendance attendance) {
        this.workerNumber = attendance.workerNumber();
        this.workDate = attendance.workDate().toString();

        this.startHour = attendance.workTimeRecord().workTimeRange().start().clockTime().hour().toString();
        this.startMinute = attendance.workTimeRecord().workTimeRange().start().clockTime().minute().toString();

        this.endHour = attendance.workTimeRecord().workTimeRange().end().clockTime().hour().toString();
        this.endMinute = attendance.workTimeRecord().workTimeRange().end().clockTime().minute().toString();

        this.daytimeBreakTime = attendance.workTimeRecord().daytimeBreakTime().toString();
        this.midnightBreakTime = attendance.workTimeRecord().midnightBreakTime().toString();
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
        } catch (NumberFormatException ex) {
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

    boolean workTimeValid;

    @AssertTrue(message = "終了時刻には開始時刻よりあとの時刻を入力してください")
    public boolean isWorkTimeValid() {
        if (!isStartTimeComplete()) return true;
        if (!isEndTimeComplete()) return true;
        if (!isStartTimeValid() || !isEndTimeValid()) return true;

        WorkStartTime workStartTime = workStartTime();
        WorkEndTime workEndTime = workEndTime();
        if (workStartTime.isAfter(workEndTime)) return false;

        return true;
    }

    private WorkStartTime workStartTime() {
        ClockTime clockTime = new ClockTime(Integer.valueOf(startHour), Integer.valueOf(this.startMinute));
        return new WorkStartTime(clockTime);
    }

    private WorkEndTime workEndTime() {
        ClockTime clockTime = new ClockTime(Integer.valueOf(endHour), Integer.valueOf(endMinute));
        return new WorkEndTime(clockTime);
    }

    boolean daytimeBreakTimeValid;

    @AssertTrue(message = "休憩時間が不正です")
    public boolean isDaytimeBreakTimeValid() {
        if (daytimeBreakTime.isEmpty()) return true;

        try {
            new DaytimeBreakTime(new Minute(daytimeBreakTime));
        } catch (NumberFormatException | DateTimeException ex) {
            return false;
        }
        return true;
    }

    boolean midnightBreakTimeValid;

    @AssertTrue(message = "休憩時間（深夜）が不正です")
    public boolean isMidnightBreakTimeValid() {
        if (midnightBreakTime.isEmpty()) return true;

        try {
            new MidnightBreakTime(new Minute(midnightBreakTime));
        } catch (NumberFormatException | DateTimeException ex) {
            return false;
        }
        return true;
    }
}
