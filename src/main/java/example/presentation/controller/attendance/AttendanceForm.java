package example.presentation.controller.attendance;

import example.domain.model.attendance.*;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.time.ClockTime;
import example.domain.type.time.Minute;

import javax.validation.constraints.AssertTrue;
import java.time.DateTimeException;

public class AttendanceForm {

    WorkerNumber workerNumber;
    WorkDay workDay;

    String startHour;
    String startMinute;
    String endHour;
    String endMinute;

    String normalBreakTime;
    String midnightBreakTime;

    public AttendanceForm() {
        this.workDay = new WorkDay();
        this.startHour = "9";
        this.startMinute = "0";
        this.endHour = "17";
        this.endMinute = "30";
        this.normalBreakTime = "60";
        this.midnightBreakTime = "0";
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
        // TODO : 空値チェックはClockTimeモデルにさせるべき
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
