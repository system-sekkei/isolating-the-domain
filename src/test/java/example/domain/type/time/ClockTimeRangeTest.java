package example.domain.type.time;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClockTimeRangeTest {

    @DisplayName("時間間隔取得テスト")
    @ParameterizedTest
    @CsvSource({
            "9:00, 17:00, 08:00",
            "17:00, 9:00, 08:00",
            "9:00, 9:30, 00:30",
            "9:30, 10:00, 00:30",
            "20:00, 26:00, 06:00"})
    void differenceTime(String fromTime, String toTime, String rangeTime) {
        ClockTime from = new ClockTime(fromTime);
        ClockTime to = new ClockTime(toTime);

        ClockTimeRange range = new ClockTimeRange(from, to);
        HourAndMinute result = HourAndMinute.from(range.between());

        assertEquals(rangeTime, result.toString());
    }
}