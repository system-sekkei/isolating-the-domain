package example.domain.model.timerecord.evaluation;

import example.domain.model.employee.EmployeeNumber;
import example.presentation.controller.timerecord.AttendanceForm;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OverLegalHoursWorkTimeTest {

    @Test
    void 週の法定時間外労働時間を計算できる() {
        EmployeeNumber en = new EmployeeNumber(1);
        List<TimeRecord> list = Arrays.asList(
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2019-12-26", "9:00", "17:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2019-12-27", "9:00", "18:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2019-12-28", "9:00", "18:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2019-12-29", "9:00", "18:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2019-12-30", "9:00", "20:00", "60", "0")),
                new TimeRecord(en, AttendanceForm.toActualWorkDateTime("2019-12-31", "9:00", "18:00", "60", "0")));
        WeeklyTimeRecords weeklyTimeRecords = new WeeklyTimeRecords(list);
        OverLegalHoursWorkTime overLegalHoursWorkTime = OverLegalHoursWorkTime.from(weeklyTimeRecords);

        assertEquals("540", overLegalHoursWorkTime.quarterHour().minute().toString());
    }

    @Test
    void 一日８時間を超える労働時間の週の集計を計算できる() {
        List<WorkTime> weeklyWorkTimes = List.of(
            new WorkTime(new DaytimeWorkTime(400), new NightWorkTime(0)),
            new WorkTime(new DaytimeWorkTime(500), new NightWorkTime(0)),
            new WorkTime(new DaytimeWorkTime(480), new NightWorkTime(0)),
            new WorkTime(new DaytimeWorkTime(480), new NightWorkTime(60)),
            new WorkTime(new DaytimeWorkTime(600), new NightWorkTime(30)),
            new WorkTime(new DaytimeWorkTime(600), new NightWorkTime(0)));

        OverLegalHoursWorkTime overLegalHoursWorkTime = OverLegalHoursWorkTime.dailyOverLegalHoursWorkTimePerWeek(weeklyWorkTimes);
        assertEquals("350", overLegalHoursWorkTime.quarterHour().minute().toString());
    }

    @Test
    void 週４０時間を超える労働時間を計算できる() {
        List<WorkTime> weeklyWorkTimes = List.of(
                new WorkTime(new DaytimeWorkTime(400), new NightWorkTime(0)),
                new WorkTime(new DaytimeWorkTime(500), new NightWorkTime(0)),
                new WorkTime(new DaytimeWorkTime(480), new NightWorkTime(0)),
                new WorkTime(new DaytimeWorkTime(480), new NightWorkTime(60)),
                new WorkTime(new DaytimeWorkTime(600), new NightWorkTime(30)),
                new WorkTime(new DaytimeWorkTime(600), new NightWorkTime(0)));

        OverLegalHoursWorkTime overLegalHoursWorkTime = OverLegalHoursWorkTime.weeklyOverLegalHoursWorkTime(weeklyWorkTimes);
        assertEquals("750", overLegalHoursWorkTime.quarterHour().minute().toString());
    }
}