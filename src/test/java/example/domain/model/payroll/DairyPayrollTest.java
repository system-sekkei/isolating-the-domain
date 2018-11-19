package example.domain.model.payroll;

import example.domain.model.attendance.AttendanceOfDay;
import example.domain.model.attendance.Break;
import example.domain.model.attendance.WorkEndTime;
import example.domain.model.attendance.WorkStartTime;
import example.domain.model.contract.HourlyWage;
import example.domain.type.date.Date;
import example.domain.type.time.HourTime;
import example.domain.type.time.Minute;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DairyPayrollTest {
    @DisplayName("作業時間と時給で賃金計算が行えること")
    @ParameterizedTest
    @CsvSource({"9:00, 0:00, 60, 1000, 15750"})
    void wage(String begin, String end, int breakMinute, int hourlyWage, int expected) {
        AttendanceOfDay attendanceOfDay = new AttendanceOfDay(Date.now(),
                new WorkStartTime(new HourTime(begin)), new WorkEndTime(new HourTime(end)), new Break(new Minute(breakMinute)));
        HourlyWage hourlyWageObj = new HourlyWage(hourlyWage);
        DairyPayroll sut = new DairyPayroll(Date.now(), attendanceOfDay, hourlyWageObj);
        assertEquals(expected, sut.wage().value.intValue());
    }
}
