package example._experimental.spa.controller.timerecord;

import example.domain.model.employee.EmployeeNumber;
import example.domain.model.timerecord.evaluation.*;
import example.domain.model.timerecord.timefact.*;
import example.domain.type.date.Date;
import example.domain.type.datetime.DateTime;
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

        String[] startClockTime = timeRecord.actualWorkDateTime().workRange().start().toString().split(" ")[1].split(":");
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
        return new TimeRecord(employeeNumber, toActualWorkDateTime());
    }

    private ActualWorkDateTime toActualWorkDateTime() {
        Date workDate = new Date(this.workDate);
        ClockTime startTime = new ClockTime(Integer.valueOf(startHour), Integer.valueOf(startMinute));

        Minute daytimeBreakMinute = new Minute(daytimeBreakTime);
        Minute nightBreakTime = new Minute(this.nightBreakTime);
        InputEndTime inputEndTime = new InputEndTime(Integer.valueOf(endHour), Integer.valueOf(endMinute));
        return new ActualWorkDateTime(
                new WorkRange(
                    new StartDateTime(new DateTime(workDate, startTime)),
                    inputEndTime.endDateTime(workDate)
                ),
                new DaytimeBreakTime(daytimeBreakMinute),
                new NightBreakTime(nightBreakTime));
    }

    @AssertTrue(message = "勤務日を入力してください")
    boolean isWorkDateComplete() {
        return !workDate.isEmpty();
    }

    @AssertTrue(message = "勤務日が不正です")
    boolean isWorkDateValid() {
        if (!isWorkDateComplete()) return true;
        try {
            new Date(this.workDate);
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

        StartDateTime startDateTime = workStartDateTime();
        EndDateTime endDateTime = workEndDateTime();
        if (endDateTime.isAfter(startDateTime)) return true;

        return false;
    }

    private ClockTime workStartTime() {
        return new ClockTime(Integer.valueOf(startHour), Integer.valueOf(this.startMinute));
    }

    private EndTime workEndTime() {
        ClockTime clockTime = new ClockTime(Integer.valueOf(endHour), Integer.valueOf(endMinute));
        return new EndTime(clockTime);
    }

    private StartDateTime workStartDateTime() {
        ClockTime clockTime = new ClockTime(Integer.valueOf(startHour), Integer.valueOf(this.startMinute));
        return new StartDateTime(new DateTime(new Date(workDate), clockTime));
    }

    private EndDateTime workEndDateTime() {
        InputEndTime inputEndTime = new InputEndTime(Integer.valueOf(endHour), Integer.valueOf(endMinute));
        return inputEndTime.endDateTime(new Date(workDate));
    }

    @AssertTrue(message = "休憩時間が不正です")
    boolean isDaytimeBreakTimeValid() {
        if (daytimeBreakTime.isEmpty()) return true;

        try {
            DaytimeBreakTime daytimeBreakTime = new DaytimeBreakTime(new Minute(this.daytimeBreakTime));

            ActualWorkDateTime actualWorkDateTime = toActualWorkDateTime();
            Minute daytimeBindingMinute = actualWorkDateTime.daytimeBindingTime().quarterHour().minute();
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

            ActualWorkDateTime actualWorkDateTime = toActualWorkDateTime();
            Minute nightBindingMinute = actualWorkDateTime.nightBindingTime().quarterHour().minute();
            if (nightBindingMinute.lessThan(nightBreakTime.minute())) {
                return false;
            }
        } catch (NumberFormatException | DateTimeException ex) {
            return false;
        }
        return true;
    }
}