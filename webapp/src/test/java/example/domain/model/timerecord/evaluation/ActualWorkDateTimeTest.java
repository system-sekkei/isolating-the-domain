package example.domain.model.timerecord.evaluation;

import example.domain.model.employee.EmployeeNumber;
import example.presentation.controller.timerecord.AttendanceForm;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ActualWorkDateTimeTest {

    @Test
    void 労働時間が週４０時間を超えていない場合の１日あたりの法定時間外労働時間を計算できる() {
        EmployeeNumber en = new EmployeeNumber(1);
        List<TimeRecord> list = Arrays.asList(
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2019-12-27", "9:00", "18:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2019-12-28", "9:00", "18:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2019-12-29", "9:00", "18:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2019-12-30", "9:00", "20:00", "60", "0")));
        TimeRecords weeklyTimeRecord = new TimeRecords(list).weeklyRecords(WorkDate.from("2019-12-30"));
        ActualWorkDateTime actualWorkDateTime = weeklyTimeRecord.at(WorkDate.from("2019-12-30")).actualWorkDateTime;

        OverLegalHoursWorkTime overLegalHoursWorkTime = weeklyTimeRecord.overLegalHoursWorkTime(actualWorkDateTime);

        assertEquals("120", overLegalHoursWorkTime.quarterHour().minute().toString());
    }

    @Test
    void 労働時間が週４０時間を超えている場合の１日あたりの法定時間外労働時間を計算できる() {
        EmployeeNumber en = new EmployeeNumber(1);
        List<TimeRecord> list = Arrays.asList(
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2020-05-25", "9:00", "18:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2020-05-26", "9:00", "20:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2020-05-27", "9:00", "17:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2020-05-28", "9:00", "20:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2020-05-29", "9:00", "18:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2020-05-30", "9:00", "19:00", "60", "0")));
        TimeRecords weeklyTimeRecord = new TimeRecords(list).weeklyRecords(WorkDate.from("2020-05-30"));
        ActualWorkDateTime actualWorkDateTime = weeklyTimeRecord.at(WorkDate.from("2020-05-30")).actualWorkDateTime;

        OverLegalHoursWorkTime overLegalHoursWorkTime = weeklyTimeRecord.overLegalHoursWorkTime(actualWorkDateTime);

        assertEquals("480", overLegalHoursWorkTime.quarterHour().minute().toString());
    }
}