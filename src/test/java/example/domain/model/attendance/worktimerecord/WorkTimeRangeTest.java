package example.domain.model.attendance.worktimerecord;

import example.domain.type.time.ClockTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkTimeRangeTest {
    @DisplayName("深夜時間帯の拘束時間を正しく返却できること")
    @ParameterizedTest
    @CsvSource({
            "18:00, 27:00, 300",
            "8:00, 17:00, 0",
            "1:00, 3:00, 120",
            "3:00, 23:00, 180"
    })
    void midnightBindingTime(String begin, String end, String expected) {
        WorkTimeRange sut = new WorkTimeRange(new WorkStartTime(new ClockTime(begin)), new WorkEndTime(new ClockTime(end)));
        assertEquals(expected, sut.midnightBindingTime().quarterHour().minute().toString());
    }

    @DisplayName("拘束時間を正しく返却できること")
    @ParameterizedTest
    @CsvSource({
            "9:00, 17:00, 480",
            "19:00, 25:00, 360",
            "20:00, 23:00, 180",
            "9:01, 18:14, 540"
    })
    void totalBindingTime(String begin, String end, String expected) {
        WorkTimeRange sut = new WorkTimeRange(new WorkStartTime(new ClockTime(begin)), new WorkEndTime(new ClockTime(end)));
        assertEquals(expected, sut.bindingTime().quarterHour().minute().toString());
    }
}
