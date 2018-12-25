package example.domain.type.time;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClockTimeRangeTest {

    @DisplayName("時間間隔取得テスト")
    @ParameterizedTest
    @CsvSource({"9:00, 17:00, 08:00", "9:00, 9:30, 00:30", "9:30, 10:00, 00:30", "20:00, 2:00, 06:00"})
    void differenceTime(String fromTime, String toTime, String rangeTime) {
        ClockTime from = new ClockTime(fromTime);
        ClockTime to = new ClockTime(toTime);

        ClockTimeRange range = new ClockTimeRange(from, to);
        HourAndMinute result = range.between();

        assertEquals(rangeTime, result.toString());
    }

    @DisplayName("含有のテスト")
    @ParameterizedTest
    @CsvSource({
            "22:00, 5:00, 22:00, true",
            "22:00, 5:00, 21:50, false",
            "22:00, 5:00, 22:01, true",
            "22:00, 5:00, 4:59, true",
            "22:00, 5:00, 5:00, true",
            "22:00, 5:00, 5:01, false",
    })
    void include(String begin, String end, String target, boolean actual) {
        ClockTimeRange sut = new ClockTimeRange(new ClockTime(begin), new ClockTime(end));
        ClockTime value = new ClockTime(target);
        assertEquals(actual, sut.include(value));
    }
}