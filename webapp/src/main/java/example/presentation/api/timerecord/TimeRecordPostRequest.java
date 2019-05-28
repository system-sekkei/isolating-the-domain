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

/**
 * 勤務時間の登録リクエスト
 */
class TimeRecordPostRequest {
    @JsonProperty("employeeNumber")
    String employeeNumber;
    @JsonProperty("workDate")
    String workDate;

    @JsonProperty("startHour")
    String startHour;
    @JsonProperty("startMinute")
    String startMinute;
    @JsonProperty("endHour")
    String endHour;
    @JsonProperty("endMinute")
    String endMinute;

    @JsonProperty("daytimeBreakTime")
    String daytimeBreakTime;
    @JsonProperty("midnightBreakTime")
    String midnightBreakTime;

    static TimeRecordPostRequest prepare(EmployeeNumber employeeNumber, WorkDate workDate) {
        TimeRecordPostRequest request = new TimeRecordPostRequest();
        request.employeeNumber = employeeNumber.toString();
        request.workDate = workDate.toString();
        request.startHour = "9";
        request.startMinute = "0";
        request.endHour = "17";
        request.endMinute = "30";
        request.daytimeBreakTime = "60";
        request.midnightBreakTime = "0";
        return request;
    }

    static TimeRecordPostRequest apply(TimeRecord timeRecord) {
        TimeRecordPostRequest request = new TimeRecordPostRequest();
        request.employeeNumber = timeRecord.employeeNumber().toString();
        request.workDate = timeRecord.workDate().toString();
        request.startHour = timeRecord.actualWorkTime().timeRange().start().clockTime().hour().toString();
        request.startMinute = timeRecord.actualWorkTime().timeRange().start().clockTime().minute().toString();
        request.endHour = timeRecord.actualWorkTime().timeRange().end().clockTime().hour().toString();
        request.endMinute = timeRecord.actualWorkTime().timeRange().end().clockTime().minute().toString();
        request.daytimeBreakTime = timeRecord.actualWorkTime().daytimeBreakTime().toString();
        request.midnightBreakTime = timeRecord.actualWorkTime().midnightBreakTime().toString();
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

    @AssertTrue(message = "勤務日を入力してください")
    boolean isWorkDateComplete() {
        return !workDate.isEmpty();
    }

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

    @AssertTrue(message = "開始時刻を入力してください")
    boolean isStartTimeComplete() {
        if (startHour.isEmpty() || startMinute.isEmpty()) return false;
        return true;
    }

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

    @AssertTrue(message = "終了時刻を入力してください")
    boolean isEndTimeComplete() {
        if (endHour.isEmpty() || endMinute.isEmpty()) return false;
        return true;
    }

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
