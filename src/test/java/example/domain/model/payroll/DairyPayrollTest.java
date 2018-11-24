package example.domain.model.payroll;

import example.domain.model.attendance.*;
import example.domain.model.contract.HourlyWage;
import example.domain.type.date.Date;
import example.domain.type.time.ClockTime;
import example.domain.type.time.Minute;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DairyPayrollTest {
    @DisplayName("作業時間と時給で賃金計算が行えること")
    @ParameterizedTest
    @CsvSource({
            // 通常
            "09:00, 10:00, 0, 0, 1000, 1000",
            // 深夜
            "23:00, 24:00, 0, 0, 1000, 1250",
            // 通常＋深夜＋休憩
            "20:00, 24:00, 30, 60, 1000, 2750",
            // 通常10時間（超過2時間）
            "09:00, 19:00, 0, 0, 1000, 10500",
            // 通常13時間＋深夜1時間（超過6時間）
            "8:00, 0:00, 60, 60, 1000, 15750"
    })
    void wage(String begin, String end, int breakMinute, int midnightBreakMinute, int hourlyWage, int expected) {
        Attendance attendance = new Attendance(Date.now(),
                new WorkStartTime(new ClockTime(begin)), new WorkEndTime(new ClockTime(end)),
                new NormalBreakTime(new Minute(breakMinute)), new MidnightBreakTime(new Minute(midnightBreakMinute)));
        HourlyWage hourlyWageObj = new HourlyWage(hourlyWage);
        DairyPayroll sut = new DairyPayroll(Date.now(), attendance, hourlyWageObj);
        assertEquals(expected, sut.wage().value.intValue());
    }
}
