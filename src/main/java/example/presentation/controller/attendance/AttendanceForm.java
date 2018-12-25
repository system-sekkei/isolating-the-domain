package example.presentation.controller.attendance;

import example.domain.model.attendance.*;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
import example.domain.type.time.ClockTime;
import example.domain.type.time.Minute;

import javax.validation.constraints.AssertTrue;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AttendanceForm {

    WorkerNumber workerNumber;
    String workDay;

    String startHour;
    String startMinute;
    String endHour;
    String endMinute;

    String normalBreakTime;
    String midnightBreakTime;

    public AttendanceForm() {
        this.workDay = LocalDate.now().toString();
        this.startHour = "9";
        this.startMinute = "0";
        this.endHour = "17";
        this.endMinute = "30";
        this.normalBreakTime = "60";
        this.midnightBreakTime = "0";
    }

    public WorkerAttendance toWorkerAttendance() {
        WorkDay workDay = new WorkDay(new Date(LocalDate.parse(this.workDay, DateTimeFormatter.ISO_DATE)));
        ClockTime startTime = new ClockTime(Integer.valueOf(startHour), Integer.valueOf(startMinute));
        ClockTime endTime = new ClockTime(Integer.valueOf(endHour), Integer.valueOf(endMinute));
        Minute minute = new Minute(normalBreakTime);
        Minute midnightMinute = new Minute(midnightBreakTime);
        Attendance attendance = new Attendance(
                workDay,
                new WorkStartTime(startTime),
                new WorkEndTime(endTime),
                new NormalBreakTime(minute),
                new MidnightBreakTime(midnightMinute)
        );
        return new WorkerAttendance(workerNumber, attendance);
    };

    public void apply(WorkerAttendance attendance) {
        this.workerNumber = attendance.workerNumber();
        this.workDay = attendance.attendance().workDay().toString();

        LocalTime start = LocalTime.parse(attendance.attendance().workTimeRange().start().toString());
        this.startHour = Integer.toString(start.getHour());
        this.startMinute = Integer.toString(start.getMinute());

        LocalTime end = LocalTime.parse(attendance.attendance().workTimeRange().end().toString());
        this.endHour = Integer.toString(end.getHour());
        this.endMinute = Integer.toString(end.getMinute());

        this.normalBreakTime = attendance.attendance().normalBreakTime().toString();
        this.midnightBreakTime = attendance.attendance().midnightBreakTime().toString();
    }

    boolean workDayComplete;

    @AssertTrue(message = "勤務日を入力してください")
    boolean isWorkDayComplete() {
        return !workDay.isEmpty();
    }

    boolean workDayValid;

    @AssertTrue(message = "勤務日が不正です")
    boolean isWorkDayValid() {
        if (!isWorkDayComplete()) return true;
        try {
            new WorkDay(new Date(LocalDate.parse(workDay, DateTimeFormatter.ISO_DATE)));
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
        // TODO : 24時を超える終業時刻を入力できるようにする
        // TODO : 上記に対応したあとで開始時刻 < 終了時刻のチェックもする
        if (!isEndTimeComplete()) return true;

        try {
            workEndTime();
        } catch (NumberFormatException | DateTimeException ex) {
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

    boolean normalBreakTimeValid;

    @AssertTrue(message = "休憩時間が不正です")
    public boolean isNormalBreakTimeValid() {
        if (normalBreakTime.isEmpty()) return true;

        try {
            new NormalBreakTime(new Minute(normalBreakTime));
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
