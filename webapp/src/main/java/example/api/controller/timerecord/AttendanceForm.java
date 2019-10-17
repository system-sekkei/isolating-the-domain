package example.api.controller.timerecord;

import example.domain.model.employee.EmployeeNumber;
import example.domain.model.timerecord.*;
import example.domain.model.timerecord.breaktime.DaytimeBreakTime;
import example.domain.model.timerecord.breaktime.NightBreakTime;
import example.domain.type.time.ClockTime;
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
        request.startHour = timeRecord.actualWorkTime().workRange().start().clockTime().hour().toString();
        request.startMinute = timeRecord.actualWorkTime().workRange().start().clockTime().minute().toString();
        request.endHour = timeRecord.actualWorkTime().workRange().end().clockTime().hour().toString();
        request.endMinute = timeRecord.actualWorkTime().workRange().end().clockTime().minute().toString();
        request.daytimeBreakTime = timeRecord.actualWorkTime().daytimeBreakTime().toString();
        request.nightBreakTime = timeRecord.actualWorkTime().nightBreakTime().toString();
        return request;
    }

    TimeRecord toAttendance() {
        EmployeeNumber employeeNumber = new EmployeeNumber(this.employeeNumber);
        WorkDate workDate = new WorkDate(this.workDate);
        ClockTime startTime = new ClockTime(Integer.valueOf(startHour), Integer.valueOf(startMinute));
        ClockTime endTime = new ClockTime(Integer.valueOf(endHour), Integer.valueOf(endMinute));
        Minute daytimeBreakMinute = new Minute(daytimeBreakTime);
        Minute nightBreakTime = new Minute(this.nightBreakTime);
        ActualWorkDateTime actualWorkDateTime = new ActualWorkDateTime(
                new WorkRange(workDate, new StartTime(startTime), new EndTime(endTime)),
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

            TimeRecord timeRecord = toAttendance();
            Minute daytimeBindingMinute = timeRecord.actualWorkTime().workRange().daytimeBindingTime().quarterHour().minute();
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

            TimeRecord timeRecord = toAttendance();
            Minute nightBindingMinute = timeRecord.actualWorkTime().workRange().nightBindingTime().quarterHour().minute();
            if (nightBindingMinute.lessThan(nightBreakTime.minute())) {
                return false;
            }
        } catch (NumberFormatException | DateTimeException ex) {
            return false;
        }
        return true;
    }
}