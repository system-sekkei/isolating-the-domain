package example.domain.model.attendance;

import example.domain.model.employee.EmployeeNumber;
import example.domain.model.timerecord.evaluation.ActualWorkDateTime;
import example.domain.model.timerecord.evaluation.StatutoryWorkOnDaysOff;
import example.domain.model.timerecord.evaluation.TimeRecord;
import example.domain.type.date.Date;
import example.domain.type.date.Week;
import example.presentation.controller.timerecord.AttendanceForm;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AttendanceTest {

    @Test
    void 指定した週の労働時間合計を集計することができる() {
        EmployeeNumber en = new EmployeeNumber(1);
        List<TimeRecord> list = Arrays.asList(
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2019-11-09", "9:00", "18:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2019-11-10", "9:00", "18:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2019-11-11", "9:00", "18:00", "60", "0")));
        Attendance attendance = new Attendance(new WorkMonth("2019-11"), new TimeRecords(list));
        Week week = Week.from(Arrays.asList(
            Date.from("2019-11-10"),
            Date.from("2019-11-11"),
            Date.from("2019-11-12"),
            Date.from("2019-11-13"),
            Date.from("2019-11-14"),
            Date.from("2019-11-15"),
            Date.from("2019-11-16")));

        WeekWorkTime weekWorkTime = attendance.weekWorkTime(week);

        assertEquals("960", weekWorkTime.value.minute().toString());
    }

    @Test
    void 月跨ぎの週の労働時間合計を集計することができる() {
        EmployeeNumber en = new EmployeeNumber(1);
        List<TimeRecord> list = Arrays.asList(
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2019-12-24", "9:00", "18:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2019-12-31", "9:00", "18:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2020-01-01", "9:00", "18:00", "60", "0")));
        Attendance attendance = new Attendance(new WorkMonth("2019-12"), new TimeRecords(list));
        Week week = Week.from(Arrays.asList(
                Date.from("2019-12-29"),
                Date.from("2019-12-30"),
                Date.from("2019-12-31"),
                Date.from("2020-01-01"),
                Date.from("2020-01-02"),
                Date.from("2020-01-03"),
                Date.from("2020-01-04")));

        WeekWorkTime weekWorkTime = attendance.weekWorkTime(week);

        assertEquals("960", weekWorkTime.value.minute().toString());
    }

    @Test
    void 指定した週の法定休日労働時間が計算できる() {
        EmployeeNumber en = new EmployeeNumber(1);
        List<TimeRecord> list = Arrays.asList(
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2019-12-29", "9:00", "18:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2019-12-30", "9:00", "18:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2019-12-31", "9:00", "18:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2020-01-01", "9:00", "18:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2020-01-02", "9:00", "18:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2020-01-03", "9:00", "18:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2020-01-04", "9:00", "18:00", "60", "0")));
        Attendance attendance = new Attendance(new WorkMonth("2019-12"), new TimeRecords(list));
        Week week = Week.from(Arrays.asList(
                Date.from("2019-12-29"),
                Date.from("2019-12-30"),
                Date.from("2019-12-31"),
                Date.from("2020-01-01"),
                Date.from("2020-01-02"),
                Date.from("2020-01-03"),
                Date.from("2020-01-04")));

        StatutoryWorkOnDaysOff statutoryWorkOnDaysOff = attendance.statutoryDaysOffWorkByWeek(week);

        assertEquals("480", statutoryWorkOnDaysOff.quarterHour().minute().toString());
    }
}