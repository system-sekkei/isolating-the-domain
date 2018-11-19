package example.domain.model.payroll;

import example.domain.model.contract.HourlyWage;
import example.domain.type.time.Minute;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WageTest {
    @DisplayName("作業時間と時給で賃金計算が行えること")
    @ParameterizedTest
    @CsvSource({"480, 1000, 8000" , "510, 1000, 8500"})
    void of(int minute, int hourlyWage, int expected) {
        WorkHours workHours = WorkHours.of(new Minute(minute));
        HourlyWage hourlyWageObj = new HourlyWage((hourlyWage));
        Wage sut = Wage.of(workHours, hourlyWageObj);
        assertEquals(expected, sut.value.intValue());
    }
}
