package example.domain.model.attendance;

import example.domain.model.employee.EmployeeNumber;
import example.domain.model.timerecord.evaluation.ActualWorkDateTime;
import example.domain.model.timerecord.evaluation.TimeRecord;
import example.domain.type.date.WeekOfMonth;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AttendanceTest {

    @Test
    void 指定した週の労働時間合計を集計することができる() {
        EmployeeNumber en = new EmployeeNumber(1);
        List<TimeRecord> list = Arrays.asList(
                new TimeRecord(en, ActualWorkDateTime.toActualWorkDateTime("2019-11-09", "9:00", "18:00", "60", "0")),
                new TimeRecord(en, ActualWorkDateTime.toActualWorkDateTime("2019-11-10", "9:00", "18:00", "60", "0")),
                new TimeRecord(en, ActualWorkDateTime.toActualWorkDateTime("2019-11-11", "9:00", "18:00", "60", "0")));
        Attendance attendance = new Attendance(new WorkMonth("2019-11"), new TimeRecords(list));

        WeekWorkTime weekWorkTime = attendance.weekWorkTime(new WeekOfMonth(3));

        assertEquals("960", weekWorkTime.value.minute().toString());
    }
}