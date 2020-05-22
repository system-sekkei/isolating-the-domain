package example.domain.model.timerecord.evaluation;

import example.domain.model.employee.EmployeeNumber;
import example.presentation.controller.timerecord.AttendanceForm;
import org.junit.jupiter.api.Disabled;
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

        OverLegalHoursWorkTime overLegalHoursWorkTime = actualWorkDateTime.overLegalHoursWorkTime(weeklyTimeRecord);

        assertEquals("120", overLegalHoursWorkTime.quarterHour().minute().toString());
    }

    @Test
    @Disabled
    void 労働時間が週４０時間を超えている場合の１日あたりの法定時間外労働時間を計算できる() {
        // TODO:
        EmployeeNumber en = new EmployeeNumber(1);
        List<TimeRecord> list = Arrays.asList(
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2019-12-26", "9:00", "17:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2019-12-27", "9:00", "18:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2019-12-28", "9:00", "18:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2019-12-29", "9:00", "18:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2019-12-30", "9:00", "20:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2019-12-31", "9:00", "18:00", "60", "0")));
        TimeRecords weeklyTimeRecord = new TimeRecords(list).weeklyRecords(WorkDate.from("2019-12-30"));
        ActualWorkDateTime actualWorkDateTime = weeklyTimeRecord.at(WorkDate.from("2019-12-30")).actualWorkDateTime;

        OverLegalHoursWorkTime overLegalHoursWorkTime = actualWorkDateTime.overLegalHoursWorkTime(weeklyTimeRecord);

        assertEquals("120", overLegalHoursWorkTime.quarterHour().minute().toString());
    }
}