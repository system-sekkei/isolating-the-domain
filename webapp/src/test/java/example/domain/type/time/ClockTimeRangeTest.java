package example.domain.type.time;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClockTimeRangeTest {

    @DisplayName("時間間隔取得テスト")
    @ParameterizedTest
    @CsvSource({
            "9:00, 17:00, 480",
            "17:00, 9:00, 480",
            "9:00, 9:30, 30",
            "9:30, 10:00, 30",
            "20:00, 26:00, 360"})
    void differenceTime(String fromTime, String toTime, String rangeTime) {
        ClockTime from = new ClockTime(fromTime);
        ClockTime to = new ClockTime(toTime);

        ClockTimeRange range = new ClockTimeRange(from, to);
        Minute result = range.between();

        assertEquals(rangeTime, result.toString());
    }
}