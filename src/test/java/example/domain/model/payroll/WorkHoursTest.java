package example.domain.model.payroll;

import example.domain.model.payroll.WorkHours;
import example.domain.type.time.Minute;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WorkHoursTest {
    @DisplayName("正しく生成できること")
    @ParameterizedTest
    @CsvSource({"60, 1.00, 75, 1.25"})
    void constract(int minute, String expected) {
        WorkHours sut = WorkHours.of(new Minute(minute));
        assertEquals(expected, sut.value().toString());
    }
}
