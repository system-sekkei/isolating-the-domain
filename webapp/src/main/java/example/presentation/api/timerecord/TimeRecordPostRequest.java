package example.presentation.api.timerecord;

import com.fasterxml.jackson.annotation.JsonProperty;
import example.domain.model.employee.EmployeeNumber;
import example.domain.model.timerecord.*;
import example.domain.model.timerecord.breaktime.DaytimeBreakTime;
import example.domain.model.timerecord.breaktime.MidnightBreakTime;
import example.domain.type.time.ClockTime;
import example.domain.type.time.Minute;

import javax.validation.constraints.AssertTrue;
import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * 勤務時間の登録リクエスト
 */
class TimeRecordPostRequest {
    @JsonProperty("employeeNumber")
    String employeeNumber;
    @JsonProperty("勤務日")
    String workDate;

    @JsonProperty("開始時刻_時")
    String startHour;
    @JsonProperty("開始時刻_分")
    String startMinute;
    @JsonProperty("終了時刻_時")
    String endHour;
    @JsonProperty("終了時刻_分")
    String endMinute;

    @JsonProperty("休憩時間")
    String daytimeBreakTime;
    @JsonProperty("休憩時間_深夜")
    String midnightBreakTime;

    static TimeRecordPostRequest prepare() {
        TimeRecordPostRequest request = new TimeRecordPostRequest();
        request.workDate = LocalDate.now().toString();
        request.startHour = "9";
        request.startMinute = "0";
        request.endHour = "17";
        request.endMinute = "30";
        request.daytimeBreakTime = "60";
        request.midnightBreakTime = "0";
        return request;
    }

    TimeRecord toAttendance() {
        EmployeeNumber employeeNumber = new EmployeeNumber(this.employeeNumber);
        WorkDate workDate = new WorkDate(this.workDate);
        ClockTime startTime = new ClockTime(Integer.valueOf(startHour), Integer.valueOf(startMinute));
        ClockTime endTime = new ClockTime(Integer.valueOf(endHour), Integer.valueOf(endMinute));
        Minute minute = new Minute(daytimeBreakTime);
        Minute midnightMinute = new Minute(midnightBreakTime);
        ActualWorkTime actualWorkTime = new ActualWorkTime(
                new TimeRange(new StartTime(startTime), new EndTime(endTime)),
                new DaytimeBreakTime(minute),
                new MidnightBreakTime(midnightMinute));
        return new TimeRecord(employeeNumber, workDate, actualWorkTime);
    }

    void apply(TimeRecord timeRecord) {
        this.employeeNumber = timeRecord.employeeNumber().toString();
        this.workDate = timeRecord.workDate().toString();

        this.startHour = timeRecord.actualWorkTime().timeRange().start().clockTime().hour().toString();
        this.startMinute = timeRecord.actualWorkTime().timeRange().start().clockTime().minute().toString();

        this.endHour = timeRecord.actualWorkTime().timeRange().end().clockTime().hour().toString();
        this.endMinute = timeRecord.actualWorkTime().timeRange().end().clockTime().minute().toString();

        this.daytimeBreakTime = timeRecord.actualWorkTime().daytimeBreakTime().toString();
        this.midnightBreakTime = timeRecord.actualWorkTime().midnightBreakTime().toString();
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

        StartTime startTime = workStartTime();
        EndTime endTime = workEndTime();
        if (startTime.isAfter(endTime)) return false;

        return true;
    }

    private StartTime workStartTime() {
        ClockTime clockTime = new ClockTime(Integer.valueOf(startHour), Integer.valueOf(this.startMinute));
        return new StartTime(clockTime);
    }

    private EndTime workEndTime() {
        ClockTime clockTime = new ClockTime(Integer.valueOf(endHour), Integer.valueOf(endMinute));
        return new EndTime(clockTime);
    }

    boolean daytimeBreakTimeValid;

    @AssertTrue(message = "休憩時間が不正です")
    public boolean isDaytimeBreakTimeValid() {
        if (daytimeBreakTime.isEmpty()) return true;

        try {
            DaytimeBreakTime daytimeBreakTime = new DaytimeBreakTime(new Minute(this.daytimeBreakTime));

            TimeRecord timeRecord = toAttendance();
            Minute daytimeBindingMinute = timeRecord.actualWorkTime().timeRange().daytimeBindingTime().quarterHour().minute();
            if (daytimeBindingMinute.lessThan(daytimeBreakTime.minute())) {
                return false;
            }
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
            MidnightBreakTime midnightBreakTime = new MidnightBreakTime(new Minute(this.midnightBreakTime));

            TimeRecord timeRecord = toAttendance();
            Minute midnightBindingMinute = timeRecord.actualWorkTime().timeRange().midnightBindingTime().quarterHour().minute();
            if (midnightBindingMinute.lessThan(midnightBreakTime.minute())) {
                return false;
            }
        } catch (NumberFormatException | DateTimeException ex) {
            return false;
        }
        return true;
    }
}