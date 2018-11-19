package example.domain.model.attendance;

import example.domain.type.time.HourTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkTimeRangeTest {
    @DisplayName("深夜時間帯の作業時間を正しく返却できること")
    @ParameterizedTest
    @CsvSource({"18:00, 3:00, 300", "8:00, 17:00, 0"})
    void midnightWorkTime(String begin, String end, int expected) {
        WorkTimeRange sut = new WorkTimeRange(new WorkStartTime(new HourTime(begin)), new WorkEndTime(new HourTime(end)));
        assertEquals(expected, sut.midnightWorkTime().toMinute().value());
    }

    @DisplayName("作業時間を正しく返却できること")
    @ParameterizedTest
    @CsvSource({"9:00, 17:00, 08:00", "19:00, 1:00, 06:00", "20:00, 23:00, 03:00", "9:01, 18:14, 09:00"})
    void normalWorkTime(String begin, String end, String expected) {
        WorkTimeRange sut = new WorkTimeRange(new WorkStartTime(new HourTime(begin)), new WorkEndTime(new HourTime(end)));
        assertEquals(expected, sut.workTime().toString());
    }
}
