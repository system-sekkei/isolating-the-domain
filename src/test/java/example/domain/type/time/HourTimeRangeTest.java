package example.domain.type.time;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HourTimeRangeTest {

    @DisplayName("時間間隔取得テスト")
    @ParameterizedTest
    @CsvSource({"9:00, 17:00, 08:00", "9:00, 9:30, 00:30", "9:30, 10:00, 00:30", "20:00, 2:00, 06:00"})
    void differenceTime(String fromTime, String toTime, String rangeTime) {
        HourTime from = new HourTime(fromTime);
        HourTime to = new HourTime(toTime);

        HourTimeRange range = new HourTimeRange(from, to);
        HourAndMinute result = range.between();

        assertEquals(rangeTime, result.toString());
    }
}