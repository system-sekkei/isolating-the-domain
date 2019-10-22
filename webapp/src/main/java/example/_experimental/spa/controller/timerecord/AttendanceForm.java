package example._experimental.spa.controller.timerecord;

import example.domain.model.employee.EmployeeNumber;
import example.domain.model.timerecord.*;
import example.domain.model.timerecord.breaktime.DaytimeBreakTime;
import example.domain.model.timerecord.breaktime.NightBreakTime;
import example.domain.type.time.ClockTime;
import example.domain.type.time.InputTime;
import example.domain.type.time.Minute;

import javax.validation.constraints.AssertTrue;
import java.time.DateTimeException;

class AttendanceForm {
    String employeeNumber;
    String workDate;
    String startHour;
    String startMinute;
    String endHour;
    String endMinute;

    String daytimeBreakTime;
    String nightBreakTime;

    static AttendanceForm of(EmployeeNumber employeeNumber, WorkDate workDate) {
        AttendanceForm request = new AttendanceForm();
        request.employeeNumber = employeeNumber.toString();
        request.workDate = workDate.toString();
        request.startHour = "9";
        request.startMinute = "0";
        request.endHour = "17";
        request.endMinute = "30";
        request.daytimeBreakTime = "60";
        request.nightBreakTime = "0";
        return request;
    }

    static AttendanceForm of(TimeRecord timeRecord) {
        AttendanceForm request = new AttendanceForm();
        request.employeeNumber = timeRecord.employeeNumber().toString();
        request.workDate = timeRecord.workDate().toString();

        String[] startClockTime = timeRecord.actualWorkDateTime().workRange().start().clockTime().toString().split(":");
        request.startHour = startClockTime[0];
        request.startMinute = startClockTime[1];
        String[] endClockTime = timeRecord.actualWorkDateTime().workRange().end().toString().split(":");
        request.endHour = endClockTime[0];
        request.endMinute = endClockTime[1];

        request.daytimeBreakTime = timeRecord.actualWorkDateTime().daytimeBreakTime().toString();
        request.nightBreakTime = timeRecord.actualWorkDateTime().nightBreakTime().toString();
        return request;
    }

    TimeRecord toTimeRecord() {
        EmployeeNumber employeeNumber = new EmployeeNumber(this.employeeNumber);
        WorkDate workDate = new WorkDate(this.workDate);
        InputTime startTime = new InputTime(Integer.valueOf(startHour), Integer.valueOf(startMinute));
        InputTime endTime = new InputTime(Integer.valueOf(endHour), Integer.valueOf(endMinute));

        Minute daytimeBreakMinute = new Minute(daytimeBreakTime);
        Minute nightBreakTime = new Minute(this.nightBreakTime);
        ActualWorkDateTime actualWorkDateTime = new ActualWorkDateTime(
                new WorkRange(StartDateTime.from(workDate, startTime), EndDateTime.from(workDate, endTime)),
                new DaytimeBreakTime(daytimeBreakMinute),
                new NightBreakTime(nightBreakTime));
        return new TimeRecord(employeeNumber, actualWorkDateTime);
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
    boolean isStartTimeValid() {
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
    boolean isEndTimeValid() {
        if (!isEndTimeComplete()) return true;

        try {
            workEndTime();
        } catch (NumberFormatException ex) {
            return false;
        }

        return true;
    }

    @AssertTrue(message = "終了時刻には開始時刻よりあとの時刻を入力してください")
    boolean isWorkTimeValid() {
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
    boolean isDaytimeBreakTimeValid() {
        if (daytimeBreakTime.isEmpty()) return true;

        try {
            DaytimeBreakTime daytimeBreakTime = new DaytimeBreakTime(new Minute(this.daytimeBreakTime));

            TimeRecord timeRecord = toTimeRecord();
            Minute daytimeBindingMinute = timeRecord.actualWorkDateTime().workRange().daytimeBindingTime().quarterHour().minute();
            if (daytimeBindingMinute.lessThan(daytimeBreakTime.minute())) {
                return false;
            }
        } catch (NumberFormatException | DateTimeException ex) {
            return false;
        }
        return true;
    }

    @AssertTrue(message = "休憩時間（深夜）が不正です")
    boolean isNightBreakTimeValid() {
        if (nightBreakTime.isEmpty()) return true;

        try {
            NightBreakTime nightBreakTime = new NightBreakTime(new Minute(this.nightBreakTime));

            TimeRecord timeRecord = toTimeRecord();
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