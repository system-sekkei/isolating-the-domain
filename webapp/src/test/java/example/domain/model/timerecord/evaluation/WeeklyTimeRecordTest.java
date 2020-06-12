package example.domain.model.timerecord.evaluation;

import example.domain.model.employee.EmployeeNumber;
import example.domain.type.date.Date;
import example.presentation.controller.timerecord.AttendanceForm;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class WeeklyTimeRecordTest {

    @Test
    void 週の途中で月を跨いだ場合の週の勤務実績情報を取得することができる() {
        EmployeeNumber en = new EmployeeNumber(1);
        TimeRecords timeRecords = new TimeRecords(Arrays.asList(
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2020-06-01", "9:00", "18:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2020-06-02", "9:00", "20:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2020-06-07", "9:00", "20:00", "60", "0"))));

        TimeRecords beforeMonthlyRecords = new TimeRecords(Arrays.asList(
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2020-05-30", "9:00", "18:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2020-05-31", "9:00", "20:00", "60", "0"))));

        WeeklyTimeRecord weeklyTimeRecord = WeeklyTimeRecord.from(timeRecords, beforeMonthlyRecords, new Date(LocalDate.parse("2020-06-01", DateTimeFormatter.ISO_DATE)));

        List<String> result = weeklyTimeRecord.value.list().stream().map(r -> r.workDate().toDate().toString()).collect(Collectors.toList());

        assertEquals(result, List.of("2020-05-31", "2020-06-01", "2020-06-02"));
    }

    @Test
    void 週の途中で年を跨いだ場合の週の勤務実績情報を取得することができる() {
        EmployeeNumber en = new EmployeeNumber(1);
        TimeRecords timeRecords = new TimeRecords(Arrays.asList(
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2020-01-01", "9:00", "18:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2020-01-02", "9:00", "20:00", "60", "0"))));

        TimeRecords beforeMonthlyRecords = new TimeRecords(Arrays.asList(
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2019-12-29", "9:00", "18:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2019-12-28", "9:00", "20:00", "60", "0"))));

        WeeklyTimeRecord weeklyTimeRecord = WeeklyTimeRecord.from(timeRecords, beforeMonthlyRecords, new Date(LocalDate.parse("2020-01-02", DateTimeFormatter.ISO_DATE)));

        List<String> result = weeklyTimeRecord.value.list().stream().map(r -> r.workDate().toDate().toString()).collect(Collectors.toList());

        assertEquals(result, List.of("2019-12-29", "2020-01-01", "2020-01-02"));
    }
}