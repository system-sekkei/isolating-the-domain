package example.domain.model.payroll;

import example.domain.model.contract.HourlyWage;
import example.domain.type.time.HourAndMinute;
import example.domain.type.time.Minute;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WageTest {
    @DisplayName("作業時間と時給で賃金計算が行えること")
    @ParameterizedTest
    @CsvSource({"8, 0, 1000, 8000" , "8, 30, 1000, 8500"})
    void of(int workHour, int workMinute, int hourlyWage, int expected) {
        HourAndMinute workTime = HourAndMinute.from(new Minute(workHour * 60 + workMinute));
        HourlyWage hourlyWageObj = new HourlyWage((hourlyWage));
        Wage sut = Wage.of(workTime, hourlyWageObj);
        assertEquals(expected, sut.value.intValue());
    }
}
